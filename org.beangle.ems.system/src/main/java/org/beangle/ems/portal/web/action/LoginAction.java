/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.portal.web.action;

import org.beangle.commons.lang.Strings;
import org.beangle.security.Securities;
import org.beangle.security.auth.UsernamePasswordAuthentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.web.auth.AuthenticationService;
import org.beangle.struts2.action.ActionSupport;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

public class LoginAction extends ActionSupport {

  private CaptchaService captchaService;

  public static final String LOGIN_FAILURE_COUNT = "loginFailureCount";

  private AuthenticationService authenticationService;

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

  protected boolean shouldLogin() {
    String username = get("username");
    String password = get("password");
    if (Strings.isBlank(username) || Strings.isBlank(password)) { return false; }
    if (notFailEnough()) { return true; }
    // 校验验证码
    if (null != captchaService) {
      try {
        String sessionId = getRequest().getSession().getId();
        String captchaText = get("captcha");
        if (Strings.isEmpty(captchaText)) {
          addError(getText("security.EmptyCaptcha"));
          return false;
        }
        Boolean valid = captchaService.validateResponseForID(sessionId, captchaText);
        if (Boolean.FALSE.equals(valid)) {
          addError(getText("security.WrongCaptcha"));
          return false;
        }
      } catch (CaptchaServiceException e) {
        addError(getText("security.WrongCaptcha"));
        return false;
      }
    }
    return true;
  }

  protected String doLogin() {
    String username = get("username");
    String password = get("password");
    if (Strings.isBlank(username) || Strings.isBlank(password)) { return "failure"; }
    username = username.trim();
    UsernamePasswordAuthentication auth = new UsernamePasswordAuthentication(username, password);
    try {
      authenticationService.login(getRequest(), auth);
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

  public void setCaptchaService(CaptchaService captchaService) {
    this.captchaService = captchaService;
  }

  public void setAuthenticationService(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

}
