package org.beangle.ems.web;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.beangle.ems.web.helper.SecurityHelper;

public class EmsWebModule extends AbstractBindModule{

  @Override
  protected void doBinding() {
    bind(SecurityHelper.class);
  }
  
}
