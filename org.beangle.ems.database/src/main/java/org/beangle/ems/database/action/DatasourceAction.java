/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
    Long datasourceId = getId("datasource");
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
