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
package org.beangle.ems.portal.web.action;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.util.HierarchyEntityUtils;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.blueprint.SecurityUtils;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.nav.MenuProfile;
import org.beangle.security.blueprint.nav.service.MenuService;

/**
 * 加载用户登陆信息
 * 
 * @author chaostone
 */
public class HomeAction extends SecurityActionSupport {

  private MenuService menuService;

  public String index() {
    User user = entityDao.get(User.class, getUserId());
    List<MenuProfile> profiles = menuService.getProfiles(user);

    if (profiles.isEmpty()) {
      put("menus", Collections.EMPTY_LIST);
    } else {
      Long profileId = getLong("security.menuProfileId");
      MenuProfile profile = null;
      if (null != profileId) profile = menuService.getProfile(user, profileId);
      else profile = profiles.get(0);
      put("menus", HierarchyEntityUtils.getRoots(menuService.getMenus(profile, user)));
      put("profile", profile);
    }
    put("menuProfiles", profiles);
    put("user", user);
    return forward();
  }

  public String welcome() {
    put("date", new Date(System.currentTimeMillis()));
    put("user", SecurityUtils.getFullname());
    return forward();
  }

  protected MenuProfile getMenuProfile(Long categoryId) {
    OqlBuilder<MenuProfile> query = OqlBuilder.from(MenuProfile.class, "mp");
    query.where("category.id=:categoryId", categoryId).cacheable();
    List<MenuProfile> mps = entityDao.search(query);
    if (mps.isEmpty()) {
      return null;
    } else {
      return mps.get(0);
    }
  }

  public void setMenuService(MenuService menuService) {
    this.menuService = menuService;
  }

}
