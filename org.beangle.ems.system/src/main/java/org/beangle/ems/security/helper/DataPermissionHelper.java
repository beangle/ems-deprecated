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

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.lang.Strings;
import org.beangle.security.blueprint.data.Profile;
import org.beangle.security.blueprint.data.Property;
import org.beangle.security.blueprint.data.RoleProfile;
import org.beangle.security.blueprint.data.UserProfile;
import org.beangle.security.blueprint.data.model.RoleProfileBean;
import org.beangle.security.blueprint.data.model.UserProfileBean;
import org.beangle.security.blueprint.data.service.DataPermissionService;
import org.beangle.security.blueprint.model.RoleBean;
import org.beangle.security.blueprint.model.UserBean;
import org.beangle.struts2.helper.ContextHelper;

public class DataPermissionHelper {

  final EntityDao entityDao;

  final DataPermissionService dataPermissionService;

  public static String getProfileEntity(String type) {
    if (type.equals("user")) return UserProfile.class.getName();
    else return RoleProfile.class.getName();
  }

  public static Profile newProfile(String type, Long holderId) {
    Profile profile = null;
    if (type.equals("user")) {
      UserProfileBean up = new UserProfileBean();
      up.setUser(new UserBean(holderId));
      profile = up;
    } else {
      RoleProfileBean rp = new RoleProfileBean();
      rp.setRole(new RoleBean(holderId.intValue()));
      profile = rp;
    }
    return profile;
  }

  public DataPermissionHelper(EntityDao entityDao, DataPermissionService dataPermissionService) {
    super();
    this.entityDao = entityDao;
    this.dataPermissionService = dataPermissionService;
  }

  /**
   * 查看限制资源界面
   */
  public void populateInfo(List<? extends Profile> profiles) {
    Map<String, Map<String, Object>> fieldMaps = CollectUtils.newHashMap();
    for (Profile profile : profiles) {
      Map<String, Object> aoFields = CollectUtils.newHashMap();
      for (Property property : profile.getProperties()) {
        String value = property.getValue();
        if (Strings.isNotEmpty(value)) {
          if (null == property.getField().getSource()) {
            aoFields.put(property.getField().getName(), value);
          } else if (value.equals("*")) {
            aoFields.put(property.getField().getName(), "不限");
          } else {
            aoFields.put(property.getField().getName(),
                dataPermissionService.getPropertyValue(property.getField(), profile));
          }
        }
      }
      fieldMaps.put(profile.getId().toString(), aoFields);
    }
    ContextHelper.put("profiles", profiles);
    ContextHelper.put("fieldMaps", fieldMaps);
  }

}
