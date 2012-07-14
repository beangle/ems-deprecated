package org.beangle.ems.wiki;

import org.beangle.commons.context.inject.AbstractBindModule;
import org.beangle.ems.wiki.web.action.IndexAction;

public class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind(IndexAction.class);
  }

}
