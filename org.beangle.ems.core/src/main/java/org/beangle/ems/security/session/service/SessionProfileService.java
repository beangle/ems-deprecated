/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.session.service;

import java.util.List;

import org.beangle.ems.security.Role;
import org.beangle.ems.security.session.model.SessionProfileBean;

/**
 * @author chaostone
 * @version $Id: CategoryProfileService.java Jul 14, 2011 8:47:30 AM chaostone $
 */
public interface SessionProfileService {

	public void saveOrUpdate(List<SessionProfileBean> profiles);

	public boolean hasProfile(Role role);

}
