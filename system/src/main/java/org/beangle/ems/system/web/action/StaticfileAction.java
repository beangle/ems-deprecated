/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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

import java.io.FileInputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.io.StreamDownloader;
import org.beangle.ems.io.StaticFileLoader;
import org.beangle.struts2.action.ActionSupport;

/**
 * 静态资源下载
 * 
 * @author chaostone
 */
public class StaticfileAction extends ActionSupport {

  private List<StaticFileLoader> loaders = CollectUtils.newArrayList();

  private StreamDownloader streamDownloader;

  public String index() throws Exception {
    String name = get("file");
    String displayName = get("display");
    URL url = null;
    if (Strings.isNotEmpty(name)) {
      for (StaticFileLoader loader : loaders) {
        url = loader.getFile(name);
        if (null != url) {
          break;
        }
      }
      if (null != url) {
	String path = URLDecoder.decode(url.getPath(), "UTF8");
        streamDownloader.download(getRequest(), getResponse(), new FileInputStream(path), name, displayName);
      }
    }
    if (null == url) {
      return "nofound";
    } else {
      return null;
    }
  }

  public List<StaticFileLoader> getLoaders() {
    return loaders;
  }

  public void setLoaders(List<StaticFileLoader> loaders) {
    this.loaders = loaders;
  }

  public void setStreamDownloader(StreamDownloader streamDownloader) {
    this.streamDownloader = streamDownloader;
  }

}
