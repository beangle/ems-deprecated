/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.nav.model;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.collection.CollectUtils;
import org.beangle.dao.pojo.LongIdObject;
import org.beangle.ems.security.Role;
import org.beangle.ems.security.nav.Menu;
import org.beangle.ems.security.nav.MenuProfile;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 菜单配置
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.ems.security.nav.MenuProfile")
@Cacheable
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
public class MenuProfileBean extends LongIdObject implements MenuProfile {

	private static final long serialVersionUID = 9147563981118270960L;

	/** 菜单配置名称 */
	@NotNull
	@Size(max = 50)
	@Column(unique = true)
	private String name;

	/**菜单列表*/
	@OneToMany(mappedBy = "profile")
	private List<Menu> menus = CollectUtils.newArrayList();

	/** 角色 */
	@NotNull
	@ManyToOne
	private Role role;

	/** 是否启用 */
	@NotNull
	private boolean enabled;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
