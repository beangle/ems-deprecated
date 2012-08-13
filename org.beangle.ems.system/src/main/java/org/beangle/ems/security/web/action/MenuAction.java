/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.web.action;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.util.HierarchyEntityUtils;
import org.beangle.commons.transfer.exporter.PropertyExtractor;
import org.beangle.ems.security.helper.MenuPropertyExtractor;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.blueprint.function.FuncPermission;
import org.beangle.security.blueprint.function.FuncResource;
import org.beangle.security.blueprint.nav.Menu;
import org.beangle.security.blueprint.nav.MenuProfile;
import org.beangle.security.blueprint.nav.service.MenuService;

/**
 * 系统模块(菜单)管理响应类
 * 
 * @author 鄂州蚊子 2008-8-4
 */
public class MenuAction extends SecurityActionSupport {

  private MenuService menuService;

  protected void indexSetting() {
    put("profiles", entityDao.getAll(MenuProfile.class));
  }

  protected void editSetting(Entity<?> entity) {
    put("profiles", entityDao.getAll(MenuProfile.class));
    Menu menu = (Menu) entity;
    List<Menu> folders = CollectUtils.newArrayList();
    OqlBuilder<FuncResource> builder = OqlBuilder.from(FuncResource.class, "r");
    if (null != menu.getProfile() && null != menu.getProfile().getId()) {
      MenuProfile profile = entityDao.get(MenuProfile.class, menu.getProfile().getId());
      // 查找可以作为父节点的菜单
      OqlBuilder<Menu> folderBuilder = OqlBuilder.from(Menu.class, "m");
      folderBuilder.where("m.entry is null and m.profile=:profile", profile);
      folderBuilder.orderBy("m.code");
      folders = entityDao.search(folderBuilder);
      if (null != menu.getParent() && !folders.contains(menu.getParent())) folders.add(menu.getParent());
      folders.removeAll(HierarchyEntityUtils.getFamily(menu));
    }
    List<FuncResource> resurces = entityDao.search(builder);
    Set<FuncResource> existResources = menu.getResources();
    if (null != resurces) {
      resurces.removeAll(existResources);
    }
    put("parents", folders);
    put("resources", resurces);
  }

  @Override
  protected String removeAndForward(Collection<?> entities) {
    @SuppressWarnings("unchecked")
    List<Menu> menus = (List<Menu>) entities;
    List<Menu> parents = CollectUtils.newArrayList();
    for (Menu menu : menus) {
      if (null != menu.getParent()) {
        menu.getParent().getChildren().remove(menu);
        parents.add(menu.getParent());
      }
    }
    entityDao.saveOrUpdate(parents);
    return super.removeAndForward(entities);
  }

  protected String saveAndForward(Entity<?> entity) {
    Menu menu = (Menu) entity;
    try {
      List<FuncResource> resources = entityDao.get(FuncResource.class, getAll("resourceId", Long.class));
      menu.getResources().clear();
      menu.getResources().addAll(resources);
      Long newParentId = getLong("parent.id");
      int indexno = getInteger("indexno");
      Menu parent = null;
      if (null != newParentId) {
        parent = entityDao.get(Menu.class, newParentId);
      }
      menuService.move(menu, parent, indexno);
      entityDao.saveOrUpdate(menu);
    } catch (Exception e) {
      e.printStackTrace();
      return forward(ERROR);
    }
    return redirect("search", "info.save.success");
  }

  /**
   * 禁用或激活一个或多个模块
   * 
   * @return
   */
  public String activate() {
    Long[] menuIds = getIds(getShortName());
    Boolean enabled = getBoolean("isActivate");
    if (null == enabled) {
      enabled = Boolean.TRUE;
    }
    List<Menu> menus = entityDao.get(Menu.class, menuIds);
    for (Menu menu : menus) {
      menu.setEnabled(enabled);
    }
    entityDao.saveOrUpdate(menus);
    return redirect("search", "info.save.success");
  }

  /**
   * 打印预览功能列表
   * 
   * @return
   */
  public String preview() {
    OqlBuilder<Menu> query = OqlBuilder.from(Menu.class, "menu");
    populateConditions(query);
    query.orderBy("menu.code asc");
    put("menus", entityDao.search(query));

    query.cleanOrders();
    query.select("max(length(menu.code)/2)");
    List<?> rs = entityDao.search(query);
    put("depth", rs.get(0));
    return forward();
  }

  @Override
  public String info() {
    Long entityId = getId(getShortName());
    if (null == entityId) {
      logger.warn("cannot get paremeter {}Id or {}.id", getShortName(), getShortName());
    }
    Menu menu = (Menu) getModel(getEntityName(), entityId);
    put(getShortName(), menu);
    if (!menu.getResources().isEmpty()) {
      OqlBuilder<FuncPermission> roleQuery = OqlBuilder.from(FuncPermission.class, "auth");
      roleQuery.where("auth.resource in(:resources)", menu.getResources()).select("distinct auth.role");
      put("roles", entityDao.search(roleQuery));
    }
    return forward();
  }

  public String xml() {
    put("resources", entityDao.getAll(FuncResource.class));
    put("menuProfiles", entityDao.getAll(MenuProfile.class));
    return forward();
  }

  protected PropertyExtractor getPropertyExtractor() {
    return new MenuPropertyExtractor(getTextResource());
  }

  @Override
  protected String getEntityName() {
    return Menu.class.getName();
  }

  public void setMenuService(MenuService menuService) {
    this.menuService = menuService;
  }

}
