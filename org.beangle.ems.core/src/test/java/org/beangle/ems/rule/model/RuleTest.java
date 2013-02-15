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
package org.beangle.ems.rule.model;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.rule.Context;
import org.beangle.ems.rule.Rule;
import org.beangle.ems.rule.engine.RuleExecutor;
import org.beangle.ems.rule.engine.RuleExecutorBuilder;
import org.beangle.ems.rule.engine.impl.DefaultRuleExecutorBuilder;
import org.beangle.inject.spring.SpringTestCase;

public class RuleTest extends SpringTestCase {

  // public void testSpringBuilder() {
  // RuleExecutorBuilder builder = (DefaultRuleExecutorBuilder)
  // applicationContext
  // .getBean("ruleExecutorBuilder");
  // Rule rule = (Rule) Model.newInstance(Rule.class);
  // rule.setFactory(DefaultRuleExecutorBuilder.SPRING);
  // rule.setServiceName("ruleExecutor1");
  // RuleExecutor exceutor = builder.build(rule);
  // Context context = new SimpleContext();
  // exceutor.execute(context);
  // }

  public void testComposite() {
    RuleExecutorBuilder builder = (DefaultRuleExecutorBuilder) applicationContext
        .getBean("ruleExecutorBuilder");
    List<Rule> rules = CollectUtils.newArrayList();
    // Rule rule1 = (Rule) Model.newInstance(Rule.class);
    // rule1.setFactory(DefaultRuleExecutorBuilder.SPRING);
    // rule1.setServiceName("ruleExecutor1");

    Rule rule2 = new RuleBean();
    rule2.setFactory(DefaultRuleExecutorBuilder.BEAN);
    rule2.setServiceName("org.beangle.rule.impl.RuleExecutor2");

    // rules.add(rule1);
    rules.add(rule2);

    Context context = new SimpleContext();
    RuleExecutor exceutor = builder.build(rules, false);
    exceutor.execute(context);
  }
}
