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
package org.beangle.ems.web.helper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.web.util.RequestUtils;
import org.beangle.security.access.AccessDeniedException;
import org.beangle.security.blueprint.Dimension;
import org.beangle.security.blueprint.Profile;
import org.beangle.security.blueprint.SecurityUtils;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.data.DataPermission;
import org.beangle.security.blueprint.data.service.DataPermissionService;
import org.beangle.security.blueprint.function.FuncResource;
import org.beangle.security.blueprint.function.service.FuncPermissionService;
import org.beangle.security.blueprint.nav.service.MenuService;
import org.beangle.security.blueprint.service.ProfileService;
import org.beangle.security.blueprint.service.UserService;
import org.beangle.security.web.auth.AuthenticationService;

import com.opensymphony.xwork2.ActionContext;

public class SecurityHelper {

  private static final String ProfileIdSessionAttributeName = "security.profileId";

  private FuncPermissionService funcPermissionService;

  private DataPermissionService dataPermissionService;

  private ProfileService profileService;

  private UserService userService;

  private MenuService menuService;

  private AuthenticationService authenticationService;

  public List<Profile> getProfiles() {
    return getProfiles(userService.get(getUserId()), getResource());
  }

  public Profile getSessionProfile() {
    Object profileId = ActionContext.getContext().getSession().get(ProfileIdSessionAttributeName);
    if (null == profileId) return null;
    else return profileService.get((Long) profileId);
  }

  public void setSessionProfile(Profile profile) {
    if (null == profile) {
      ActionContext.getContext().getSession().remove(ProfileIdSessionAttributeName);
    } else {
      ActionContext.getContext().getSession().put(ProfileIdSessionAttributeName,
          PropertyUtils.getProperty(profile, "id"));
    }
  }

  private List<Profile> getProfiles(User user, FuncResource resource) {
    List<Profile> profiles = profileService.getProfiles(user, resource);
    Object profileId = ActionContext.getContext().getSession().get(ProfileIdSessionAttributeName);
    if (null == profileId) return profiles;
    else {
      Profile sessionProfile = null;
      for (Profile p : profiles) {
        if (PropertyUtils.getProperty(p, "id").equals(profileId)) {
          sessionProfile = p;
          break;
        }
      }
      if (null == sessionProfile) {
        throw new AccessDeniedException(resource.getName(), "error.security.errorprofile");
      } else return Collections.singletonList(sessionProfile);
    }
  }

  public FuncResource getResource() {
    String resourceName = SecurityUtils.getResource();
    if (null == resourceName) {
      resourceName = funcPermissionService
          .extractResource(RequestUtils.getServletPath(ServletActionContext.getRequest()));
    }
    return funcPermissionService.getResource(resourceName);
  }

  protected boolean isAdmin() {
    return funcPermissionService.getUserService().isRoot(getUserId());
  }

  @SuppressWarnings({ "unchecked" })
  public <T> List<T> getProperties(String name) {
    List<Profile> profiles = getProfiles(userService.get(getUserId()), getResource());
    Dimension field = profileService.getDimension(name);
    Set<T> results = CollectUtils.newHashSet();
    for (Profile profile : profiles) {
      Object prop = profileService.getProperty(profile, field);
      if (prop instanceof Collection<?>) results.addAll((Collection<T>) prop);
      else results.add((T) prop);
    }
    return CollectUtils.newArrayList(results);
  }

  protected Long getUserId() {
    return SecurityUtils.getUserId();
  }

  public void applyPermission(OqlBuilder<?> query) {
    User user = userService.get(getUserId());

    if (userService.isRoot(user)) return;

    DataPermission dp = dataPermissionService.getPermission(user, query.getEntityClass().getName(),
        SecurityUtils.getResource());
    if (null == dp) return;
    List<Profile> profiles = getProfiles(user, getResource());

    if (profiles.isEmpty())
      throw new AccessDeniedException(SecurityUtils.getResource(), "error.security.errorprofile");

    dataPermissionService.apply(query, dp, profiles);
  }

  public void setFuncPermissionService(FuncPermissionService funcPermissionService) {
    this.funcPermissionService = funcPermissionService;
  }

  public void setDataPermissionService(DataPermissionService dataPermissionService) {
    this.dataPermissionService = dataPermissionService;
  }

  public void setProfileService(ProfileService profileService) {
    this.profileService = profileService;
  }

  public FuncPermissionService getFuncPermissionService() {
    return funcPermissionService;
  }

  public DataPermissionService getDataPermissionService() {
    return dataPermissionService;
  }

  public ProfileService getProfileService() {
    return profileService;
  }

  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public MenuService getMenuService() {
    return menuService;
  }

  public void setMenuService(MenuService menuService) {
    this.menuService = menuService;
  }

  public AuthenticationService getAuthenticationService() {
    return authenticationService;
  }

  public void setAuthenticationService(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

}
