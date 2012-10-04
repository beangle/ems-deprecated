/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.system.web.action;

import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.context.property.PropertyConfig;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.config.model.PropertyConfigItemBean;
import org.beangle.struts2.action.ActionSupport;

public class PropertyAction extends ActionSupport {

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
    PropertyConfigItemBean config = entityDao.get(PropertyConfigItemBean.class, getLong("config.id"));
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
