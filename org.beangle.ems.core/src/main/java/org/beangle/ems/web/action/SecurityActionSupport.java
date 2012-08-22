/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.web.action;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.NoParameters;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.util.RequestUtils;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.SecurityUtils;
import org.beangle.security.blueprint.data.DataPermission;
import org.beangle.security.blueprint.data.ProfileField;
import org.beangle.security.blueprint.data.UserProfile;
import org.beangle.security.blueprint.data.service.DataPermissionService;
import org.beangle.security.blueprint.function.service.FuncPermissionService;
import org.beangle.security.blueprint.model.UserBean;
import org.beangle.struts2.action.EntityActionSupport;

public abstract class SecurityActionSupport extends EntityActionSupport implements NoParameters {

  protected FuncPermissionService funcPermissionService;

  protected DataPermissionService dataPermissionService;

  protected Resource getResource() {
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
  protected <T> List<T> getUserPropertyValues(String name) {
    ProfileField field = dataPermissionService.getProfileField(name);
    UserBean user = new UserBean(getUserId());
    List<UserProfile> profiles = dataPermissionService.getUserProfiles(user);
    Set<T> results = CollectUtils.newHashSet();
    for (UserProfile profile : profiles) {
      Object prop = dataPermissionService.getPropertyValue(field, profile);
      if (prop instanceof Collection<?>) {
        results.addAll((Collection<T>) prop);
      } else {
        results.add((T) prop);
      }
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
