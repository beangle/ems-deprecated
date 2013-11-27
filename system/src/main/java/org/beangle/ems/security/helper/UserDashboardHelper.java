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
package org.beangle.ems.security.helper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.function.FuncResource;
import org.beangle.security.blueprint.function.service.FuncPermissionService;
import org.beangle.security.blueprint.nav.Menu;
import org.beangle.security.blueprint.nav.MenuProfile;
import org.beangle.security.blueprint.nav.service.MenuService;
import org.beangle.security.blueprint.service.ProfileService;
import org.beangle.security.blueprint.session.service.SessioninfoLogService;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.struts2.helper.ContextHelper;
import org.beangle.struts2.helper.Params;

/**
 * @author chaostone
 * @version $Id: DashboardHelper.java Nov 3, 2010 5:19:42 PM chaostone $
 */
public class UserDashboardHelper {

  private EntityDao entityDao;

  private SessionRegistry sessionRegistry;

  private FuncPermissionService permissionService;

  private MenuService menuService;

  private ProfileService profileService;

  private SessioninfoLogService sessioninfoLogService;

  public void buildDashboard(User user) {
    ContextHelper.put("user", user);
    populateMenus(user);
    populateSessioninfoLogs(user);
    populateOnlineActivities(user);
    new ProfileHelper(entityDao, profileService).populateInfo(user.getProfiles());
  }

  private void populateOnlineActivities(User user) {
    ContextHelper.put("sessioninfos", sessionRegistry.getSessioninfos(user.getName(), true));
  }

  private void populateSessioninfoLogs(User user) {
    ContextHelper.put("sessioninfoLogs", sessioninfoLogService.getLoggers(user.getName(), 5));
  }

  private void populateMenus(User user) {
    List<?> menuProfiles = menuService.getProfiles(user);
    ContextHelper.put("menuProfiles", menuProfiles);
    Integer menuProfileId = Params.getInt("menuProfileId");
    if (null == menuProfileId && !menuProfiles.isEmpty()) {
      menuProfileId = ((MenuProfile) (menuProfiles.get(0))).getId();
    }
    if (null != menuProfileId) {
      MenuProfile menuProfile = (MenuProfile) entityDao.get(MenuProfile.class, menuProfileId);
      List<Menu> menus = menuService.getMenus(menuProfile, user, user.getProfiles());
      Set<FuncResource> resources = CollectUtils.newHashSet(permissionService.getResources(user));
      Map<Role, List<Menu>> roleMenusMap = CollectUtils.newHashMap();

      for (Role role : user.getRoles()) {
        roleMenusMap.put(role, menuService.getMenus(menuProfile, role, Boolean.TRUE));
      }
      ContextHelper.put("menus", menus);
      ContextHelper.put("roleMenusMap", roleMenusMap);
      ContextHelper.put("resources", resources);
    }
  }

  public void setMenuService(MenuService menuService) {
    this.menuService = menuService;
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  public void setSessionRegistry(SessionRegistry sessionRegistry) {
    this.sessionRegistry = sessionRegistry;
  }

  public void setPermissionService(FuncPermissionService permissionService) {
    this.permissionService = permissionService;
  }

  public void setProfileService(ProfileService profileService) {
    this.profileService = profileService;
  }

  public void setSessioninfoLogService(SessioninfoLogService sessioninfoLogService) {
    this.sessioninfoLogService = sessioninfoLogService;
  }

  public SessioninfoLogService getSessioninfoLogService() {
    return sessioninfoLogService;
  }

}
