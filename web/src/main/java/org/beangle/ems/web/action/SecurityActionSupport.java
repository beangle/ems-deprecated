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

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.util.RequestUtils;
import org.beangle.security.blueprint.Field;
import org.beangle.security.blueprint.Profile;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.SecurityUtils;
import org.beangle.security.blueprint.UserProfile;
import org.beangle.security.blueprint.data.DataPermission;
import org.beangle.security.blueprint.data.service.DataPermissionService;
import org.beangle.security.blueprint.function.FuncResource;
import org.beangle.security.blueprint.function.service.FuncPermissionService;
import org.beangle.security.blueprint.model.UserBean;
import org.beangle.struts2.action.EntityDrivenAction;

public abstract class SecurityActionSupport extends EntityDrivenAction {

  protected FuncPermissionService funcPermissionService;

  protected DataPermissionService dataPermissionService;

  protected FuncResource getResource() {
    String resourceName = SecurityUtils.getResource();
    if (null == resourceName) {
      resourceName = funcPermissionService.extractResource(RequestUtils.getServletPath(ServletActionContext
          .getRequest()));
    }
    return funcPermissionService.getResource(resourceName);
  }

  protected boolean isAdmin() {
    return funcPermissionService.getUserService().isRoot(getUserId());
  }

  @SuppressWarnings({ "unchecked" })
  protected <T> List<T> getUserProperties(String name) {
    Field field = dataPermissionService.getField(name);
    UserBean user = new UserBean(getUserId());
    List<Profile> profiles = dataPermissionService.getUserProfiles(user);
    Set<T> results = CollectUtils.newHashSet();
    for (Profile profile : profiles) {
      Object prop = dataPermissionService.getProperty(profile, field);
      if (prop instanceof Collection<?>) results.addAll((Collection<T>) prop);
      else results.add((T) prop);
    }
    return CollectUtils.newArrayList(results);
  }

  protected DataPermission getDataPermission(String dataResource) {
    Resource resource = getResource();
    return dataPermissionService.getPermission(getUserId(), dataResource,
        (null == resource ? null : resource.getName()));
  }

  protected void applyPermission(OqlBuilder<?> query, UserProfile profile) {
    dataPermissionService.apply(query, getDataPermission(query.getEntityClass().getName()), profile);
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

  public void setFuncPermissionService(FuncPermissionService permissionService) {
    this.funcPermissionService = permissionService;
  }

  public void setDataPermissionService(DataPermissionService dataPermissionService) {
    this.dataPermissionService = dataPermissionService;
  }

}
