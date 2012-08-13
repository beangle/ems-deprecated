/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.web.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.NoParameters;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.util.RequestUtils;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.SecurityUtils;
import org.beangle.security.blueprint.data.DataPermission;
import org.beangle.security.blueprint.data.UserProfile;
import org.beangle.security.blueprint.data.service.DataPermissionService;
import org.beangle.security.blueprint.function.service.FuncPermissionService;
import org.beangle.struts2.action.EntityActionSupport;

public abstract class SecurityActionSupport extends EntityActionSupport implements NoParameters {

  protected FuncPermissionService permissionService;

  protected DataPermissionService dataPermissionService;

  protected Resource getResource() {
    String resourceName = SecurityUtils.getResource();
    if (null == resourceName) {
      resourceName = permissionService.extractResource(RequestUtils
          .getServletPath(ServletActionContext.getRequest()));
    }
    return permissionService.getResource(resourceName);
  }

  protected boolean isAdmin() {
    return permissionService.getUserService().isRoot(getUserId());
  }

  protected Object getUserPropertyValue(String name) {
    UserProfile profile = getUserProfile();
    if (null == profile) return null;
    else return dataPermissionService.getPropertyValue(name, profile);
  }

  protected UserProfile getUserProfile() {
    List<UserProfile> profiles = dataPermissionService.getUserProfiles(getUserId(), null);
    return profiles.isEmpty() ? null : profiles.get(0);
  }

  protected DataPermission getDataPermission(String data) {
    Resource resource = getResource();
    return dataPermissionService.getPermission(getUserId(), data,
        (null == resource ? null : resource.getName()));
  }

  protected void applyPermission(OqlBuilder<?> query) {
    dataPermissionService.apply(query, getDataPermission(query.getEntityClass().getName()), getUserProfile());
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

  public void setPermissionService(FuncPermissionService permissionService) {
    this.permissionService = permissionService;
  }

  public void setDataPermissionService(DataPermissionService dataPermissionService) {
    this.dataPermissionService = dataPermissionService;
  }

}
