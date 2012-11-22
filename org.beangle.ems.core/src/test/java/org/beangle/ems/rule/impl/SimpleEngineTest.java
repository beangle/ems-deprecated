/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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

import org.beangle.commons.context.spring.SpringTestCase;
import org.beangle.ems.rule.Context;
import org.beangle.ems.rule.Rule;
import org.beangle.ems.rule.RuleBase;
import org.beangle.ems.rule.engine.Engine;
import org.beangle.ems.rule.engine.RuleExecutorBuilder;
import org.beangle.ems.rule.engine.impl.DefaultRuleExecutorBuilder;
import org.beangle.ems.rule.engine.impl.FullPatternMatcher;
import org.beangle.ems.rule.engine.impl.SimpleEngine;
import org.beangle.ems.rule.model.RuleBean;
import org.beangle.ems.rule.model.SimpleContext;

public class SimpleEngineTest extends SpringTestCase {

  public void testEngine() {
    Context context = new SimpleContext();
    Engine engine = new SimpleEngine();
    RuleBase ruleBase = new TestRuleBase();
    // Rule rule1 = new BusinessRule();
    // rule1.setFactory(DefaultRuleExecutorBuilder.SPRING);
    // rule1.setServiceName("ruleExecutor1");

    Rule rule2 = new RuleBean();
    rule2.setFactory(DefaultRuleExecutorBuilder.BEAN);
    rule2.setServiceName("org.beangle.rule.impl.RuleExecutor2");

    // ruleBase.getRules().add(rule1);
    ruleBase.getRules().add(rule2);
    engine.setRuleExecutorBuilder((RuleExecutorBuilder) applicationContext.getBean("ruleExecutorBuilder"));
    engine.setRuleBase(ruleBase);
    engine.setPatternMatcher(new FullPatternMatcher());
    engine.execute(context);
  }
}
