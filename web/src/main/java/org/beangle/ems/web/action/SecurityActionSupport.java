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

import java.util.List;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.web.helper.SecurityHelper;
import org.beangle.security.blueprint.Profile;
import org.beangle.security.blueprint.SecurityUtils;
import org.beangle.security.blueprint.function.FuncResource;
import org.beangle.struts2.action.EntityDrivenAction;

public abstract class SecurityActionSupport extends EntityDrivenAction {

  protected SecurityHelper securityHelper;

  protected FuncResource getResource() {
    return securityHelper.getResource();
  }

  protected boolean isAdmin() {
    return securityHelper.getUserService().isRoot(getUserId());
  }

  protected List<Profile> getProfiles() {
    return securityHelper.getProfiles();
  }

  protected <T> List<T> getProperties(String name) {
    return securityHelper.getProperties(name);
  }

  protected void applyPermission(OqlBuilder<?> query) {
    securityHelper.applyPermission(query);
  }

  protected Long getUserId() {
    return SecurityUtils.getUserId();
  }

  protected String getUsername() {
    return SecurityUtils.getUsername();
  }

  protected String getUser() {
    return Strings.concat(SecurityUtils.getUsername(), "(", SecurityUtils.getFullname(), ")");
  }

  public void setSecurityHelper(SecurityHelper securityHelper) {
    this.securityHelper = securityHelper;
  }

}
