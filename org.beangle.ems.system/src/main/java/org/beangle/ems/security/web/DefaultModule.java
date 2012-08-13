/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.web;

import org.beangle.commons.context.inject.AbstractBindModule;
import org.beangle.commons.context.inject.Scope;
import org.beangle.ems.avatar.web.action.BoardAction;
import org.beangle.ems.avatar.web.action.MyUploadAction;
import org.beangle.ems.security.helper.UserDashboardHelper;
import org.beangle.ems.security.web.action.*;

public final class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    // security
    bind(SessioninfoLogAction.class, PermissionAction.class, RoleAction.class, IndexAction.class,
        MenuAction.class, MenuNavAction.class, MenuProfileAction.class, MonitorAction.class, MyAction.class,
        PasswordAction.class, ResourceAction.class, UserAction.class, CaptchaAction.class)
        .in(Scope.PROTOTYPE);

    bind(UserDashboardHelper.class).shortName();

    // data security
    bind(org.beangle.ems.security.web.action.data.FieldAction.class,
        org.beangle.ems.security.web.action.data.ResourceAction.class,
        org.beangle.ems.security.web.action.data.PermissionAction.class,
        org.beangle.ems.security.web.action.data.UserProfileAction.class).in(Scope.PROTOTYPE);

    // avatar
    bind(BoardAction.class, org.beangle.ems.avatar.web.action.MyAction.class, MyUploadAction.class,
        org.beangle.ems.avatar.web.action.UserAction.class).in(Scope.PROTOTYPE);

  }

}
