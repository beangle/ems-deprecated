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
package org.beangle.ems.security.web.action.data;

import org.beangle.commons.entity.Entity;
import org.beangle.security.blueprint.data.DataResource;
import org.beangle.security.blueprint.data.model.DataResourceBean;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.struts2.convention.route.Action;

/**
 * 数据资源元信息配置类
 * 
 * @author chaostone
 * @version $Id: ResourceAction.java Apr 13, 2012 10:01:36 PM chaostone $
 */
public class ResourceAction extends EntityDrivenAction {

  @Override
  protected String getEntityName() {
    return DataResource.class.getName();
  }

  @Override
  protected String getShortName() {
    return "resource";
  }

  @Override
  protected String saveAndForward(Entity<?> entity) {
    DataResourceBean resource = (DataResourceBean) entity;
    if (null != resource.getName()) {
      if (entityDao.duplicate(DataResourceBean.class, resource.getId(), "name", resource.getName())) {
        addError("名称重复");
        return forward(new Action(this, "edit"));
      }
      // for (int i = 0; i <= getInt("fieldCount"); i++) {
      // String name = get("field" + i + ".name");
      // if (Strings.isBlank(name)) continue;
      // DataFieldBean field = (DataFieldBean) fields.remove(name);
      // if (null == field) {
      // field = populate(DataFieldBean.class, "field" + i);
      // field.setResource(resource);
      // resource.getDimensions().add(field);
      // } else {
      // populate(field, "field" + i);
      // }
      // }
      // resource.getDimensions().removeAll(fields.values());
      entityDao.saveOrUpdate(resource);
      logger.info("save restrict entity with name {}", resource.getName());
    }
    return redirect("search", "info.save.success");
  }

}
