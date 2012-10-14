/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.rule;

import java.util.Set;

import org.beangle.commons.entity.pojo.LongIdTimeEntity;

/**
 * Rule represent a business constraint
 * 
 * @author chaostone
 */
public interface Rule extends LongIdTimeEntity {

  Set<RuleParameter> getParams();

  void setParams(Set<RuleParameter> ruleParams);

  boolean isEnabled();

  void setEnabled(boolean enabled);

  String getName();

  void setName(String name);

  String getBusiness();

  void setBusiness(String business);

  String getDescription();

  void setDescription(String description);

  String getFactory();

  void setFactory(String factory);

  String getServiceName();

  void setServiceName(String serviceName);

}
