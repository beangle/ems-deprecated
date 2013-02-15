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
package org.beangle.ems.security.web;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.beangle.ems.avatar.web.action.BoardAction;
import org.beangle.ems.avatar.web.action.MyUploadAction;
import org.beangle.ems.security.helper.UserDashboardHelper;
import org.beangle.ems.security.web.action.*;

public final class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    // security
    bind(PermissionAction.class, RoleAction.class, IndexAction.class, MyAction.class, PasswordAction.class,
        ResourceAction.class, UserAction.class);

    bind(org.beangle.ems.security.web.action.session.LogAction.class,
        org.beangle.ems.security.web.action.session.MonitorAction.class).property("entityDao",
        ref("sessionEntityDao"));

    bind(org.beangle.ems.security.web.action.nav.MenuAction.class,
        org.beangle.ems.security.web.action.nav.IndexAction.class,
        org.beangle.ems.security.web.action.nav.ProfileAction.class);

    bind(UserDashboardHelper.class).shortName();

    // data security
    bind(org.beangle.ems.security.web.action.data.FieldAction.class,
        org.beangle.ems.security.web.action.data.ResourceAction.class,
        org.beangle.ems.security.web.action.data.PermissionAction.class,
        org.beangle.ems.security.web.action.data.ProfileAction.class,
        org.beangle.ems.security.web.action.data.TypeAction.class);

    // avatar
    bind(BoardAction.class, org.beangle.ems.avatar.web.action.MyAction.class, MyUploadAction.class,
        org.beangle.ems.avatar.web.action.UserAction.class);
  }
}
