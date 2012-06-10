/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
