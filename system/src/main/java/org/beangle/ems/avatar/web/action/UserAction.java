/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.ems.avatar.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.avatar.Avatar;
import org.beangle.ems.avatar.service.AvatarBase;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.blueprint.User;

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
    String userCode = get("user.code");
    if (Strings.isEmpty(userCode)) { return null; }
    List<User> users = entityDao.get(User.class, "code", userCode);
    if (!users.isEmpty()) {
      User user = users.get(0);
      put("user", Strings.concat(userCode, "(", user.getName(), ")"));
    } else {
      put("user", userCode);
    }
    Avatar avatar = avatarBase.getAvatar(userCode);
    put("avatar", avatar);
    return forward();
  }

  /**
   * 只显示头像
   * 
   * @throws IOException
   */
  public String index() throws IOException {
    String userName = get("user.name");
    if (Strings.isEmpty(userName)) { return null; }
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
