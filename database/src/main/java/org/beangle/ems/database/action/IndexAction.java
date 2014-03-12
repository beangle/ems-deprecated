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

import java.util.List;

import javax.sql.DataSource;

import org.beangle.ems.database.model.DatasourceBean;
import org.beangle.ems.database.service.DatasourceService;
import org.beangle.ems.web.action.SecurityActionSupport;

import com.opensymphony.xwork2.ActionContext;

public class IndexAction extends SecurityActionSupport {

  private DatasourceService datasourceService;

  private QueryContext getQueryContext() {
    return (QueryContext) ActionContext.getContext().getSession().get("QueryContext");
  }

  public String connect() {
    QueryContext queryContext = getQueryContext();
    Integer datasourceId = getInt("datasource.id");
    if (null == queryContext) {
      if (null == datasourceId) {
        List<?> datasources = entityDao.getAll(DatasourceBean.class);
        put("datasources", datasources);
        return forward();
      } else {
        DataSource datasource = datasourceService.getDatasource(datasourceId);
        DatasourceBean dsbean = entityDao.get(DatasourceBean.class, datasourceId);
        dsbean.getProvider().getDialect();
        ActionContext.getContext().getSession().put("QueryContext", new QueryContext(datasource, dsbean));
        return redirect("index", "info.action.success");
      }
    }
    return forward();
  }

  public String index() {

    return forward();
  }

  public String disconnect() {
    ActionContext.getContext().getSession().remove("QueryContext");
    return redirect("index", "info.action.success");
  }

  public void setDatasourceService(DatasourceService datasourceService) {
    this.datasourceService = datasourceService;
  }
}
