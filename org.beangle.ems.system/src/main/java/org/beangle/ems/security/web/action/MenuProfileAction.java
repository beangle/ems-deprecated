/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.web.action;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.nav.MenuProfile;

public class MenuProfileAction extends SecurityActionSupport {

  protected void editSetting(Entity<?> entity) {
    OqlBuilder<Role> builder = OqlBuilder.from(Role.class, "g");
    builder.orderBy("g.code");
    put("roles", entityDao.search(builder));
  }

  @Override
  protected String getEntityName() {
    return MenuProfile.class.getName();
  }

}
