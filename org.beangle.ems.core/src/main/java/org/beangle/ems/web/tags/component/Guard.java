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

package org.beangle.ems.web.tags.component;

import java.io.Writer;

import org.beangle.security.access.AuthorityManager;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 对资源和内置区域进行守护
 * 
 * @author chaostone
 */
public class Guard extends SecurityUIBean {

  private String res;

  public Guard(ValueStack stack, AuthorityManager authorityManager) {
    super(stack, authorityManager);
  }

  public boolean end(Writer writer, String body) {
    return end(writer, body, true);
  }

  @Override
  protected String getResource() {
    return res;
  }

  public void setRes(String res) {
    this.res = res;
  }

}
