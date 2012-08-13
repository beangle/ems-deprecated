/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.web.action.data;

import org.beangle.commons.entity.Entity;
import org.beangle.security.blueprint.data.DataField;
import org.beangle.struts2.action.EntityActionSupport;
import org.beangle.struts2.convention.route.Action;

/**
 * 数据限制域元信息配置类
 * 
 * @author chaostone
 * @version $Id: FieldAction.java Apr 13, 2012 10:01:36 PM chaostone $
 */
public class FieldAction extends EntityActionSupport {

  @Override
  protected String getEntityName() {
    return DataField.class.getName();
  }

  @Override
  protected String getShortName() {
    return "field";
  }

  @Override
  protected String saveAndForward(Entity<?> entity) {
    DataField field = (DataField) entity;
    if (entityDao.duplicate(DataField.class, field.getId(), "name", field.getName())) {
      addFlashErrorNow("名称重复");
      return forward(new Action(this, "editField"));
    }
    entityDao.saveOrUpdate(field);
    return redirect("search", "info.save.success");
  }

}
