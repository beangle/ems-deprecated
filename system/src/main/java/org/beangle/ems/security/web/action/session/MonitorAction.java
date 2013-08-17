/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.ems.security.web.action.session;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.beangle.commons.bean.comparators.PropertyComparator;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.access.AccessMonitor;
import org.beangle.commons.web.access.AccessRequest;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.service.RoleService;
import org.beangle.security.blueprint.session.model.SessionProfileBean;
import org.beangle.security.blueprint.session.model.SessionStat;
import org.beangle.security.blueprint.session.model.SessioninfoBean;
import org.beangle.security.blueprint.session.service.SessionProfileService;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.core.session.Sessioninfo;

/**
 * 系统在线用户管理
 * 
 * @author chaostone
 */
public class MonitorAction extends SecurityActionSupport {

  private SessionRegistry sessionRegistry;

  private SessionProfileService categoryProfileService;

  private AccessMonitor accessMonitor;

  private RoleService roleService;

  @SuppressWarnings("unchecked")
  public String profiles() {
    List<SessionProfileBean> profiles = (List<SessionProfileBean>) categoryProfileService.getProfiles();
    put("profiles", profiles);
    List<Role> roles = roleService.getRootRoles();
    for (SessionProfileBean profile : profiles)
      roles.remove(profile.getRole());
    put("roles", roles);
    return forward();
  }

  public String index() {
    String orderBy = get("orderBy");
    if (Strings.isEmpty(orderBy)) orderBy = "sessioninfo.loginAt desc";
    OqlBuilder<SessioninfoBean> builder = OqlBuilder.from(SessioninfoBean.class, "sessioninfo");
    populateConditions(builder);
    builder.orderBy(get(Order.ORDER_STR)).limit(getPageLimit());
    List<SessioninfoBean> infos=entityDao.search(builder);
    put("sessioninfos",infos );
    OqlBuilder<SessionStat> statBuilder = OqlBuilder.from(SessionStat.class, "stat");
    put("sessionStats", entityDao.search(statBuilder));
    return forward();
  }

  /**
   * 保存设置
   */
  @SuppressWarnings("unchecked")
  public String saveProfile() {
    List<SessionProfileBean> profiles = (List<SessionProfileBean>) categoryProfileService.getProfiles();
    for (final SessionProfileBean profile : profiles) {
      Integer roleId = profile.getRole().getId();
      Integer max = getInt("max_" + roleId);
      Short maxSessions = getShort("maxSessions_" + roleId);
      Short inactiveInterval = getShort("inactiveInterval_" + roleId);
      if (null != max && null != maxSessions && null != inactiveInterval) {
        profile.setCapacity(max);
        profile.setUserMaxSessions(maxSessions);
        profile.setInactiveInterval(inactiveInterval);
      }
    }
    Integer roleId = getInt("roleId_new");
    Integer max = getInt("max_new");
    Short maxSessions = getShort("maxSessions_new");
    Short inactiveInterval = getShort("inactiveInterval_new");
    if (null != max && null != maxSessions && null != inactiveInterval) {
      SessionProfileBean newProfile = new SessionProfileBean();
      newProfile.setRole(roleService.get(roleId));
      newProfile.setCapacity(max);
      newProfile.setUserMaxSessions(maxSessions);
      newProfile.setInactiveInterval(inactiveInterval);
      profiles.add(newProfile);
    }
    categoryProfileService.saveOrUpdate(profiles);
    return redirect("profiles", "info.save.success");
  }

  public String invalidate() {
    String[] sessionIds = getIds("sessioninfo", String.class);
    String mySessionId = ServletActionContext.getRequest().getSession().getId();
    boolean killed = getBool("kill");
    int success = 0;
    for (String sessionId : sessionIds) {
      if (mySessionId.equals(sessionId)) continue;
      if (sessionRegistry.expire(sessionId)) success++;
    }
    addFlashMessage(killed ? "security.info.session.kill" : "security.info.session.expire", success);
    return redirect("index");
  }

  public String kill() {
    String[] sessionIds = getIds("sessioninfo", String.class);
    String mySessionId = ServletActionContext.getRequest().getSession().getId();
    int success = 0;
    for (String sessionId : sessionIds) {
      if (mySessionId.equals(sessionId)) continue;
      Sessioninfo info = sessionRegistry.getSessioninfo(sessionId);
      if (info.isExpired()) {
        sessionRegistry.remove(sessionId);
        success++;
      }
    }
    addFlashMessage("security.info.session.kill", success);
    return redirect("index");
  }

  public String stat() {
    sessionRegistry.getController().stat();
    return redirect("index", "统计完成");
  }

  /**
   * 访问记录
   */
  public String requests() {
    String orderBy = get("orderBy");
    if (Strings.isEmpty(orderBy)) orderBy = "beginAt";
    List<AccessRequest> requests = accessMonitor.getRequests();
    Collections.sort(requests, new PropertyComparator(orderBy));
    Map<String, Date> beginAts = CollectUtils.newHashMap();
    for (AccessRequest r : requests) {
      beginAts.put(r.getSessionid(), new Date(r.getBeginAt()));
    }
    put("beginAts", beginAts);
    put("requests", requests);
    return forward();
  }

  public void setSessionRegistry(SessionRegistry sessionRegistry) {
    this.sessionRegistry = sessionRegistry;
  }

  public void setCategoryProfileService(SessionProfileService categoryProfileService) {
    this.categoryProfileService = categoryProfileService;
  }

  public void setAccessMonitor(AccessMonitor accessMonitor) {
    this.accessMonitor = accessMonitor;
  }

  public void setRoleService(RoleService roleService) {
    this.roleService = roleService;
  }

}
