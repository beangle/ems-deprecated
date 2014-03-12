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

import java.io.File;

import org.beangle.commons.lang.Strings;
import org.beangle.ems.avatar.Avatar;
import org.beangle.ems.avatar.service.AvatarBase;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.struts2.convention.route.Action;

/**
 * 上传照片
 * 
 * @author chaostone
 */
public class MyUploadAction extends SecurityActionSupport {

  protected AvatarBase avatarBase;

  public String index() {
    Avatar avatar = avatarBase.getAvatar(getUsername());
    put("avatar", avatar);
    put("user", getUser());
    return forward();
  }

  public String upload() throws Exception {
    Object[] files = getAll("avatar");
    if (null == files || files.length == 0) return redirect(new Action(MyAction.class, "info"),
        "error.nofile");
    String type = Strings.substringAfter(get("avatarFileName"), ".");

    if (files.length > 0 && null != type && avatarBase.containType(type)) {
      avatarBase.updateAvatar(getUsername(), (File) files[0], type);
      return redirect(new Action(MyAction.class, "info"), "info.upload.success");
    } else {
      return redirect(new Action(MyAction.class, "info"), "info.save.failure");
    }
  }

  public void setAvatarBase(AvatarBase avatarBase) {
    this.avatarBase = avatarBase;
  }

}
