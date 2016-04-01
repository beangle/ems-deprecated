/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.ems.dev;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.beangle.ems.dev.hibernate.web.action.CacheAction;
import org.beangle.ems.dev.hibernate.web.action.EvolutionAction;
import org.beangle.ems.dev.spring.web.action.SpringAction;
import org.beangle.ems.dev.struts2.web.action.ConfigAction;

public final class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind(SpringAction.class, ConfigAction.class, CacheAction.class);

    bind(EvolutionAction.class);
  }

}
