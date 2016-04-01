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
package org.beangle.ems.database.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.ems.database.model.DatasourceBean;
import org.beangle.ems.database.model.DatasourcePropertyBean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DatasourceService extends BaseServiceImpl {

  private Map<Integer, DataSource> datasources = CollectUtils.newHashMap();

  public DataSource getDatasource(Integer id) {
    DataSource datasource = datasources.get(id);
    if (null == datasource) {
      OqlBuilder<DatasourceBean> builder = OqlBuilder.from(DatasourceBean.class, "ds");
      builder.where("ds.id=:id", id);
      List<DatasourceBean> beans = entityDao.search(builder);
      if (!beans.isEmpty()) {
        DatasourceBean bean = beans.get(0);
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(bean.getUrl());
        ds.setUsername(bean.getUsername());
        ds.setPassword(bean.getPassword());
        ds.setDriverClassName(bean.getDriverClassName());
        Properties properties = new Properties();
        for (DatasourcePropertyBean propertyBean : bean.getProperties()) {
          properties.put(propertyBean.getName(), propertyBean.getValue());
        }
        ds.setConnectionProperties(properties);
        datasource = ds;
      }
      datasources.put(id, datasource);
    }
    return datasource;
  }

}
