/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.wiki;

import org.beangle.commons.context.inject.AbstractBindModule;
import org.beangle.ems.wiki.web.action.IndexAction;

public class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind(IndexAction.class);
  }

}
