/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.web.action.data;

import org.beangle.commons.entity.Entity;
import org.beangle.security.blueprint.data.DataType;
import org.beangle.security.blueprint.data.ProfileField;
import org.beangle.security.blueprint.data.service.DataPermissionService;
import org.beangle.struts2.action.EntityActionSupport;
import org.beangle.struts2.convention.route.Action;

/**
 * 数据限制域元信息配置类
 * 
 * @author chaostone
 * @version $Id: FieldAction.java Apr 13, 2012 10:01:36 PM chaostone $
 */
public class FieldAction extends EntityActionSupport {

  private DataPermissionService dataPermissionService;

  @Override
  protected String getEntityName() {
    return ProfileField.class.getName();
  }

  @Override
  protected String getShortName() {
    return "field";
  }

  @Override
  protected void editSetting(Entity<?> entity) {
    put("types", entityDao.getAll(DataType.class));
  }

  @Override
  protected String saveAndForward(Entity<?> entity) {
    ProfileField field = (ProfileField) entity;
    if (entityDao.duplicate(ProfileField.class, field.getId(), "name", field.getName())) {
      addError("名称重复");
      return forward(new Action(this, "edit"));
    }
    try {
      // FIXME FOR general id
      dataPermissionService.getFieldValues(field, 1L);
    } catch (Exception e) {
      e.printStackTrace();
      addError("不能按照给定描述提取数据");
      return forward(new Action(this, "edit"));
    }

    entityDao.saveOrUpdate(field);
    return redirect("search", "info.save.success");
  }

  public void setDataPermissionService(DataPermissionService dataPermissionService) {
    this.dataPermissionService = dataPermissionService;
  }

}
