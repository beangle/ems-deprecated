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
package org.beangle.ems.root.web.action;

import org.beangle.commons.lang.Strings;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.Securities;
import org.beangle.security.auth.UsernamePasswordAuthentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.struts2.annotation.Result;
import org.beangle.struts2.annotation.Results;
import org.beangle.struts2.captcha.service.CaptchaProvider;

@Results({ @Result(name = "home", type = "redirectAction", location = "home"),
    @Result(name = "failure", type = "freemarker", location = "/login.ftl") })
public class LoginAction extends SecurityActionSupport {

  private CaptchaProvider captchaProvider;

  public static final String LOGIN_FAILURE_COUNT = "loginFailureCount";

  public String index() {
    if (Securities.hasValidAuthentication()) { return "home"; }
    if (!shouldLogin()) { return "failure"; }
    String errorMsg = doLogin();
    if (Strings.isNotEmpty(errorMsg)) {
      addError(getText(errorMsg));
      increaseLoginFailure();
      return "failure";
    }
    clearLoginFailure();
    return "home";
  }

  /**
   * @return true if all other checker is pass.
   */
  protected boolean shouldLogin() {
    String username = get("username");
    String password = get("password");
    if (Strings.isBlank(username) || Strings.isBlank(password)) { return false; }
    if (notFailEnough()) { return true; }

    if (null != captchaProvider) {
      boolean valid = captchaProvider.verify(getRequest(),get("captcha_response"));
      if (!valid) addError("security.WrongCaptcha");
      return valid;
    } else {
      return true;
    }
  }

  protected String doLogin() {
    String username = get("username");
    String password = get("password");
    if (Strings.isBlank(username) || Strings.isBlank(password)) return "failure";
    username = username.trim();
    UsernamePasswordAuthentication auth = new UsernamePasswordAuthentication(username, password);
    try {
      securityHelper.getAuthenticationService().login(getRequest(), auth);
    } catch (AuthenticationException e) {
      return e.getMessage();
    }
    return null;
  }

  private boolean notFailEnough() {
    Integer loginFailureCount = (Integer) getSession().get(LOGIN_FAILURE_COUNT);
    if (null == loginFailureCount) loginFailureCount = Integer.valueOf(0);
    if (loginFailureCount.intValue() <= 1) { return true; }
    return false;
  }

  private void increaseLoginFailure() {
    Integer loginFailureCount = (Integer) getSession().get(LOGIN_FAILURE_COUNT);
    if (null == loginFailureCount) loginFailureCount = Integer.valueOf(0);
    loginFailureCount++;
    getSession().put(LOGIN_FAILURE_COUNT, loginFailureCount);
  }

  private void clearLoginFailure() {
    getSession().remove(LOGIN_FAILURE_COUNT);
  }

  public CaptchaProvider getCaptchaProvider() {
    return captchaProvider;
  }

  public void setCaptchaProvider(CaptchaProvider captchaProvider) {
    this.captchaProvider = captchaProvider;
  }

}
