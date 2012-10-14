/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.avatar.service;

import java.io.File;
import java.util.Set;

import org.beangle.commons.collection.page.Page;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.ems.avatar.Avatar;

/**
 * 照片库
 * 
 * @author chaostone
 */
public interface AvatarBase {

  String getDescription();

  Page<String> getAvatarNames(PageLimit pageLimit);

  Avatar getAvatar(String name);

  Avatar getDefaultAvatar();

  Set<String> getTypes();

  boolean containType(String type);

  boolean isReadOnly();

  boolean updateAvatar(String name, File file, String type);

  int updateAvatarBatch(File zipFile);
}
