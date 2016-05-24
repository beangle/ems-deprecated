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

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.helper.UserDashboardHelper;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.blueprint.SecurityUtils;
import org.beangle.security.blueprint.Settings;
import org.beangle.security.blueprint.model.UserBean;
import org.beangle.security.blueprint.service.UserService;
import org.beangle.security.core.session.SessionRegistry;

/**
 * 维护个人账户信息
 * 
 * @author chaostone
 */
public class MyAction extends SecurityActionSupport {

  private SessionRegistry sessionRegistry;

  private UserDashboardHelper userDashboardHelper;

  private UserService userService;

  public String index() {
    userDashboardHelper.buildDashboard(userService.get(SecurityUtils.getUsername()));
    return forward();
  }

  public String infolet() {
    put("user", userService.get(SecurityUtils.getUsername()));
    return forward();
  }

  public String dashboard() {
    userDashboardHelper.buildDashboard(userService.get(SecurityUtils.getUsername()));
    return forward();
  }

  public String activity() {
    put("sessioninfoLogs", userDashboardHelper.getSessioninfoLogService().getLoggers(getUsername(), 10));
    put("sessioninfos", sessionRegistry.getSessioninfos(SecurityUtils.getUsername(), true));
    return forward();
  }

  public String resetPassword() {
    return forward();
  }

  /**
   * 用户修改自己的密码
   */
  public String edit() {
    put("user", userService.get(SecurityUtils.getUsername()));
    put("settings", new Settings(getConfig()));
    return forward();
  }

  /**
   * 用户更新自己的密码和邮箱
   */
  public String save() {
    String email = get("mail");
    String pwd = get("password");
    Map<String, Object> valueMap = CollectUtils.newHashMap();
    valueMap.put("password", pwd);
    valueMap.put("mail", email);
    UserBean user = (UserBean) userService.get(SecurityUtils.getUsername());
    user.setPassword(pwd);
    entityDao.saveOrUpdate(user);
    return redirect("infolet", "ok.passwordChanged");
  }

  public void setSessionRegistry(SessionRegistry sessionRegistry) {
    this.sessionRegistry = sessionRegistry;
  }

  public void setUserDashboardHelper(UserDashboardHelper userDashboardHelper) {
    this.userDashboardHelper = userDashboardHelper;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

}
