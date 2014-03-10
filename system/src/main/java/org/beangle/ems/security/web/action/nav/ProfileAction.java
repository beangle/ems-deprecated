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
package org.beangle.ems.security.web.action.nav;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.nav.MenuProfile;

public class ProfileAction extends SecurityActionSupport {

  protected void editSetting(Entity<?> entity) {
    OqlBuilder<Role> builder = OqlBuilder.from(Role.class, "g");
    builder.orderBy("g.indexno");
    put("roles", entityDao.search(builder));
  }

  @Override
  protected String getEntityName() {
    return MenuProfile.class.getName();
  }

}
