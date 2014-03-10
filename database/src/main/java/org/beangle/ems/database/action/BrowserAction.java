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
package org.beangle.ems.database.action;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.web.action.SecurityActionSupport;

import com.opensymphony.xwork2.ActionContext;

public class BrowserAction extends SecurityActionSupport {

  public String table() {
    // DataSource datasource=getDataSource();
    return forward();
  }

  public String index() throws Exception {
    loadObjects();
    return forward();
  }

  private QueryContext getQueryContext() {
    return (QueryContext) ActionContext.getContext().getSession().get("QueryContext");
  }

  private void loadObjects() throws Exception {
    QueryContext queryConext = getQueryContext();
    DataSource datasource = queryConext.getDataSource();
    // DatasourceBean dsbean = queryConext.getDatasourceBean();
    DatabaseMetaData meta = datasource.getConnection().getMetaData();
    List<String> schemas = CollectUtils.newArrayList();
    ResultSet rs = meta.getSchemas();
    while (rs.next()) {
      schemas.add(rs.getString(1));
    }
    Set<?> tables = CollectUtils.newHashSet();

    // FIXME
    // MetadataLoader loader = new MetadataLoader((Dialect)
    // Class.forName(dsbean.getProvider().getDialect())
    // .newInstance(), meta);
    // if (!schemas.isEmpty()) {
    // String schema = get("schema");
    // if (StringUtils.isNotEmpty(schema)) {
    // queryConext.setSchema(schema);
    // }
    // if (StringUtils.isEmpty(schema)) {
    // schema = queryConext.getSchema();
    // if (StringUtils.isEmpty(schema)) {
    // schema = schemas.get(0);
    // }
    // }
    // put("schema", schema);
    // tables = loader.loadTables(null, schema, false);
    // }
    put("schemas", schemas);
    put("tables", tables);
  }
}
