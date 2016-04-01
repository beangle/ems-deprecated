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
package org.beangle.ems.database.action;

import java.sql.Connection;
import java.util.Map;

import javax.sql.DataSource;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Throwables;
import org.beangle.ems.database.service.DatasourceService;
import org.beangle.ems.web.action.SecurityActionSupport;

public class DatasourceAction extends SecurityActionSupport {

  private DatasourceService datasourceService;

  public String test() {
    Integer datasourceId = getId("datasource",Integer.class);
    DataSource dataSource = datasourceService.getDatasource(datasourceId);
    Map<String, String> driverinfo = CollectUtils.newHashMap();
    Map<String, Object> dbinfo = CollectUtils.newHashMap();
    Map<String, Object> jdbcinfo = CollectUtils.newHashMap();
    Connection con = null;
    try {
      con = dataSource.getConnection();
      if (con != null) {
        java.sql.DatabaseMetaData dm = con.getMetaData();
        driverinfo.put("Driver Name", dm.getDriverName());
        driverinfo.put("Driver Version", dm.getDriverVersion());
        dbinfo.put("Database Name", dm.getDatabaseProductName());
        dbinfo.put("Database Version", dm.getDatabaseProductVersion());
        jdbcinfo.put("JDBC Version", dm.getJDBCMajorVersion() + "." + dm.getJDBCMinorVersion());
        StringBuilder catelogs = new StringBuilder();
        dbinfo.put("Avalilable Catalogs", catelogs);
        java.sql.ResultSet rs = dm.getCatalogs();
        while (rs.next()) {
          catelogs.append(rs.getString(1));
          if (rs.next()) catelogs.append(',');
        }
        rs.close();
      }
    } catch (Exception e) {
      put("exceptionStack", Throwables.getStackTrace(e));
    } finally {
      try {
        if (con != null) con.close();
        con = null;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    put("driverinfo", driverinfo);
    put("dbinfo", dbinfo);
    put("jdbcinfo", jdbcinfo);
    return forward();
  }

  @Override
  protected String getEntityName() {
    return "org.beangle.webapp.database.model.DatasourceBean";
  }

  @Override
  protected String getShortName() {
    return "datasource";
  }

  public void setDatasourceService(DatasourceService dataSourceService) {
    this.datasourceService = dataSourceService;
  }

}
