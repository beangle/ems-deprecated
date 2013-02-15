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
