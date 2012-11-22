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
package org.beangle.ems.business.web.action;

import java.util.Date;

import org.beangle.commons.entity.Entity;
import org.beangle.ems.rule.Rule;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.struts2.convention.route.Action;

/**
 * 规则信息
 * 
 * @author chaostone
 * @version $Id: RuleAction.java Jun 27, 2011 7:41:11 PM chaostone $
 */
public class RuleAction extends SecurityActionSupport {

  @Override
  protected String saveAndForward(Entity<?> entity) {
    Rule rule = (Rule) entity;
    if (null == rule.getId()) {
      rule.setCreatedAt(new Date());
      rule.setUpdatedAt(new Date());
    } else {
      rule.setUpdatedAt(new Date());
    }
    entityDao.saveOrUpdate(rule);
    return redirect("search", "info.save.success");
  }

  @Override
  protected String getEntityName() {
    return Rule.class.getName();
  }

  public String params() {
    return redirect(
        Action.to(RuleParamAction.class).method("search").param("ruleParameter.rule.id", getLong("rule.id")),
        null);
  }

}
