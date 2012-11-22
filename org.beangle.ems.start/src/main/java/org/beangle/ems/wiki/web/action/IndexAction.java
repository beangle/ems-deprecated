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
package org.beangle.ems.wiki.web.action;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.beangle.ems.web.action.SecurityActionSupport;

import com.petebevin.markdown.MarkdownProcessor;

public class IndexAction extends SecurityActionSupport {

  public String index() throws IOException {
    String file = get("file");
    String path = get("path");
    MarkdownProcessor processor = new MarkdownProcessor();
    if (null != file) {
      String raw = FileUtils.readFileToString(new File(file));
      put("file", new File(file));
      put("res", processor.markdown(raw));
    } else if (null != path) {
      StringBuilder sb = new StringBuilder();
      File index = new File(path + "/index.md");
      if (index.exists()) sb.append(processor.markdown(FileUtils.readFileToString(index)));
      File dir = new File(path);
      put("file", dir);
      File[] mds = dir.listFiles();
      Arrays.sort(mds);
      for (File md : mds) {
        if (!md.getName().equals("index.md")) sb.append(processor.markdown(FileUtils.readFileToString(md)));
      }
      put("res", sb.toString());
    } else {
      put("res", "need file= parameter");
    }
    return forward();
  }

  public static void main(String[] args) {

  }
}
