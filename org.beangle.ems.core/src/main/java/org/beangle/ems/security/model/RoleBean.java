/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.collection.CollectUtils;
import org.beangle.dao.pojo.HierarchyLongIdObject;
import org.beangle.ems.security.Member;
import org.beangle.ems.security.Permission;
import org.beangle.ems.security.Role;
import org.beangle.ems.security.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 角色信息
 * 系统中角色的基本信息和账号信息.
 * 
 * @author dell,chaostone 2005-9-26
 */
@Entity(name = "org.beangle.ems.security.Role")
@Cacheable
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleBean extends HierarchyLongIdObject<Role> implements Role {

	private static final long serialVersionUID = -3404181949500894284L;

	/** 名称 */
	@Size(max = 100)
	@NotNull
	@Column(unique = true)
	private String name;

	/** 关联的用户 */
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	private Set<Member> members = CollectUtils.newHashSet();

	/** 父级组 */
	@ManyToOne
	private Role parent;

	/**
	 * 下级组
	 * 这里不是用orphanRemoval = true 因为会出现下级组移动到别的组，而不是删除在新加入的逻辑
	 */
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	private List<Role> children = CollectUtils.newArrayList();

	/** 创建人 */
	@NotNull
	@ManyToOne
	private User owner;

	/** 备注 */
	protected String remark;

	/** 是否启用 */
	@NotNull
	public boolean enabled = true;

	/** 动态组 */
	public boolean dynamic = false;

	/** 权限 */
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
	protected Set<Permission> permissions = CollectUtils.newHashSet();

	/** 创建时间 */
	protected Date createdAt;

	/** 最后修改时间 */
	protected Date updatedAt;

	public RoleBean() {
		super();
	}

	public RoleBean(Long id) {
		setId(id);
	}

	public RoleBean(Long id, String name) {
		setId(id);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setAuthorities(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Role getParent() {
		return parent;
	}

	public void setParent(Role parent) {
		this.parent = parent;
	}

	public List<Role> getChildren() {
		return children;
	}

	public void setChildren(List<Role> children) {
		this.children = children;
	}

	public Set<Member> getMembers() {
		return members;
	}

	public void setMembers(Set<Member> members) {
		this.members = members;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String toString() {
		return name;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isDynamic() {
		return dynamic;
	}

	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}

	public int compareTo(Role o) {
		return getCode().compareTo(o.getCode());
	}

}
