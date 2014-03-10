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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.Page;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.collection.page.SinglePage;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.Throwables;
import org.beangle.ems.database.service.SqlService;
import org.beangle.ems.web.action.SecurityActionSupport;

import com.opensymphony.xwork2.ActionContext;

/**
 * 查询
 * 
 * @author chaostone
 */
public class QueryAction extends SecurityActionSupport {

  private String sql;

  private SqlService sqlService;

  private void processSql() {
    sql = get("sql");
    if (null != sql) sql = sql.trim();
  }

  protected Collection<?> getExportDatas() {
    processSql();
    String newSql = sql;
    if (Strings.isEmpty(newSql)) { return Collections.emptyList(); }
    boolean thisPage = getBool("thisPage");
    if (thisPage) {
      PageLimit limit = getPageLimit();
      newSql = sqlService.getLimitString(sql, limit);
    }
    List<Map<String, Object>> datas = sqlService.queryForList(newSql);
    return datas;
  }

  public String index() {
    DataSource datasource = (DataSource) ActionContext.getContext().getSession().get("datasource");
    sqlService.setDataSource(datasource);
    processSql();
    List<QueryResult> results = CollectUtils.newArrayList();
    put("results", results);
    if (Strings.isEmpty(sql)) { return forward("result"); }
    boolean isBatch = getBool("isBatch");
    if (isBatch) {
      String[] sqls = Strings.split(sql, ";");
      for (String one : sqls) {
        results.add(query(one.trim()));
      }
    } else {
      results.add(query(sql.trim()));
    }
    return forward("result");
  }

  private QueryResult query(String sqlString) {
    QueryResult result = new QueryResult(sqlString);
    try {
      if (sqlString.toLowerCase().startsWith("select")) {
        PageLimit limit = getPageLimit();
        String newSql = sqlService.getLimitString(sqlString, limit);
        List<Map<String, Object>> datas = sqlService.queryForList(newSql);
        Page<?> page = new SinglePage<Map<String, Object>>(limit.getPageNo(), limit.getPageSize(),
            sqlService.count(sqlString), datas);
        if (!datas.isEmpty()) {
          result.setColumns(new ArrayList<String>(datas.get(0).keySet()));
        }
        result.setDatas(page);
      } else {
        int updateCount = sqlService.update(sqlString);
        result.setUpdateCount(updateCount);
      }
    } catch (Exception e) {
      result.setMsg(Throwables.getStackTrace(e));
    }
    @SuppressWarnings("unchecked")
    List<String> history = (List<String>) ActionContext.getContext().getSession().get("sql_history");
    if (null == history) {
      history = CollectUtils.newArrayList();
      ActionContext.getContext().getSession().put("sql_history", history);
    }
    history.add(sqlString);
    return result;
  }

  public String history() {
    List<?> history = (List<?>) ActionContext.getContext().getSession().get("sql_history");
    if (null == history) {
      history = CollectUtils.newArrayList();
      ActionContext.getContext().getSession().put("sql_history", history);
    }
    boolean clear = getBool("clear");
    if (clear) {
      history.clear();
      return redirect("index", "info.save.success");
    }
    return forward();
  }

  public void setSqlService(SqlService sqlService) {
    this.sqlService = sqlService;
  }

}
