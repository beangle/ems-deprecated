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
package org.beangle.ems.rule;

import java.util.Set;

import org.beangle.commons.entity.Entity;

/**
 * 规则对应参数
 * 
 * @author chaostone
 */
public interface RuleParameter extends Entity<Integer> {

  Set<RuleParameter> getChildren();

  void setChildren(Set<RuleParameter> subRuleParams);

  RuleParameter getParent();

  void setParent(RuleParameter superRuleParameter);

  /**
   * 
   */
  Rule getRule();

  void setRule(Rule businessRule);

  String getName();

  void setName(String name);

  String getType();

  void setType(String type);

  void setTitle(String title);

  String getTitle();

}
