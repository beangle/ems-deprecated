/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.avatar.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.ems.avatar.Avatar;
import org.beangle.ems.avatar.service.AvatarBase;
import org.beangle.ems.security.User;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.lang.StrUtils;

/**
 * 管理照片
 * 
 * @author chaostone
 */
public class UserAction extends SecurityActionSupport {

	protected AvatarBase avatarBase;

	/**
	 * 查看照片信息
	 */
	public String info() {
		String userName = get("user.name");
		if (StringUtils.isEmpty(userName)) { return null; }
		List<User> users = entityDao.get(User.class, "name", userName);
		if (!users.isEmpty()) {
			User user = users.get(0);
			put("user", StrUtils.concat(userName, "(", user.getFullname(), ")"));
		} else {
			put("user", userName);
		}
		Avatar avatar = avatarBase.getAvatar(userName);
		put("avatar", avatar);
		return forward();
	}

	/**
	 * 只显示头像
	 * 
	 * @return
	 * @throws IOException
	 */
	public String index() throws IOException {
		String userName = get("user.name");
		if (StringUtils.isEmpty(userName)) { return null; }
		Avatar avatar = avatarBase.getAvatar(userName);
		if (null == avatar) {
			avatar = avatarBase.getDefaultAvatar();
		}
		AvatarUtil.copyTo(avatar, ServletActionContext.getResponse());
		return null;
	}

	public String show() throws IOException {
		return index();
	}

	public void setAvatarBase(AvatarBase avatarBase) {
		this.avatarBase = avatarBase;
	}
}
