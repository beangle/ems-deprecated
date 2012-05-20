/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.profile.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.dao.pojo.LongIdObject;
import org.beangle.ems.security.profile.RoleProfile;
import org.beangle.ems.security.profile.RoleProperty;
import org.beangle.ems.security.profile.PropertyMeta;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 角色属性
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.ems.security.profile.RoleProperty")
@Cacheable
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
public class RolePropertyBean extends LongIdObject implements RoleProperty {
	private static final long serialVersionUID = 1L;

	/**值*/
	@Size(max=1000)
	private String value;

	/**属性元*/
	@NotNull
	@ManyToOne
	private PropertyMeta meta;
	
	/**角色属性配置*/
	@NotNull
	@ManyToOne
	private RoleProfile profile;

	public RolePropertyBean() {
		super();
	}

	public RolePropertyBean(RoleProfileBean profile, PropertyMeta meta, String value) {
		super();
		this.profile = profile;
		this.meta = meta;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public PropertyMeta getMeta() {
		return meta;
	}

	public void setMeta(PropertyMeta meta) {
		this.meta = meta;
	}

	public RoleProfile getProfile() {
		return profile;
	}

	public void setProfile(RoleProfile profile) {
		this.profile = profile;
	}

}
