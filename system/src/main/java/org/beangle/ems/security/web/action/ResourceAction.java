/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.transfer.exporter.PropertyExtractor;
import org.beangle.ems.security.helper.ResourcePropertyExtractor;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.blueprint.Permission;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.function.FuncResource;
import org.beangle.security.blueprint.function.service.internal.CacheableAuthorityManager;
import org.beangle.security.blueprint.nav.Menu;
import org.beangle.struts2.convention.route.Action;

/**
 * 系统模块管理响应类
 * 
 * @author chaostone 2005-10-9
 */
public class ResourceAction extends SecurityActionSupport {

  private CacheableAuthorityManager authorityManager;

  /**
   * 禁用或激活一个或多个模块
   */
  public String activate() {
    Integer[] resourceIds = getIntIds("resource");
    Boolean enabled = getBoolean("enabled");
    if (null == enabled) {
      enabled = Boolean.FALSE;
    }
    securityHelper.getFuncPermissionService().updateState(resourceIds, enabled.booleanValue());
    authorityManager.refreshCache();
    return redirect("search", "info.save.success");
  }

  @Override
  public String index() {
    return forward(new Action(this, "search"));
  }

  protected String saveAndForward(Entity<?> entity) {
    Resource resource = (Resource) entity;
    if (null != resource) {
      if (entityDao.duplicate(Resource.class, resource.getId(), "name", resource.getName())) { return redirect(
          "edit", "error.notUnique"); }
    }
    entityDao.saveOrUpdate(resource);
    authorityManager.refreshCache();

    logger.info("save resource success {}", resource.getTitle());
    return redirect("search", "info.save.success");
  }

  public String info() {
    Integer entityId = getIntId(getShortName());
    Entity<?> entity = getModel(getEntityName(), entityId);
    OqlBuilder<Menu> query = OqlBuilder.from(Menu.class, "menu");
    query.join("menu.resources", "r").where("r.id=:resourceId", entity.getId())
        .orderBy("menu.profile.id,menu.indexno");

    OqlBuilder<Permission> roleQuery = OqlBuilder.from(Permission.class, "auth");
    roleQuery.where("auth.resource=:resource", entity).select("auth.role");
    put(getShortName(), entity);
    put("roles", entityDao.search(roleQuery));
    put("menus", entityDao.search(query));
    return forward();
  }

  public void setAuthorityManager(CacheableAuthorityManager authorityManager) {
    this.authorityManager = authorityManager;
  }

  protected PropertyExtractor getPropertyExtractor() {
    return new ResourcePropertyExtractor(getTextResource());
  }

  @Override
  protected String getEntityName() {
    return FuncResource.class.getName();
  }

  @Override
  protected String getShortName() {
    return "resource";
  }
}
