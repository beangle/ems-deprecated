/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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

package org.beangle.ems.system.web.action;

import java.io.File;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.http.mime.MimeTypeProvider;
import org.beangle.commons.lang.Strings;

public class FileMimeType {

  private MimeTypeProvider mimeTypeProvider;

  private Set<String> texts = CollectUtils.newHashSet();

  public FileMimeType(MimeTypeProvider mimeTypeProvider) {
    super();
    texts.add("xml");
    this.mimeTypeProvider = mimeTypeProvider;
  }

  public String getMimeType(File file) {
    String ext = Strings.substringAfterLast(file.getName(), ".");
    String mimeType = mimeTypeProvider.getMimeType(ext, "application/x-msdownload");
    return Strings.replace(mimeType, "/", "-");
  }

  public boolean isTextType(File file) {
    String ext = Strings.substringAfterLast(file.getName(), ".");
    String mimeType = mimeTypeProvider.getMimeType(ext, "application/x-msdownload");
    boolean text = mimeType.contains("text");
    if (!text) {
      return texts.contains(ext);
    } else {
      return text;
    }
  }

}
