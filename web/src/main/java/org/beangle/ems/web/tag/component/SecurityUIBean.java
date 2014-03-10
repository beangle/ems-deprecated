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
package org.beangle.ems.web.tag.component;

import java.io.Writer;

import org.beangle.security.access.AuthorityManager;
import org.beangle.security.blueprint.SecurityUtils;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.struts2.view.component.UIBean;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 基于授权访问的bean
 * 
 * @author chaostone
 * @version $Id: SecurityUIBean.java Jul 2, 2011 9:24:56 AM chaostone $
 */
public abstract class SecurityUIBean extends UIBean {

  private AuthorityManager authorityManager;

  public SecurityUIBean(ValueStack stack, AuthorityManager authorityManager) {
    super(stack);
    this.authorityManager = authorityManager;
  }

  public boolean start(Writer writer) {
    return isAuthorize(getResource());
  }

  protected boolean isAuthorize(String res) {
    if (null == res) { return false; }
    int queryIndex = res.indexOf('?');
    if (-1 != queryIndex) res = res.substring(0, queryIndex);
    if ('/' != res.charAt(0)) {
      String refer = SecurityUtils.getResource();
      res = refer.substring(0, refer.lastIndexOf("/")) + "/" + res;
    }
    return authorityManager.isAuthorized(SecurityContextHolder.getContext().getAuthentication(), res);
  }

  protected abstract String getResource();
}
