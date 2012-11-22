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

import org.beangle.commons.entity.Entity;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.data.DataPermission;
import org.beangle.security.blueprint.data.DataResource;
import org.beangle.security.blueprint.data.model.DataPermissionBean;
import org.beangle.security.blueprint.function.FuncResource;
import org.beangle.struts2.action.EntityActionSupport;
import org.beangle.struts2.convention.route.Action;

/**
 * 数据限制模式元信息配置类
 * 
 * @author chaostone
 * @version $Id: PermissionAction.java Apr 13, 2012 10:01:36 PM chaostone $
 */
public class PermissionAction extends EntityActionSupport {
  @Override
  protected String getEntityName() {
    return DataPermission.class.getName();
  }

  @Override
  protected String getShortName() {
    return "permission";
  }

  @Override
  protected void editSetting(Entity<?> entity) {
    put("roles", entityDao.getAll(Role.class));
    put("funcResources", entityDao.getAll(FuncResource.class));
    put("dataResources", entityDao.getAll(DataResource.class));
  }

  @Override
  protected String saveAndForward(Entity<?> entity) {
    DataPermissionBean pattern = (DataPermissionBean) entity;
    if (entityDao.duplicate(DataPermissionBean.class, pattern.getId(), "remark", pattern.getRemark())) {
      addError("限制模式描述重复");
      return forward(new Action(this, "editPattern"));
    } else {
      entityDao.saveOrUpdate(pattern);
      return redirect("search", "info.save.success");
    }
  }
}
