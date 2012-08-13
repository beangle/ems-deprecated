/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.helper;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.lang.Strings;
import org.beangle.security.blueprint.data.Profile;
import org.beangle.security.blueprint.data.Property;
import org.beangle.security.blueprint.data.UserProfile;
import org.beangle.security.blueprint.data.service.DataPermissionService;
import org.beangle.struts2.helper.ContextHelper;

public class DataPermissionHelper {

  final EntityDao entityDao;

  final DataPermissionService dataPermissionService;

  public DataPermissionHelper(EntityDao entityDao, DataPermissionService dataPermissionService) {
    super();
    this.entityDao = entityDao;
    this.dataPermissionService = dataPermissionService;
  }

  /**
   * 查看限制资源界面
   */
  public void populateInfo( List<UserProfile> profiles) {
    Map<String, Map<String, Object>> fieldMaps = CollectUtils.newHashMap();
    for (Profile profile : profiles) {
      Map<String, Object> aoFields = CollectUtils.newHashMap();
      for (Property property : profile.getProperties()) {
        String value = property.getValue();
        if (Strings.isNotEmpty(value)) {
          if (null == property.getField().getSource()) {
            aoFields.put(property.getField().getName(), value);
          } else {
            aoFields.put(property.getField().getName(),
                dataPermissionService.getPropertyValue(property.getField().getName(), profile));
          }
        }
      }
      fieldMaps.put(profile.getId().toString(), aoFields);
    }
    ContextHelper.put("profiles", profiles);
    ContextHelper.put("fieldMaps", fieldMaps);
  }

}
