/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.rule.engine;

import java.util.List;

import org.beangle.ems.rule.Rule;
import org.beangle.ems.rule.model.RuleConfig;

public interface RuleExecutorBuilder {

  RuleExecutor build(Rule rule);

  RuleExecutor build(List<Rule> rules, boolean stopWhenFail);

  RuleExecutor build(RuleConfig ruleConfig);

}
