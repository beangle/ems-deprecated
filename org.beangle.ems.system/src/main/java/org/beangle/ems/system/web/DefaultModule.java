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

package org.beangle.ems.system.web;

import org.beangle.commons.inject.bind.AbstractBindModule;
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
