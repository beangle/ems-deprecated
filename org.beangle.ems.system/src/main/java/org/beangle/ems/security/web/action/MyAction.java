/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.security.helper.UserDashboardHelper;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.blueprint.SecurityUtils;
import org.beangle.security.blueprint.Settings;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.model.UserBean;
import org.beangle.security.codec.EncryptUtil;
import org.beangle.security.core.session.SessionRegistry;

/**
 * 维护个人账户信息
 * 
 * @author chaostone
 */
public class MyAction extends SecurityActionSupport {

  private SessionRegistry sessionRegistry;

  private UserDashboardHelper userDashboardHelper;

  public String index() {
    userDashboardHelper.buildDashboard(entityDao.get(User.class, getUserId()));
    return forward();
  }

  public String infolet() {
    put("user", entityDao.get(User.class, getUserId()));
    return forward();
  }

  public String dashboard() {
    userDashboardHelper.buildDashboard(entityDao.get(User.class, getUserId()));
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
    put("user", entityDao.get(User.class, getUserId()));
    put("settings", new Settings(getConfig()));
    return forward();
  }

  /**
   * 用户更新自己的密码和邮箱
   */
  public String save() {
    Long userId = getUserId();
    String email = get("mail");
    String pwd = get("password");
    Map<String, Object> valueMap = CollectUtils.newHashMap();
    valueMap.put("password", pwd);
    valueMap.put("mail", email);
    entityDao.update(User.class, "id", new Object[] { userId }, valueMap);
    return redirect("infolet", "ok.passwordChanged");
  }

  /**
   * 发送密码
   */
  public String sendPassword() {
    String name = get("name");
    String email = get("mail");
    if (Strings.isEmpty(name) || Strings.isEmpty(email)) {
      addError("error.parameters.needed");
      return (ERROR);
    }
    List<UserBean> userList = entityDao.get(UserBean.class, "name", name);
    UserBean user = null;
    if (userList.isEmpty()) {
      return goErrorWithMessage("error.user.notExist");
    } else {
      user = userList.get(0);
    }
    if (!Objects.equals(email, user.getMail())) {
      return goErrorWithMessage("error.email.notEqualToOrign");
    } else {
      String longinName = user.getName();
      String password = RandomStringUtils.randomNumeric(6);
      user.setRemark(password);
      user.setPassword(EncryptUtil.encode(password));
      // String title = getText("user.password.sendmail.title");

      List<Object> values = CollectUtils.newArrayList();
      values.add(longinName);
      values.add(password);
      // String body = getText("user.password.sendmail.body", values);
      // try {
      // SimpleMailMessage msg = new SimpleMailMessage(message);
      // msg.setTo(user.getMail());
      // msg.setSubject(title);
      // msg.setText(body.toString());
      // mailSender.send(msg);
      // } catch (Exception e) {
      // e.printStackTrace();
      // logger.info("reset password error for user:" + user.getName() + " with email :"
      // + user.getMail());
      // return goErrorWithMessage("error.email.sendError");
      // }
    }
    entityDao.saveOrUpdate(user);
    return forward("sendResult");
  }

  private String goErrorWithMessage(String key) {
    addError(key);
    return forward("resetPassword");
  }

  public void setSessionRegistry(SessionRegistry sessionRegistry) {
    this.sessionRegistry = sessionRegistry;
  }

  // public void setMailSender(MailSender mailSender) {
  // this.mailSender = mailSender;
  // }
  //
  // public void setMessage(SimpleMailMessage message) {
  // this.message = message;
  // }

  public void setUserDashboardHelper(UserDashboardHelper userDashboardHelper) {
    this.userDashboardHelper = userDashboardHelper;
  }

}
