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

package org.beangle.ems.log.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.annotation.LogEntity;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.ems.log.BusinessLog;
import org.beangle.ems.log.BusinessLogDetail;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 业务日志实现
 * 
 * @author chaostone
 * @version $Id: BusinessLogBean.java Jun 27, 2011 7:09:58 PM chaostone $
 */
@Entity(name = "org.beangle.ems.log.BusinessLog")
@LogEntity
public class BusinessLogBean extends LongIdObject implements BusinessLog {
  private static final long serialVersionUID = 4059776203680197111L;

  /** 操作用户 */
  @NotNull
  @Size(max = 50)
  private String operator;

  /** 操作内容 */
  @NotNull
  @Size(max = 200)
  private String operation;

  /** 操作资源 */
  @NotNull
  @Size(max = 100)
  @Column(name = "resrc")
  private String resource;

  /** 操作资源 */
  @NotNull
  @Size(max = 200)
  private String entry;

  /** 操作时间 */
  @NotNull
  private Date operateAt;

  /** 操作IP */
  @Size(max = 100)
  private String ip;

  /** 操作客户端代理 */
  @Size(max = 100)
  private String agent;

  /** 操作明细 */
  @Cascade(CascadeType.ALL)
  @ManyToOne(fetch = FetchType.LAZY)
  private BusinessLogDetail detail;

  public String getAgent() {
    return agent;
  }

  public void setAgent(String agent) {
    this.agent = agent;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public String getEntry() {
    return entry;
  }

  public void setEntry(String entry) {
    this.entry = entry;
  }

  public Date getOperateAt() {
    return operateAt;
  }

  public void setOperateAt(Date operateAt) {
    this.operateAt = operateAt;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public BusinessLogDetail getDetail() {
    return detail;
  }

  public void setDetail(BusinessLogDetail detail) {
    this.detail = detail;
  }
}
