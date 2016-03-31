/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.blueprint.Dimension;
import org.beangle.security.blueprint.RoleMember;
import org.beangle.security.blueprint.data.model.DataPermissionBean;
import org.beangle.security.blueprint.function.FuncResource;
import org.beangle.security.blueprint.nav.Menu;
import org.beangle.security.blueprint.nav.MenuProfile;

public class IndexAction extends SecurityActionSupport {

  public String stat() {
    populateUserStat();
    // state menus
    List<MenuProfile> menuProfiles = entityDao.getAll(MenuProfile.class);
    Map<Integer, List<?>> menuStats = CollectUtils.newHashMap();
    for (MenuProfile profile : menuProfiles) {
      OqlBuilder<Menu> menuQuery = OqlBuilder.from(Menu.class, "menu");
      menuQuery.where("menu.profile=:profile", profile).select("menu.enabled,count(*)").groupBy("enabled");
      menuStats.put(profile.getId(), entityDao.search(menuQuery));
    }
    put("menuProfiles", menuProfiles);
    put("menuStats", menuStats);

    // stat resource
    OqlBuilder<FuncResource> resourceQuery = OqlBuilder.from(FuncResource.class, "resource");
    resourceQuery.select("resource.enabled,count(*)").groupBy("enabled");
    put("resourceStat", entityDao.search(resourceQuery));

    // stat dataPermission and restriction
    put("dataPermissionCnt",
        entityDao.search(OqlBuilder.from(DataPermissionBean.class, "p").select("count(*)")));
    put("fieldCnt", entityDao.search(OqlBuilder.from(Dimension.class, "param").select("count(*)")));
    return forward();
  }

  private void populateUserStat() {
    OqlBuilder<RoleMember> userQuery = OqlBuilder.from(RoleMember.class, "gm");
    userQuery.select("gm.role.indexno,gm.role.name,gm.user.enabled,count(*)")
        .groupBy("gm.role.indexno,gm.role.name,gm.user.enabled");
    List<?> datas = entityDao.search(userQuery);
    Map<String, Map<Object, Object>> rs = CollectUtils.newHashMap();
    for (Object data : datas) {
      Object[] roleStat = (Object[]) data;
      String key = roleStat[0] + " " + roleStat[1];
      Map<Object, Object> statusMap = rs.get(key);
      if (null == statusMap) {
        statusMap = CollectUtils.newHashMap();
        rs.put(key, statusMap);
      }
      statusMap.put(roleStat[2], roleStat[3]);
    }
    put("userStat", rs);
  }

}
