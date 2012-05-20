/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.session.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.dao.pojo.LongIdObject;
import org.beangle.ems.security.Role;

/**
 * 角色会话配置
 * 
 * @author chaostone
 */
@Entity(name="org.beangle.ems.security.session.model.SessionProfileBean")
public class SessionProfileBean extends LongIdObject {

	private static final long serialVersionUID = 1999239598984221565L;

	/** 角色 */
	@NotNull
	@ManyToOne
	protected Role role;

	/** 最大在线人数 */
	@NotNull
	protected int capacity;

	/** 单用户的同时最大会话数 */
	@NotNull
	protected int userMaxSessions = 1;

	/** 不操作过期时间(以分为单位) */
	@NotNull
	protected int inactiveInterval;

	public SessionProfileBean() {
		super();
	}

	public SessionProfileBean(Role role, int max, int inactiveInterval) {
		super();
		this.role = role;
		this.capacity = max;
		this.inactiveInterval = inactiveInterval;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int max) {
		this.capacity = max;
	}

	public int getInactiveInterval() {
		return inactiveInterval;
	}

	public void setInactiveInterval(int inactiveInterval) {
		this.inactiveInterval = inactiveInterval;
	}

	public int getUserMaxSessions() {
		return userMaxSessions;
	}

	public void setUserMaxSessions(int maxSessions) {
		this.userMaxSessions = maxSessions;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(role.getName());
		sb.append(":{max=").append(capacity).append(',');
		sb.append("maxSessions=").append(userMaxSessions).append(',');
		sb.append("inactiveInterval=").append(inactiveInterval).append('}');
		return sb.toString();
	}

}
