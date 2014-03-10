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
