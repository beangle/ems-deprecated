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
package org.beangle.ems.business.web.action;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.beangle.ems.rule.RuleParameter;
import org.beangle.ems.web.action.SecurityActionSupport;

/**
 * 规则参数管理
 * 
 * @author chaostone
 * @version $Id: RuleParamAction.java Jun 27, 2011 7:41:33 PM chaostone $
 */
public class RuleParamAction extends SecurityActionSupport {

  @Override
  protected void editSetting(Entity<?> entity) {
    Long ruleId = getLong("ruleParameter.rule.id");
    Long paramId = getLong("ruleParameter.id");

    OqlBuilder<RuleParameter> builder = OqlBuilder.from(RuleParameter.class, "ruleParam");
    if (null != ruleId) builder.where(" ruleParam.rule.id=:ruleId", ruleId);
    if (null != paramId) {
      builder.where(" ruleParam.id<>:paramId", paramId);
    }
    put("ruleParams", entityDao.search(builder));
  }

  @Override
  protected String getEntityName() {
    return RuleParameter.class.getName();
  }

}
