/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.web.action.data;

import org.beangle.commons.entity.Entity;
import org.beangle.security.blueprint.data.DataResource;
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
  protected String saveAndForward(Entity<?> entity) {
    DataResourceBean resource = (DataResourceBean) entity;
    if (null != resource.getName()) {
      if (entityDao.duplicate(DataResourceBean.class, resource.getId(), "name", resource.getName())) {
        addFlashErrorNow("名称重复");
        return forward(new Action(this, "editResource"));
      }
      entityDao.saveOrUpdate(resource);
      logger.info("save restrict entity with name {}", resource.getName());
    }
    return redirect("search", "info.save.success");
  }

}
