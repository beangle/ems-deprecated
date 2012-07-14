package org.beangle.ems.database;

import org.beangle.commons.context.inject.AbstractBindModule;
import org.beangle.commons.context.inject.Scope;
import org.beangle.ems.database.action.BrowserAction;
import org.beangle.ems.database.action.DatasourceAction;
import org.beangle.ems.database.action.IndexAction;
import org.beangle.ems.database.action.ProviderAction;
import org.beangle.ems.database.action.QueryAction;
import org.beangle.ems.database.service.DatasourceService;
import org.beangle.ems.database.service.SqlService;
import org.hibernate.dialect.H2Dialect;

public class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind(H2Dialect.class, SqlService.class, DatasourceService.class);

    bind(QueryAction.class, DatasourceAction.class, ProviderAction.class, BrowserAction.class,
        IndexAction.class).in(Scope.PROTOTYPE);
  }

}
