/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.avatar.service;

import java.io.File;
import java.util.Set;

import org.beangle.collection.page.Page;
import org.beangle.collection.page.PageLimit;
import org.beangle.ems.avatar.Avatar;

/**
 * 照片库
 * 
 * @author chaostone
 */
public interface AvatarBase {

	public String getDescription();

	public Page<String> getAvatarNames(PageLimit pageLimit);

	public Avatar getAvatar(String name);

	public Avatar getDefaultAvatar();

	public Set<String> getTypes();

	boolean containType(String type);

	public boolean isReadOnly();

	public boolean updateAvatar(String name, File file, String type);

	public int updateAvatarBatch(File zipFile);
}
