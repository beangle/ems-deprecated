/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.action;

import org.beangle.dao.Entity;
import org.beangle.dao.query.builder.OqlBuilder;
import org.beangle.ems.security.Role;
import org.beangle.ems.security.nav.MenuProfile;
import org.beangle.ems.web.action.SecurityEntityActionSupport;

public class MenuProfileAction extends SecurityEntityActionSupport {

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
