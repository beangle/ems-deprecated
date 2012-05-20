/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service;

import java.util.List;

import org.beangle.ems.security.Role;
import org.beangle.ems.security.User;

/**
 *
 * @author chaostone
 * @version $Id: RoleService.java Jul 29, 2011 1:59:38 AM chaostone $
 */
public interface RoleService {

	/**
	 * 创建一个角色
	 * 
	 * @param creator
	 * @param role
	 */
	public void createRole(User creator, Role role);

	/**
	 * 删除管理者与角色的管理关系，如果该角色为其所创建则彻底删除. 1)超级管理员不能被删除.<br>
	 * 2)如果删除人有超级管理员角色，则可以删除不是自己创建的角色
	 * 
	 * @param manager
	 * @param roles
	 */
	public void removeRole(User manager, List<Role> roles);

	/**
	 * 移动组
	 * 
	 * @param role
	 * @param parent
	 * @param indexno
	 */
	public void moveRole(Role role, Role parent, int indexno);

}
