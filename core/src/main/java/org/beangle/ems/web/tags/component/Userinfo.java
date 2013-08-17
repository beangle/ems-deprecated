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
package org.beangle.ems.web.tags.component;

import java.io.Writer;

import org.beangle.commons.lang.Strings;
import org.beangle.security.access.AuthorityManager;
import org.beangle.security.blueprint.User;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @version $Id: Userinfo.java Jul 2, 2011 5:46:44 PM chaostone $
 */
public class Userinfo extends SecurityUIBean {

  private User user;

  private String href;

  public Userinfo(ValueStack stack, AuthorityManager authorityManager) {
    super(stack, authorityManager);
  }

  public boolean start(Writer writer) {
    return true;
  }

  @Override
  protected String getResource() {
    if (null == href) {
      this.href = Strings.concat("/security/user!dashboard?user.id=", user.getId().toString());
    }
    return this.href;
  }

  public boolean isDashboardAuthorized() {
    return isAuthorize(getResource());
  }

  public String getDashboardUrl() {
    return render(getResource());
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setHref(String href) {
    this.href = href;
  }

}
