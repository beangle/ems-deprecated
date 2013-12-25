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
package org.beangle.ems.web.tag.component;

import java.security.Principal;

import org.beangle.commons.lang.Strings;
import org.beangle.security.access.AuthorityManager;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 用户头像
 * 
 * @author chaostone
 * @version $Id: AvatarImage.java Jul 2, 2011 9:01:20 AM chaostone $
 */
public class AvatarImage extends SecurityUIBean {

  private String username;

  public static final String AvatarResource = "/avatar/user";

  public AvatarImage(ValueStack stack, AuthorityManager authorityManager) {
    super(stack, authorityManager);
  }

  public String getAvatarUrl() {
    return render(Strings.concat(AvatarResource, "?user.name=", username));
  }

  @Override
  protected String getResource() {
    return AvatarResource;
  }

  public void setUser(Object user) {
    if (null == user) {
      this.username = null;
      return;
    }
    if (user instanceof Principal) {
      this.username = ((Principal) user).getName();
    } else {
      this.username = user.toString();
    }
  }

}
