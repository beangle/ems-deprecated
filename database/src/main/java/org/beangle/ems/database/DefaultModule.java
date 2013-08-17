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
package org.beangle.ems.database;

import org.beangle.commons.inject.bind.AbstractBindModule;
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
        IndexAction.class);
  }

}
