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
package org.beangle.ems.root.web.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.SessionMap;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.struts2.annotation.Result;
import org.beangle.struts2.annotation.Results;

import com.opensymphony.xwork2.ActionContext;

@Results({ @Result(name = "success", type = "redirectAction", location = "login") })
public class LogoutAction extends SecurityActionSupport {

  public String index() {
    String result = determineTarget(getRequest());
    boolean success = securityHelper.getAuthenticationService().logout(getRequest(), getResponse());
    if (!success) ((SessionMap<?, ?>) ActionContext.getContext().getSession()).clear();
    return result;
  }

  protected String determineTarget(HttpServletRequest request) {
    String target = get("redirect");
    if (null == target) target = "success";
    else target = "redirect:" + target;
    return target;
  }

}
