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

import java.io.File;
import java.util.List;

import org.beangle.commons.collection.page.Page;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.avatar.Avatar;
import org.beangle.ems.avatar.service.AvatarBase;
import org.beangle.ems.util.ZipUtils;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.blueprint.User;
import org.beangle.struts2.convention.route.Action;

/**
 * 用户头像管理
 * 
 * @author chaostone
 */
public class BoardAction extends SecurityActionSupport {

  protected AvatarBase avatarBase;

  public String index() {
    Page<?> names = avatarBase.getAvatarNames(getPageLimit());
    put("names", names);
    put("avatarBase", avatarBase);
    return forward();
  }

  /**
   * 查看照片信息
   */
  public String info() {
    String userName = get("user.name");
    if (Strings.isEmpty(userName)) { return null; }
    List<User> users = entityDao.get(User.class, "name", userName);
    if (!users.isEmpty()) {
      User user = users.get(0);
      put("user", Strings.concat(userName, "(", user.getName(), ")"));
    } else {
      put("user", userName);
    }
    Avatar avatar = avatarBase.getAvatar(userName);
    put("avatar", avatar);
    return forward();
  }

  public String upload() throws Exception {
    String userName = get("user.name");
    Object[] files = getAll("avatar");
    if (null == files || files.length == 0) return redirect("info", "error.nofile", "&user.name=" + userName);

    String type = Strings.substringAfter(get("avatarFileName"), ".");
    if (files.length > 0 && null != type && avatarBase.containType(type)) {
      avatarBase.updateAvatar(userName, (File) files[0], type);
      return redirect(new Action(UserAction.class, "info", "&user.name=" + userName), "info.save.success");
    } else {
      addError("不支持的文件类型");
      return forward("info");
    }
  }

  public String uploadBatch() throws Exception {
    File file = get("avatar", File.class);
    String msg = "info.save.success";
    if (null == file) {
      msg = "error.nofile";
    } else {
      if (ZipUtils.isZipFile(file)) {
        avatarBase.updateAvatarBatch(file);
      } else {
        msg = "error.wrongzipfile";
      }
    }
    return redirect("index", msg);
  }

  public void setAvatarBase(AvatarBase avatarBase) {
    this.avatarBase = avatarBase;
  }

}
