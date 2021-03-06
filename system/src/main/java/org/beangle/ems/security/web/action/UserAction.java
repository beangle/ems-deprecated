/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.ems.security.web.action;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.Operation;
import org.beangle.commons.dao.query.builder.Condition;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.transfer.exporter.PropertyExtractor;
import org.beangle.ems.security.helper.UserDashboardHelper;
import org.beangle.ems.security.helper.UserPropertyExtractor;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.RoleMember;
import org.beangle.security.blueprint.SecurityUtils;
import org.beangle.security.blueprint.Settings;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.model.RoleMemberBean;
import org.beangle.security.blueprint.model.UserBean;
import org.beangle.security.blueprint.service.UserService;
import org.beangle.security.codec.EncryptUtil;
import org.beangle.struts2.convention.route.Action;

/**
 * 用户管理响应处理类
 * 
 * @author chaostone 2005-9-29
 */
public class UserAction extends SecurityActionSupport {

  private UserDashboardHelper userDashboardHelper;
  private UserService userService;

  public String dashboard() {
    Long userId = getLongId("user");
    User managed = null;
    if (null != userId) {
      managed = entityDao.get(User.class, userId);
    } else {
      String username = get("user.name");
      if (null != username) managed = securityHelper.getUserService().get(username);
    }
    User me = userService.get(SecurityUtils.getUsername());
    if (null != managed) {
      if (me.equals(managed) || securityHelper.getUserService().isManagedBy(me, managed)) {
        userDashboardHelper.buildDashboard(managed);
        return forward();
      } else {
        return forward(ERROR);
      }
    } else {
      userDashboardHelper.buildDashboard(me);
    }
    return forward();
  }

  @SuppressWarnings("unchecked")
  protected OqlBuilder<User> getQueryBuilder() {
    User manager = userService.get(SecurityUtils.getUsername());
    OqlBuilder<User> userQuery = OqlBuilder.from(getEntityName(), "user");
    // 查询角色
    StringBuilder sb = new StringBuilder("exists(from user.members m where ");
    List<Object> params = CollectUtils.newArrayList();
    boolean queryRole = false;
    if (!isAdmin()) {
      List<RoleMember> members = securityHelper.getUserService().getMembers(manager, RoleMember.Ship.MANAGER);
      List<Role> mngRoles = CollectUtils.newArrayList();
      for (RoleMember m : members) {
        mngRoles.add(m.getRole());
      }
      if (mngRoles.isEmpty()) {
        sb.append("1=0");
      } else {
        sb.append("m.role in(:roles) and m.member=true");
        params.add(mngRoles);
      }
      queryRole = true;
      userQuery.where("user.id != :meId", manager.getId());
    }
    String roleName = get("roleName");
    if (Strings.isNotEmpty(roleName)) {
      if (queryRole) sb.append(" and ");
      sb.append("m.role.name like :roleName ");
      params.add("%" + roleName + "%");
      queryRole = true;
    }
    if (queryRole) {
      sb.append(')');
      Condition roleCondition = new Condition(sb.toString());
      roleCondition.params(params);
      userQuery.where(roleCondition);
    }
    populateConditions(userQuery);
    userQuery.orderBy(get(Order.ORDER_STR)).limit(getPageLimit());
    return userQuery;
  }

  protected PropertyExtractor getPropertyExtractor() {
    return new UserPropertyExtractor(getTextResource());
  }

  /**
   * 保存用户信息
   */
  protected String saveAndForward(Entity<?> entity) {
    UserBean user = (UserBean) entity;
    if (entityDao.duplicate(User.class, user.getId(), "code", user.getCode())) {
      addMessage("security.error.usernameNotAvaliable", user.getCode());
      return forward(new Action(this, "edit"));
    }
    String errorMsg = "";
    // 检验用户合法性
    errorMsg = checkUser(user);
    if (Strings.isNotEmpty(errorMsg)) { return forward(new Action("edit"), errorMsg); }
    processPassword(user);
    if (!user.isPersisted()) {
      User creator = userService.get(SecurityUtils.getUsername());
      securityHelper.getUserService().createUser(creator, user);
    } else {
      securityHelper.getUserService().saveOrUpdate(user);
    }
    updateUserRole(user);
    return redirect("search", "info.save.success");
  }

  private void updateUserRole(User user) {
    Set<RoleMember> userMembers = user.getMembers();
    Map<Role, RoleMemberBean> memberMap = CollectUtils.newHashMap();
    for (RoleMember gm : userMembers) {
      memberMap.put(gm.getRole(), (RoleMemberBean) gm);
    }
    Set<RoleMember> newMembers = CollectUtils.newHashSet();
    Set<RoleMember> removedMembers = CollectUtils.newHashSet();
    User manager = userService.get(SecurityUtils.getUsername());
    Collection<RoleMember> members = securityHelper.getUserService().getMembers(manager,
        RoleMember.Ship.GRANTER);
    for (RoleMember member : members) {
      RoleMemberBean myMember = memberMap.get(member.getRole());
      boolean isMember = getBool("member" + member.getRole().getId());
      boolean isGranter = getBool("granter" + member.getRole().getId());
      boolean isManager = getBool("manager" + member.getRole().getId());
      if (!isMember && !isGranter && !isManager) {
        if (null != myMember) {
          user.getMembers().remove(myMember);
          removedMembers.add(myMember);
        }
      } else {
        if (null == myMember) {
          myMember = new RoleMemberBean(member.getRole(), user, null);
        }
        myMember.setUpdatedAt(new Date());
        myMember.setMember(isMember);
        myMember.setGranter(isGranter);
        myMember.setManager(isManager);
        newMembers.add(myMember);
      }
    }
    entityDao.execute(Operation.saveOrUpdate(newMembers).remove(removedMembers));
  }

  protected void editSetting(Entity<?> entity) {
    User manager = userService.get(SecurityUtils.getUsername());
    Set<Role> roles = CollectUtils.newHashSet();
    Map<Role, RoleMember> curMemberMap = CollectUtils.newHashMap();
    Collection<RoleMember> members = securityHelper.getUserService().getMembers(manager,
        RoleMember.Ship.GRANTER);
    for (RoleMember gm : members) {
      roles.add(gm.getRole());
      curMemberMap.put(gm.getRole(), gm);
    }
    put("roles", roles);

    User user = (User) entity;
    Set<RoleMember> userMembers = user.getMembers();
    Map<Role, RoleMember> memberMap = CollectUtils.newHashMap();
    for (RoleMember gm : userMembers) {
      memberMap.put(gm.getRole(), gm);
    }
    put("memberMap", memberMap);
    put("curMemberMap", curMemberMap);
    put("isadmin", securityHelper.getUserService().isRoot(user));
    put("isme", manager.getId().equals(user.getId()));
    put("settings", new Settings(getConfig()));
  }

  /**
   * 删除一个或多个用户
   */
  public String remove() {
    Long[] userIds = getLongIds("user");
    User creator = userService.get(SecurityUtils.getUsername());
    List<User> toBeRemoved = securityHelper.getUserService().getUsers(userIds);
    StringBuilder sb = new StringBuilder();
    User removed = null;
    int success = 0;
    int expected = toBeRemoved.size();
    try {
      for (User one : toBeRemoved) {
        removed = one;
        // 不能删除自己
        if (!one.getId().equals(creator.getId())) {
          securityHelper.getUserService().removeUser(creator, one);
          success++;
        } else {
          addFlashError("security.info.cannotRemoveSelf");
          expected--;
        }
      }
    } catch (Exception e) {
      sb.append(',').append(removed.getName());
    }
    if (sb.length() > 0) {
      sb.deleteCharAt(0);
      addFlashMessage("security.info.userRemovePartial", success, sb);
    } else if (expected == success && success > 0) {
      addFlashMessage("info.remove.success");
    }
    return redirect("search");
  }

  /**
   * 禁用或激活一个或多个用户
   */
  public String activate() {
    Long[] userIds = getLongIds("user");
    String isActivate = get("isActivate");
    int successCnt;
    User manager = userService.get(SecurityUtils.getUsername());
    String msg = "security.info.freeze.success";
    if (Strings.isNotEmpty(isActivate) && "false".equals(isActivate)) {
      successCnt = securityHelper.getUserService().updateState(manager, userIds, false);
    } else {
      msg = "security.info.activate.success";
      successCnt = securityHelper.getUserService().updateState(manager, userIds, true);
    }
    addFlashMessage(msg, successCnt);
    return redirect("search");
  }

  protected String checkUser(User user) {
    if (!user.isPersisted()
        && entityDao.exist(getEntityName(), "code", user.getCode())) { return "error.model.existed"; }
    return "";
  }

  public String info() throws Exception {
    String name = get("name");
    if (Strings.isNotBlank(name)) {
      User user = securityHelper.getUserService().get(name);
      if (null != user) {
        put("user", user);
        return forward(new Action((Class<?>) null, "dashboard", "&user.id=" + user.getId()));
      } else {
        return null;
      }
    } else {
      return super.info();
    }
  }

  protected void processPassword(UserBean user) {
    String password = get("password");
    if (Strings.isNotBlank(password)) {
      user.setPassword(EncryptUtil.encode(password));
    } else if (!user.isPersisted()) {
      password = User.DEFAULT_PASSWORD;
      user.setPassword(EncryptUtil.encode(password));
    }
  }

  public void setUserDashboardHelper(UserDashboardHelper userDashboardHelper) {
    this.userDashboardHelper = userDashboardHelper;
  }

  @Override
  protected String getEntityName() {
    return User.class.getName();
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

}
