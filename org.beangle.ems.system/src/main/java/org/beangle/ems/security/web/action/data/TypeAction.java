/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.web.action.data;

import org.beangle.commons.entity.Entity;
import org.beangle.security.blueprint.data.DataType;
import org.beangle.struts2.action.EntityActionSupport;
import org.beangle.struts2.convention.route.Action;

/**
 * 数据类型配置类
 * 
 * @author chaostone
 * @version $Id: TypeAction.java Apr 13, 2012 10:01:36 PM chaostone $
 */
public class TypeAction extends EntityActionSupport {

  @Override
  protected String getEntityName() {
    return DataType.class.getName();
  }

  @Override
  protected String saveAndForward(Entity<?> entity) {
    DataType type = (DataType) entity;
    if (entityDao.duplicate(DataType.class, type.getId(), "name", type.getName())) {
      addFlashErrorNow("名称重复");
      return forward(new Action(this, "edit"));
    }

    try {
      Class.forName(type.getTypeName());
    } catch (ClassNotFoundException e) {
      addFlashErrorNow("不能加载声明的类型");
      return forward(new Action(this, "edit"));
    }

    entityDao.saveOrUpdate(type);
    return redirect("search", "info.save.success");
  }

}
