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

package org.beangle.ems.rule.model;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdTimeObject;
import org.beangle.ems.rule.Rule;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 规则配置
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.ems.rule.model.RuleConfig")
@Cacheable
@Cache(region = "beangle", usage = CacheConcurrencyStrategy.READ_WRITE)
public class RuleConfig extends LongIdTimeObject {

  private static final long serialVersionUID = -5404097831423072886L;

  /** 业务规则 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Rule rule;

  /** 是否启用 */
  @NotNull
  private boolean enabled;

  /** 规则配置参数 */
  @OneToMany(mappedBy = "config", cascade = CascadeType.ALL, orphanRemoval = true)
  @Cache(region = "beangle", usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<RuleConfigParam> params = CollectUtils.newHashSet();

  public Set<RuleConfigParam> getParams() {
    return params;
  }

  public void setParams(Set<RuleConfigParam> params) {
    this.params = params;
  }

  public Rule getRule() {
    return rule;
  }

  public void setRule(Rule rule) {
    this.rule = rule;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
