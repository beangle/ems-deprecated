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

package org.beangle.ems.business.web.action;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.log.BusinessLog;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.struts2.helper.QueryHelper;

/**
 * 业务日志相应类
 * 
 * @author chaostone
 * @version $Id: LogAction.java Jun 27, 2011 7:34:59 PM chaostone $
 */
public class LogAction extends SecurityActionSupport {

  @Override
  protected String getEntityName() {
    return BusinessLog.class.getName();
  }

  @SuppressWarnings("unchecked")
  @Override
  protected  OqlBuilder<BusinessLog> getQueryBuilder() {
    OqlBuilder<BusinessLog> builder = OqlBuilder.from(BusinessLog.class, "log");
    populateConditions(builder);
    QueryHelper.addDateIntervalCondition(builder, "operateAt", "beginDate", "endDate");
    builder.limit(getPageLimit());
    String orderBy = get("orderBy");
    if (Strings.isEmpty(orderBy)) {
      orderBy = "log.operateAt desc";
    }
    builder.orderBy(orderBy);
    return builder;
  }

}
