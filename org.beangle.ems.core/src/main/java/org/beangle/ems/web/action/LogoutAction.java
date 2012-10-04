/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.web.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.SessionMap;
import org.beangle.security.web.auth.AuthenticationService;
import org.beangle.struts2.action.ActionSupport;

import com.opensymphony.xwork2.ActionContext;

public class LogoutAction extends ActionSupport {

  private AuthenticationService authenticationService;

  public String index() {
    String result = determineTarget(getRequest());
    boolean success = authenticationService.logout(getRequest(), getResponse());
    if (!success) ((SessionMap<?, ?>) ActionContext.getContext().getSession()).clear();
    return result;
  }

  protected String determineTarget(HttpServletRequest request) {
    String target = get("logoutSuccessUrl");
    if (null == target) target = "success";
    else target = "redirect:" + target;
    return target;
  }

  public void setAuthenticationService(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

}
