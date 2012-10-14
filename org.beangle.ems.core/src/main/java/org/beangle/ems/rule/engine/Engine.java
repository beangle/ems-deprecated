/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.rule.engine;

import org.beangle.ems.rule.Context;
import org.beangle.ems.rule.RuleBase;

/**
 * 规则引擎<br>
 * 具体负责执行规则
 * 
 * @author chaostone
 */
public interface Engine {

  void execute(Context context);

  void setPatternMatcher(PatternMatcher matcher);

  PatternMatcher getPatternMatcher();

  RuleBase getRuleBase();

  void setRuleBase(RuleBase base);

  RuleExecutorBuilder getRuleExecutorBuilder();

  void setRuleExecutorBuilder(RuleExecutorBuilder executorBuilder);

}
