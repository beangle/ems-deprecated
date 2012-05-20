/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.profile;

import org.beangle.dao.pojo.LongIdEntity;

/**
 * @author chaostone
 * @version $Id: Property.java Oct 18, 2011 8:57:24 AM chaostone $
 */
public interface Property extends LongIdEntity {

	public PropertyMeta getMeta();

	public String getValue();

	public void setValue(String value);
}
