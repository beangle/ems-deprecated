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
package org.beangle.ems.system.web.action;

import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.config.property.PropertyConfig;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.config.model.PropertyConfigItemBean;
import org.beangle.struts2.action.EntityActionSupport;

public class PropertyAction extends EntityActionSupport {

  private PropertyConfig propertyConfig;

  private EntityDao entityDao;

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  public String bulkEdit() {
    OqlBuilder<PropertyConfigItemBean> builder = OqlBuilder.from(PropertyConfigItemBean.class, "config");
    builder.orderBy("config.name");
    List<PropertyConfigItemBean> configs = entityDao.search(builder);
    put("propertyConfigs", configs);
    Set<String> staticNames = propertyConfig.getNames();
    for (PropertyConfigItemBean config : configs) {
      staticNames.remove(config.getName());
    }
    put("config", propertyConfig);
    put("staticNames", staticNames);
    return forward();
  }

  public String save() {
    List<PropertyConfigItemBean> configs = entityDao.getAll(PropertyConfigItemBean.class);
    Set<String> names = CollectUtils.newHashSet();
    for (PropertyConfigItemBean config : configs) {
      names.add(config.getName());
    }
    String msg = "info.save.success";
    PropertyConfigItemBean newConfig = populate(PropertyConfigItemBean.class, "configNew");
    if (Strings.isNotBlank(newConfig.getName()) && Strings.isNotBlank(newConfig.getValue())
        && !names.contains(newConfig.getName())) {
      entityDao.saveOrUpdate(newConfig);
    }
    propertyConfig.reload();
    return redirect("dynaInfo", msg);
  }

  public String bulkSave() {
    List<PropertyConfigItemBean> configs = entityDao.getAll(PropertyConfigItemBean.class);
    Set<String> names = CollectUtils.newHashSet();
    for (PropertyConfigItemBean config : configs) {
      populate(config, "config" + config.getId());
      names.add(config.getName());
    }
    entityDao.saveOrUpdate(configs);
    propertyConfig.reload();
    return redirect("dynaInfo", "info.save.success");
  }

  public String remove() {
    PropertyConfigItemBean config = entityDao.get(PropertyConfigItemBean.class, getInt("config.id"));
    if (null != config) entityDao.remove(config);
    return redirect("dynaInfo", "info.save.success");
  }

  public String index() {
    OqlBuilder<PropertyConfigItemBean> builder = OqlBuilder.from(PropertyConfigItemBean.class, "config");
    builder.orderBy("config.name");
    List<PropertyConfigItemBean> configs = entityDao.search(builder);
    Set<String> staticNames = propertyConfig.getNames();
    for (PropertyConfigItemBean config : configs) {
      staticNames.remove(config.getName());
    }
    put("config", propertyConfig);
    put("staticNames", staticNames);
    return forward();
  }

  public String dynaInfo() {
    OqlBuilder<PropertyConfigItemBean> builder = OqlBuilder.from(PropertyConfigItemBean.class, "config");
    builder.orderBy("config.name");
    List<PropertyConfigItemBean> configs = entityDao.search(builder);
    put("propertyConfigs", configs);
    put("config", propertyConfig);
    return forward();
  }

  public String newConfig() {
    return "form";
  }

  public void setPropertyConfig(PropertyConfig propertyConfig) {
    this.propertyConfig = propertyConfig;
  }

}
