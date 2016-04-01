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

import org.beangle.commons.collection.page.PageLimit;
import org.hibernate.dialect.Dialect;
import org.springframework.jdbc.core.JdbcTemplate;

public class SqlService extends JdbcTemplate {

  Dialect dialect;

  public int count(String sql) {
    String countSql = "select count(*) from (" + sql + ")";
    return queryForInt(countSql);
  }

  @SuppressWarnings("deprecation")
  public String getLimitString(String sql, PageLimit limit) {
    try {
      int offset = (limit.getPageNo() - 1) * limit.getPageSize();
      String newSql = dialect.getLimitString(sql, offset, limit.getPageSize());
      StringBuilder sb = new StringBuilder(newSql);
      int index = sb.lastIndexOf("?");
      if (-1 != index) sb.replace(index, index + 1, String.valueOf(limit.getPageSize()));

      index = sb.lastIndexOf("?");
      if (-1 != index) sb.replace(index, index + 1, String.valueOf(offset));

      return sb.toString();
    } catch (Exception e) {
      throw new RuntimeException("cannot limit sql:" + sql, e);
    }
  }

  public Dialect getDialect() {
    return dialect;
  }

  public void setDialect(Dialect dialect) {
    this.dialect = dialect;
  }

}
