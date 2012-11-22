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

package org.beangle.ems.avatar.web.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.beangle.ems.avatar.Avatar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AvatarUtil {
  private static final Logger logger = LoggerFactory.getLogger(AvatarUtil.class);

  private AvatarUtil() {
    super();
  }

  public static void copyTo(Avatar avatar, HttpServletResponse response) throws IOException {
    if (null == avatar) { return; }
    byte[] oBuff = new byte[1024];
    int iSize = 0;
    response.setContentType("image/" + avatar.getType());
    InputStream inStream = null;
    OutputStream output = null;
    try {
      inStream = avatar.getInputStream();
      output = response.getOutputStream();
      while ((iSize = inStream.read(oBuff)) > 0) {
        output.write(oBuff, 0, iSize);
      }
    } catch (Exception e) {
      logger.error("copy input to output error for XXXXXXXXXX" + avatar.getName(), e);
    } finally {
      if (null != inStream) {
        inStream.close();
      }
      if (null != output) {
        output.close();
      }
    }
  }
}
