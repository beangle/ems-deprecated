/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.system.web;

import org.beangle.commons.context.inject.AbstractBindModule;
import org.beangle.ems.business.web.action.LogAction;
import org.beangle.ems.business.web.action.RuleAction;
import org.beangle.ems.business.web.action.RuleParamAction;
import org.beangle.ems.dictionary.web.action.CodeAction;
import org.beangle.ems.dictionary.web.action.CodeMetaAction;
import org.beangle.ems.dictionary.web.action.CodeScriptAction;
import org.beangle.ems.io.ClasspathDocLoader;
import org.beangle.ems.portal.web.action.HomeAction;
import org.beangle.ems.portal.web.action.LoginAction;
import org.beangle.ems.system.web.action.FileAction;
import org.beangle.ems.system.web.action.InfoAction;
import org.beangle.ems.system.web.action.PropertyAction;
import org.beangle.ems.system.web.action.StaticfileAction;
import org.beangle.ems.web.action.LogoutAction;

public final class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    // property
    bind(FileAction.class, InfoAction.class, PropertyAction.class);
    // home
    bind(LoginAction.class, LogoutAction.class, HomeAction.class);

    bind(LogAction.class, RuleAction.class, RuleParamAction.class);

    bind(CodeAction.class, CodeMetaAction.class, CodeScriptAction.class);

    bind(StaticfileAction.class).property("loaders", listref(ClasspathDocLoader.class));

  }
}
