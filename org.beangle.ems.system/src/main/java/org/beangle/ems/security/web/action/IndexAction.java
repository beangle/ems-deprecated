/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.web.action;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.blueprint.Member;
import org.beangle.security.blueprint.data.DataField;
import org.beangle.security.blueprint.data.model.DataPermissionBean;
import org.beangle.security.blueprint.function.FuncResource;
import org.beangle.security.blueprint.nav.Menu;
import org.beangle.security.blueprint.nav.MenuProfile;

public class IndexAction extends SecurityActionSupport {

  public String stat() {
    populateUserStat();
    // state menus
    List<MenuProfile> menuProfiles = entityDao.getAll(MenuProfile.class);
    Map<Long, List<?>> menuStats = CollectUtils.newHashMap();
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
    put("dataPermissionStat", entityDao.search(OqlBuilder.from(DataPermissionBean.class, "p").select("count(*)")));
    put("propertyMetaStat", entityDao.search(OqlBuilder.from(DataField.class, "param").select("count(*)")));
    return forward();
  }

  private void populateUserStat() {
    OqlBuilder<Member> userQuery = OqlBuilder.from(Member.class, "gm");
    userQuery.select("gm.role.code,gm.role.name,gm.user.enabled,count(*)").groupBy(
        "gm.role.name,gm.user.enabled");
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
