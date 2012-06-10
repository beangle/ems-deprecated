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
import org.beangle.ems.security.web.action.AuthorityAction;
import org.beangle.ems.security.web.action.CaptchaAction;
import org.beangle.ems.security.web.action.IndexAction;
import org.beangle.ems.security.web.action.MenuAction;
import org.beangle.ems.security.web.action.MenuNavAction;
import org.beangle.ems.security.web.action.MenuProfileAction;
import org.beangle.ems.security.web.action.MonitorAction;
import org.beangle.ems.security.web.action.MyAction;
import org.beangle.ems.security.web.action.PasswordAction;
import org.beangle.ems.security.web.action.ProfileAction;
import org.beangle.ems.security.web.action.ResourceAction;
import org.beangle.ems.security.web.action.RestrictionAction;
import org.beangle.ems.security.web.action.RoleAction;
import org.beangle.ems.security.web.action.SessioninfoLogAction;
import org.beangle.ems.security.web.action.UserAction;

public final class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    // security
    bind(SessioninfoLogAction.class, AuthorityAction.class, RoleAction.class, IndexAction.class,
        MenuAction.class, MenuNavAction.class, MenuProfileAction.class, MonitorAction.class, MyAction.class,
        PasswordAction.class, ResourceAction.class, UserAction.class, CaptchaAction.class,
        ProfileAction.class, RestrictionAction.class).in(Scope.PROTOTYPE);

    bind(UserDashboardHelper.class).shortName();

    // avatar
    bind(BoardAction.class, org.beangle.ems.avatar.web.action.MyAction.class, MyUploadAction.class,
        org.beangle.ems.avatar.web.action.UserAction.class).in(Scope.PROTOTYPE);

  }

}
