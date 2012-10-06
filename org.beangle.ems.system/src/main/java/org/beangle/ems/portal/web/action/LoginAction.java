/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.portal.web.action;

import org.beangle.commons.lang.Strings;
import org.beangle.ems.portal.web.helper.RecapchaConfig;
import org.beangle.security.Securities;
import org.beangle.security.auth.UsernamePasswordAuthentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.web.auth.AuthenticationService;
import org.beangle.struts2.action.ActionSupport;
import org.beangle.struts2.view.util.RecaptchaUtils;

public class LoginAction extends ActionSupport {

  private RecapchaConfig recapchaConfig;

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

    if (null != recapchaConfig && null != recapchaConfig.getPrivatekey()) {
      boolean valid = RecaptchaUtils.isValid(getRemoteAddr(), recapchaConfig.getPrivatekey(),
          get("recaptcha_challenge_field"), get("recaptcha_response_field"));
      if (!valid) addError("security.WrongCaptcha");
      return valid;
    } else {
      return true;
    }
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

  public void setRecapchaConfig(RecapchaConfig recapchaConfig) {
    this.recapchaConfig = recapchaConfig;
  }

  public RecapchaConfig getRecapchaConfig() {
    return recapchaConfig;
  }

  public void setAuthenticationService(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

}
