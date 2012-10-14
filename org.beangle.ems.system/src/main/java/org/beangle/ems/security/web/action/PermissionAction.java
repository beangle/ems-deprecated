/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.web.action;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.blueprint.Member;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.function.FuncPermission;
import org.beangle.security.blueprint.function.FuncResource;
import org.beangle.security.blueprint.function.service.internal.CacheableAuthorityManager;
import org.beangle.security.blueprint.nav.Menu;
import org.beangle.security.blueprint.nav.MenuProfile;
import org.beangle.security.blueprint.nav.service.MenuService;
import org.beangle.security.core.authority.GrantedAuthorityBean;
import org.beangle.struts2.convention.route.Action;

/**
 * 权限分配与管理响应类
 * 
 * @author chaostone 2005-10-9
 */
public class PermissionAction extends SecurityActionSupport {

  private CacheableAuthorityManager authorityManager;

  private MenuService menuService;

  /**
   * 根据菜单配置来分配权限
   */
  public String edit() {
    Long roleId = getId("role");
    Role role = entityDao.get(Role.class, roleId);
    User user = entityDao.get(User.class, getUserId());
    put("manager", user);
    List<Role> mngRoles = CollectUtils.newArrayList();
    if (isAdmin()) {
      mngRoles = entityDao.getAll(Role.class);
    } else {
      for (Member m : user.getMembers()) {
        if (m.isGranter()) mngRoles.add(m.getRole());
      }
    }
    put("mngRoles", mngRoles);
    List<MenuProfile> menuProfiles = menuService.getProfiles(role);
    put("menuProfiles", menuProfiles);

    MenuProfile menuProfile = menuService.getProfile(role, getLong("menuProfileId"));
    if (null == menuProfile && !menuProfiles.isEmpty()) {
      menuProfile = menuProfiles.get(0);
    }
    List<Menu> menus = CollectUtils.newArrayList();
    if (null != menuProfile) {
      Collection<FuncResource> resources = null;
      if (isAdmin()) {
        menus = menuProfile.getMenus();
        resources = entityDao.getAll(FuncResource.class);
      } else {
        menus = menuService.getMenus(menuProfile, user);
        resources = funcPermissionService.getResources(user);
      }
      put("resources", CollectUtils.newHashSet(resources));
      boolean displayFreezen = getBool("displayFreezen");
      if (!displayFreezen) {
        List<Menu> freezed = CollectUtils.newArrayList();
        for (Menu menu : menus) {
          if (!menu.isEnabled()) freezed.add(menu);
        }
        menus.removeAll(freezed);
      }
      Set<FuncResource> roleResources = CollectUtils.newHashSet();
      List<FuncPermission> permissions = funcPermissionService.getPermissions(role);
      Collection<Menu> roleMenus = menuService.getMenus(menuProfile, role, null);
      for (final FuncPermission permission : permissions) {
        roleResources.add(permission.getResource());
      }
      put("roleMenus", CollectUtils.newHashSet(roleMenus));
      put("roleResources", roleResources);
      
      Set<Role> parents=CollectUtils.newHashSet();
      Set<FuncResource> parentResources = CollectUtils.newHashSet();
      Set<Menu> parentMenus=CollectUtils.newHashSet();
      Role parent=role.getParent();
      while(null!=parent && !parents.contains(parent)){
        List<FuncPermission> parentPermissions = funcPermissionService.getPermissions(parent);
        parentMenus.addAll(menuService.getMenus(menuProfile, parent, null));
        for (final FuncPermission permission : parentPermissions) {
          parentResources.add(permission.getResource());
        }
        parents.add(parent);
        parent=parent.getParent();
      }
      put("parentMenus",parentMenus);
      put("parentResources",parentResources);
    }else{
      put("roleMenus", Collections.emptySet());
      put("roleResources",Collections.emptySet());
      put("parentMenus", Collections.emptySet());
      put("parentResources",Collections.emptySet());
    }
    put("menus", menus);
    put("menuProfile", menuProfile);
    put("role", role);
    return forward();
  }

  /**
   * 显示权限操作提示界面
   */
  public String prompt() {
    return forward();
  }

  /**
   * 保存模块级权限
   */
  public String save() {
    Role role = entityDao.get(Role.class, getLong("role.id"));
    MenuProfile menuProfile = (MenuProfile) entityDao.get(MenuProfile.class, getLong("menuProfileId"));
    Set<FuncResource> newResources = CollectUtils.newHashSet(entityDao.get(FuncResource.class,
        Strings.splitToLong(get("resourceId"))));

    // 管理员拥有的菜单权限和系统资源
    User manager = entityDao.get(User.class, getUserId());
    Set<Menu> mngMenus = null;
    Set<Resource> mngResources = CollectUtils.newHashSet();
    if (isAdmin()) {
      mngMenus = CollectUtils.newHashSet(menuProfile.getMenus());
    } else {
      mngMenus = CollectUtils.newHashSet(menuService.getMenus(menuProfile, (User) manager));
    }
    for (final Menu m : mngMenus) {
      mngResources.addAll(m.getResources());
    }
    newResources.retainAll(mngResources);
    funcPermissionService.authorize(role, newResources);
    authorityManager.refreshRolePermissions(new GrantedAuthorityBean(role.getId()));

    Action redirect = Action.to(this).method("edit");
    redirect.param("role.id", role.getId()).param("menuProfileId", menuProfile.getId());
    String displayFreezen = get("displayFreezen");
    if (null != displayFreezen) redirect.param("displayFreezen", displayFreezen);

    return redirect(redirect, "info.save.success");
  }

  public void setAuthorityManager(CacheableAuthorityManager authorityManager) {
    this.authorityManager = authorityManager;
  }

  public void setMenuService(MenuService menuService) {
    this.menuService = menuService;
  }

}
