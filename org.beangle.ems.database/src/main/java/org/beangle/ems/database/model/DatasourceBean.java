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

package org.beangle.ems.database.model;

import java.util.Set;

import org.beangle.commons.entity.pojo.LongIdObject;

/**
 * @author chaostone
 */
public class DatasourceBean extends LongIdObject {

  private static final long serialVersionUID = -6769975441732211022L;

  private DatasourceProviderBean provider;

  private String name;

  private String url;

  private String username;

  private String password;

  private String driverClassName;

  private Set<DatasourcePropertyBean> properties;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDriverClassName() {
    return driverClassName;
  }

  public void setDriverClassName(String driverClassName) {
    this.driverClassName = driverClassName;
  }

  public Set<DatasourcePropertyBean> getProperties() {
    return properties;
  }

  public void setProperties(Set<DatasourcePropertyBean> properties) {
    this.properties = properties;
  }

  public DatasourceProviderBean getProvider() {
    return provider;
  }

  public void setProvider(DatasourceProviderBean provider) {
    this.provider = provider;
  }

}
