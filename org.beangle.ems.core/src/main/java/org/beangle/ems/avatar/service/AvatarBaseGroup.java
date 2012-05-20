/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.avatar.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.beangle.collection.CollectUtils;
import org.beangle.collection.page.Page;
import org.beangle.collection.page.PageLimit;
import org.beangle.collection.page.Pages;
import org.beangle.ems.avatar.Avatar;

/**
 * 组合头像库
 * 
 * @author chaostone
 */
public class AvatarBaseGroup implements AvatarBase {

	private List<AvatarBase> bases = CollectUtils.newArrayList();

	public boolean containType(String type) {
		boolean contains = false;
		for (AvatarBase base : bases) {
			if (base.containType(type)) {
				contains = true;
				break;
			}
		}
		return contains;
	}

	public Avatar getAvatar(String name) {
		Avatar avatar = null;
		for (AvatarBase base : bases) {
			avatar = base.getAvatar(name);
			if (null != avatar) {
				break;
			}
		}
		return avatar;
	}

	public Page<String> getAvatarNames(PageLimit pageLimit) {
		return Pages.emptyPage();
	}

	public Avatar getDefaultAvatar() {
		return null;
	}

	public String getDescription() {
		return "CompositeAvatarBase";
	}

	public Set<String> getTypes() {
		return null;
	}

	public boolean updateAvatar(String name, File file, String type) {
		return false;
	}

	public int updateAvatarBatch(File zipFile) {
		return 0;
	}

	public boolean isReadOnly() {
		return true;
	}

}
