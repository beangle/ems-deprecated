/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.rule;

import java.util.Set;

import org.beangle.commons.entity.pojo.LongIdEntity;

/**
 * 规则对应参数
 * 
 * @author chaostone
 */
public interface RuleParameter extends LongIdEntity {

  /**
   * 
   */
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
