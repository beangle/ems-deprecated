/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service.impl;

import java.util.Collections;
import java.util.List;

import org.beangle.dao.impl.BaseServiceImpl;
import org.beangle.ems.security.profile.PropertyMeta;
import org.beangle.ems.security.service.UserDataProvider;

public class OqlDataProvider extends BaseServiceImpl implements UserDataProvider {

	@SuppressWarnings("unchecked")
	public <T> List<T> getData(PropertyMeta field, String source) {
		try {
			return (List<T>) entityDao.searchHQLQuery(source);
		} catch (Exception e) {
			logger.error("Get data error", e);
		}
		return Collections.emptyList();
	}

	public String getName() {
		return "oql";
	}

}
