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
package org.beangle.ems.security.web.action;

import java.util.List;

import org.beangle.commons.entity.Entity;
import org.beangle.ems.security.helper.ProfileHelper;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.blueprint.Profile;
import org.beangle.security.blueprint.SecurityUtils;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.UserProfile;
import org.beangle.security.blueprint.model.UserProfileBean;
import org.beangle.security.blueprint.service.UserDataResolver;
import org.beangle.security.blueprint.service.UserService;
import org.beangle.struts2.helper.Params;

/**
 * @author chaostone
 * @version $Id: ProfileAction.java Apr 13, 2012 10:01:12 PM chaostone $
 */
public class ProfileAction extends SecurityActionSupport {
  protected UserDataResolver identifierDataResolver;
  private UserService userService;

  @Override
  protected String getEntityName() {
    return UserProfile.class.getName();
  }

  public String tip() {
    return forward();
  }

  /**
   * 删除数据限制权限
   */
  public String remove() {
    Profile profile = getProfile();
    entityDao.remove(profile);
    return redirect("info", "info.remove.success");
  }

  /**
   * 查看限制资源界面
   */
  public String info() {
    ProfileHelper helper = new ProfileHelper(entityDao, securityHelper.getProfileService());
    Long id = getLong("user.id");
    List<Profile> profiles = entityDao.get(User.class, id).getProfiles();
    helper.populateInfo(profiles);
    return forward();
  }

  public void setIdentifierDataResolver(UserDataResolver identifierDataResolver) {
    this.identifierDataResolver = identifierDataResolver;
  }

  public String save() {
    Profile profile = getProfile();
    ProfileHelper helper = new ProfileHelper(entityDao, securityHelper.getProfileService());
    helper.setIdentifierDataResolver(identifierDataResolver);
    helper.populateSaveInfo(profile, userService.get(SecurityUtils.getUsername()), isAdmin());
    if (profile.getProperties().isEmpty()) {
      if (((Entity<?>) profile).isPersisted()) entityDao.remove(profile);
      return redirect("info", "info.save.success");
    } else {
      entityDao.saveOrUpdate(profile);
      return redirect("info", "info.save.success");
    }
  }

  /**
   * 编辑权限<br>
   */
  public String edit() {
    // 取得各参数的值
    Profile profile = getProfile();
    ProfileHelper helper = new ProfileHelper(entityDao, securityHelper.getProfileService());
    helper.fillEditInfo(profile, userService.get(SecurityUtils.getUsername()), isAdmin());
    return forward();
  }

  private Profile getProfile() {
    Long profileId = getLong("profile.id");
    UserProfileBean profile = null;
    if (null == profileId) {
      profile = new UserProfileBean();
      profile.setUser(entityDao.get(User.class, getLong("user.id")));
    } else profile = entityDao.get(UserProfileBean.class, profileId);
    populate((Entity<?>) profile, getEntityName(), Params.sub("profile"));
    return profile;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

}
