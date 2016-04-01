/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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

import org.apache.struts2.ServletActionContext;
import org.beangle.ems.avatar.Avatar;
import org.beangle.ems.avatar.service.AvatarBase;
import org.beangle.ems.web.action.SecurityActionSupport;

/**
 * 查看下载自己的照片
 * 
 * @author chaostone
 */
public class MyAction extends SecurityActionSupport {

  protected AvatarBase avatarBase;

  public String index() throws IOException {
    String userName = getUsername();
    Avatar avatar = avatarBase.getAvatar(userName);
    if (null == avatar) {
      ServletActionContext.getResponse().getWriter().write("without you avatar [" + userName + "]");
    } else {
      AvatarUtil.copyTo(avatar, ServletActionContext.getResponse());
    }
    return null;
  }

  public String info() {
    Avatar avatar = avatarBase.getAvatar(getUsername());
    put("avatar", avatar);
    put("user", getUser());
    return forward();
  }

  public void setAvatarBase(AvatarBase avatarBase) {
    this.avatarBase = avatarBase;
  }

}
