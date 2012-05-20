/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.profile.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.beangle.collection.CollectUtils;
import org.beangle.dao.pojo.LongIdObject;
import org.beangle.ems.security.Role;
import org.beangle.ems.security.profile.RoleProfile;
import org.beangle.ems.security.profile.RoleProperty;
import org.beangle.ems.security.profile.PropertyMeta;

/**
 * 角色属性配置
 * 
 * @author chaostone
 * @version $Id: RoleProfileBean.java Oct 21, 2011 8:39:05 AM chaostone $
 */
@Entity(name = "org.beangle.ems.security.profile.RoleProfile")
public class RoleProfileBean extends LongIdObject implements RoleProfile {

	private static final long serialVersionUID = -9047586316477373803L;

	/** 角色 */
	@ManyToOne
	private Role role;
	
	/**角色自定义属性*/
	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	protected List<RoleProperty> properties = CollectUtils.newArrayList();

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<RoleProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<RoleProperty> properties) {
		this.properties = properties;
	}

	public RoleProperty getProperty(PropertyMeta meta) {
		if (null == properties || properties.isEmpty()) {
			return null;
		} else {
			for (RoleProperty p : properties) {
				if (p.getMeta().equals(meta)) return p;
			}
		}
		return null;
	}

	public void setProperty(PropertyMeta meta, String text) {
		RoleProperty property = getProperty(meta);
		if (null == property) {
			property = new RolePropertyBean(this, meta, text);
			properties.add(property);
		} else {
			property.setValue(text);
		}
	}

	// public RoleProperty getField(String paramName) {
	// for (final RoleProperty param : fields) {
	// if (param.getName().equals(paramName)) { return param; }
	// }
	// return null;
	// }

}
