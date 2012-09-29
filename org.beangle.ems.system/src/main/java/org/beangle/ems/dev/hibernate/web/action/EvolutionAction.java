/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.dev.hibernate.web.action;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.struts2.action.ActionSupport;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * db进化sql语句相应类
 * 
 * @author chaostone
 */
public class EvolutionAction extends ActionSupport {

  JdbcTemplate jdbcTemplate;
  String databaseName;
  PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

  public String index() throws IOException {
    Resource[] resources = resolver.getResources("classpath*:db/" + databaseName + "/evolutions/**/*.sql");
    String version = get("version");
    if (null != version && !version.endsWith("/")) version += "/";
    if (null != version && !version.startsWith("/")) version = "/" + version;
    if (Strings.isNotBlank(version)) {
      List<Resource> results = CollectUtils.newArrayList();
      for (Resource resource : resources) {
        if (resource.getURI().toString().contains(version)) {
          results.add(resource);
        }
      }
      put("resources", results);
    } else {
      put("resources", resources);
    }
    put("databaseName",databaseName);
    return forward();
  }

  public String display() throws IOException {
    String sql = get("sqlfile");
    Resource resource = resolver.getResource(sql);
    InputStream is = null;
    try {
      is = resource.getURL().openStream();
      put("sqllines", IOUtils.readLines(is));
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != is) is.close();
    }
    put("databaseName",databaseName);
    put("resource", resource);
    return forward();
  }

  public String exec() throws IOException {
    String sql = get("sql");
    if (null == sql) {
      String sqlfile = get("sqlfile");
      InputStream is = null;
      Resource resource = resolver.getResource(sqlfile);
      StringBuilder sb = new StringBuilder();
      try {
        is = resource.getURL().openStream();
        put("resource", resource);
        List<String> sqllines = IOUtils.readLines(is);
        for (String line : sqllines) {
          sb.append(line).append("\n");
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (null != is) is.close();
      }
      sql = sb.toString();
    }
    String[] statements = Strings.split(sql, ";");
    List<String> sqls=CollectUtils.newArrayList();
    Map<String, String> results = CollectUtils.newHashMap();
    for (String statement : statements) {
      if (Strings.isBlank(statement)) continue;
      try {
        sqls.add(statement);
        jdbcTemplate.execute(statement);
        results.put(statement, "success");
      } catch (Exception e) {
        results.put(statement, e.getMessage());
      }
    }
    put("sqls", sqls);
    put("results", results);
    put("databaseName",databaseName);
    return forward("results");
  }

  public void setDatasource(DataSource datasource) throws SQLException {
    this.jdbcTemplate = new JdbcTemplate(datasource);
    Connection connection = datasource.getConnection();
    databaseName = connection.getMetaData().getDatabaseProductName().toLowerCase();
    connection.close();
  }

}
