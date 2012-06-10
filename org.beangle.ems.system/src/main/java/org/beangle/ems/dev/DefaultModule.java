/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.dev;

import org.beangle.commons.context.inject.AbstractBindModule;
import org.beangle.commons.context.inject.Scope;
import org.beangle.ems.dev.hibernate.web.action.CacheAction;
import org.beangle.ems.dev.spring.web.action.SpringAction;
import org.beangle.ems.dev.struts2.web.action.ConfigBrowserAction;

public final class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind(SpringAction.class, ConfigBrowserAction.class, CacheAction.class).in(Scope.SINGLETON);
  }

}
