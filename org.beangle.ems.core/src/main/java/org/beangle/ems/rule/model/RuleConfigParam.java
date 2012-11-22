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

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.ems.rule.RuleParameter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 规则参数配置
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.ems.rule.model.RuleConfigParam")
@Cacheable
@Cache(region = "beangle", usage = CacheConcurrencyStrategy.READ_WRITE)
public class RuleConfigParam extends LongIdObject {

  private static final long serialVersionUID = 8711866530914907008L;

  /** 标准-规则 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private RuleConfig config;

  /** 规则参数 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private RuleParameter param;

  /** 参数值 */
  @NotNull
  @Size(max = 500)
  private String value;

  public RuleParameter getParam() {
    return param;
  }

  public void setParam(RuleParameter param) {
    this.param = param;
  }

  public RuleConfig getConfig() {
    return config;
  }

  public void setConfig(RuleConfig config) {
    this.config = config;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
