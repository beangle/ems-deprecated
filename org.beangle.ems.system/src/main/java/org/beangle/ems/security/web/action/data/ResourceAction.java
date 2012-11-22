/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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

package org.beangle.ems.security.web.action.data;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.lang.Strings;
import org.beangle.security.blueprint.data.DataField;
import org.beangle.security.blueprint.data.DataResource;
import org.beangle.security.blueprint.data.DataType;
import org.beangle.security.blueprint.data.model.DataFieldBean;
import org.beangle.security.blueprint.data.model.DataResourceBean;
import org.beangle.struts2.action.EntityActionSupport;
import org.beangle.struts2.convention.route.Action;

/**
 * 数据资源元信息配置类
 * 
 * @author chaostone
 * @version $Id: ResourceAction.java Apr 13, 2012 10:01:36 PM chaostone $
 */
public class ResourceAction extends EntityActionSupport {

  @Override
  protected String getEntityName() {
    return DataResource.class.getName();
  }

  @Override
  protected String getShortName() {
    return "resource";
  }

  @Override
  protected void editSetting(Entity<?> entity) {
    put("types", entityDao.getAll(DataType.class));
  }

  @Override
  protected String saveAndForward(Entity<?> entity) {
    DataResourceBean resource = (DataResourceBean) entity;
    if (null != resource.getName()) {
      if (entityDao.duplicate(DataResourceBean.class, resource.getId(), "name", resource.getName())) {
        addError("名称重复");
        return forward(new Action(this, "edit"));
      }
      Map<String, DataField> fields = CollectUtils.newHashMap();
      for (DataField field : resource.getFields()) {
        fields.put(field.getName(), field);
      }
      for (int i = 0; i <= getInt("fieldCount"); i++) {
        String name = get("field" + i + ".name");
        if (Strings.isBlank(name)) continue;
        DataFieldBean field = (DataFieldBean) fields.remove(name);
        if (null == field) {
          field = populate(DataFieldBean.class, "field" + i);
          field.setResource(resource);
          resource.getFields().add(field);
        } else {
          populate(field, "field" + i);
        }
      }
      resource.getFields().removeAll(fields.values());
      entityDao.saveOrUpdate(resource);
      logger.info("save restrict entity with name {}", resource.getName());
    }
    return redirect("search", "info.save.success");
  }

}
