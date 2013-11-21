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
package org.beangle.ems.security.web.action;

import org.beangle.commons.entity.Entity;
import org.beangle.security.blueprint.Field;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.struts2.convention.route.Action;

/**
 * 数据限制域元信息配置类
 * 
 * @author chaostone
 * @version $Id: FieldAction.java Apr 13, 2012 10:01:36 PM chaostone $
 */
public class FieldAction extends EntityDrivenAction {

  @Override
  protected String getEntityName() {
    return Field.class.getName();
  }

  @Override
  protected String saveAndForward(Entity<?> entity) {
    Field field = (Field) entity;
    if (entityDao.duplicate(Field.class, field.getId(), "name", field.getName())) {
      addError("名称重复");
      return forward(new Action(this, "edit"));
    }
    entityDao.saveOrUpdate(field);
    return redirect("search", "info.save.success");
  }

}
