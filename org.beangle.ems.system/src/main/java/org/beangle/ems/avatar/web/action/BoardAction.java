/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.avatar.web.action;

import java.io.File;
import java.util.List;

import org.beangle.commons.collection.page.Page;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.util.archiver.ZipUtils;
import org.beangle.ems.avatar.Avatar;
import org.beangle.ems.avatar.service.AvatarBase;
import org.beangle.security.blueprint.User;
import org.beangle.ems.web.action.SecurityActionSupport;
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
      put("user", Strings.concat(userName, "(", user.getFullname(), ")"));
    } else {
      put("user", userName);
    }
    Avatar avatar = avatarBase.getAvatar(userName);
    put("avatar", avatar);
    return forward();
  }

  public String upload() throws Exception {
    File[] files = (File[]) getAll("avatar");
    String userName = get("user.name");
    if (files.length > 0) {
      String type = Strings.substringAfter(get("avatarFileName"), ".");
      boolean passed = avatarBase.containType(type);
      if (passed) {
        avatarBase.updateAvatar(userName, files[0], type);
      } else {
        addError("不支持的文件类型");
        return forward("info");
      }
    }
    return redirect(new Action(UserAction.class, "info", "&user.name=" + userName), "info.save.success");
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
