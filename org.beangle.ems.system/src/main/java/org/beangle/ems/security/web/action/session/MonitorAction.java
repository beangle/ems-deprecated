/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
import org.beangle.security.blueprint.session.model.SessionProfileBean;
import org.beangle.security.blueprint.session.service.SessionProfileService;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.core.session.category.SessionStat;
import org.beangle.security.web.session.model.SessioninfoBean;

/**
 * 系统在线用户管理
 * 
 * @author chaostone
 */
public class MonitorAction extends SecurityActionSupport {

  private SessionRegistry sessionRegistry;

  private SessionProfileService categoryProfileService;

  private AccessMonitor accessMonitor;

  public String profiles() {
    List<SessionProfileBean> profiles = entityDao.getAll(SessionProfileBean.class);
    put("profiles", profiles);
    List<Role> roles = entityDao.search(OqlBuilder.from(Role.class, "g").where("g.parent is null")
        .orderBy("g.code"));
    for (SessionProfileBean profile : profiles)
      roles.remove(profile.getRole());
    put("roles", roles);
    return forward();
  }

  public String index() {
    String orderBy = get("orderBy");
    if (Strings.isEmpty(orderBy)) {
      orderBy = "sessioninfo.loginAt desc";
    }
    OqlBuilder<SessioninfoBean> builder = OqlBuilder.from(SessioninfoBean.class, "sessioninfo");
    populateConditions(builder);
    builder.orderBy(get(Order.ORDER_STR)).limit(getPageLimit());
    put("sessioninfos", entityDao.search(builder));
    OqlBuilder<SessionStat> statBuilder = OqlBuilder.from(SessionStat.class, "stat");
    put("sessionStats", entityDao.search(statBuilder));
    return forward();
  }

  /**
   * 保存设置
   */
  public String saveProfile() {
    List<SessionProfileBean> profiles = entityDao.getAll(SessionProfileBean.class);
    for (final SessionProfileBean profile : profiles) {
      Long roleId = profile.getRole().getId();
      Integer max = getInteger("max_" + roleId);
      Integer maxSessions = getInteger("maxSessions_" + roleId);
      Integer inactiveInterval = getInteger("inactiveInterval_" + roleId);
      if (null != max && null != maxSessions && null != inactiveInterval) {
        profile.setCapacity(max);
        profile.setUserMaxSessions(maxSessions);
        profile.setInactiveInterval(inactiveInterval);
      }
    }
    Long roleId = getLong("roleId_new");
    Integer max = getInteger("max_new");
    Integer maxSessions = getInteger("maxSessions_new");
    Integer inactiveInterval = getInteger("inactiveInterval_new");
    if (null != max && null != maxSessions && null != inactiveInterval) {
      SessionProfileBean newProfile = new SessionProfileBean();
      newProfile.setRole(entityDao.get(Role.class, roleId));
      newProfile.setCapacity(max);
      newProfile.setUserMaxSessions(maxSessions);
      newProfile.setInactiveInterval(inactiveInterval);
      profiles.add(newProfile);
    }
    categoryProfileService.saveOrUpdate(profiles);
    return redirect("profiles", "info.save.success");
  }

  public String invalidate() {
    String[] sessionIds = getIds(String.class, "sessioninfo");
    String mySessionId = ServletActionContext.getRequest().getSession().getId();
    boolean killed = getBool("kill");
    int success = 0;
    for (String sessionId : sessionIds) {
      if (mySessionId.equals(sessionId)) continue;
      sessionRegistry.expire(sessionId);
      success++;
    }
    addFlashMessage(killed ? "security.info.session.kill" : "security.info.session.expire", success);
    return redirect("index");
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

}
