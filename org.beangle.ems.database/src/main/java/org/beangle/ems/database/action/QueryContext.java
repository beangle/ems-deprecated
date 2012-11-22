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

package org.beangle.ems.database.action;

import javax.sql.DataSource;

import org.beangle.ems.database.model.DatasourceBean;

public class QueryContext {

  private DataSource dataSource;

  private DatasourceBean datasourceBean;

  private String schema;

  public QueryContext() {
    super();
  }

  public QueryContext(DataSource dataSource, DatasourceBean datasourceBean) {
    super();
    this.dataSource = dataSource;
    this.datasourceBean = datasourceBean;
  }

  public DataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public DatasourceBean getDatasourceBean() {
    return datasourceBean;
  }

  public void setDatasourceBean(DatasourceBean datasourceBean) {
    this.datasourceBean = datasourceBean;
  }

  public String getSchema() {
    return schema;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }

}
