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
package org.beangle.ems.rule.impl;

import org.beangle.ems.rule.Context;
import org.beangle.ems.rule.engine.RuleExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleExecutor1 implements RuleExecutor {

  private static final Logger logger = LoggerFactory.getLogger(RuleExecutor1.class);

  public boolean execute(Context context) {
    logger.info("I am rule executor No 1");
    return false;
  }

}
