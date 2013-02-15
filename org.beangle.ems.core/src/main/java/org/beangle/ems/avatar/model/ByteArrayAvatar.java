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
package org.beangle.ems.avatar.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.beangle.ems.avatar.AvatarException;

public class ByteArrayAvatar extends AbstractAvatar {

  byte[] buf;

  public ByteArrayAvatar() {
    super();
  }

  public ByteArrayAvatar(String name, String type, byte[] buf) {
    super();
    setName(name);
    setType(type);
    this.buf = buf;
  }

  public InputStream getInputStream() throws AvatarException {
    return new ByteArrayInputStream(buf);
  }

  public long getSize() {
    return buf.length;
  }

}
