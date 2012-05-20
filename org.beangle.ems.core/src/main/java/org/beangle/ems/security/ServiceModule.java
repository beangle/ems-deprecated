/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security;

import org.beangle.context.inject.AbstractBindModule;
import org.beangle.ems.security.nav.service.MenuServiceImpl;
import org.beangle.ems.security.service.CacheableAuthorityManager;
import org.beangle.ems.security.service.DaoUserDetailServiceImpl;
import org.beangle.ems.security.service.impl.AuthorityServiceImpl;
import org.beangle.ems.security.service.impl.CsvDataResolver;
import org.beangle.ems.security.service.impl.IdentifierDataResolver;
import org.beangle.ems.security.service.impl.OqlDataProvider;
import org.beangle.ems.security.service.impl.RoleServiceImpl;
import org.beangle.ems.security.service.impl.UserServiceImpl;
import org.beangle.ems.security.session.service.SessionProfileServiceImpl;
import org.beangle.security.core.session.impl.DbSessionRegistry;

/**
 * 权限缺省服务配置
 * @author chaostone
 * @version $Id: DefaultModule.java Jun 18, 2011 10:21:05 AM chaostone $
 */
public class ServiceModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind("userService", UserServiceImpl.class);
		bind("roleService", RoleServiceImpl.class);
		bind("authorityService", AuthorityServiceImpl.class);
		bind("menuService", MenuServiceImpl.class);
		bind("userDetailService",DaoUserDetailServiceImpl.class);
		bind("authorityManager",CacheableAuthorityManager.class);
		bind(SessionProfileServiceImpl.class).shortName();
		
		bind("sessionRegistry",DbSessionRegistry.class);
		bind(IdentifierDataResolver.class,CsvDataResolver.class,OqlDataProvider.class).shortName();
	}

}
