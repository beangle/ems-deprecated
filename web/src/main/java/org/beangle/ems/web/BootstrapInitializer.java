/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.ems.web;

import static javax.servlet.DispatcherType.REQUEST;

import java.util.EnumSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.beangle.commons.web.filter.CharacterEncodingFilter;
import org.beangle.commons.web.filter.DelegatingFilterProxy;
import org.beangle.commons.web.init.StartupInitializer;
import org.beangle.commons.web.resource.StaticResourceServlet;
import org.beangle.commons.web.session.HttpSessionEventPublisher;
import org.beangle.inject.spring.web.ContextListener;
import org.beangle.orm.hibernate.web.OpenSessionInViewFilter;
import org.beangle.struts2.convention.config.PropertyConstantProvider;
import org.beangle.struts2.dispatcher.ActionServlet;

public class BootstrapInitializer implements StartupInitializer {

  @Override
  public void onStartup(ServletContext sc) throws ServletException {
    sc.setInitParameter("templatePath", "webapp://pages,class://");
    sc.addListener(new HttpSessionEventPublisher());
    sc.addListener(new ContextListener());

    if (null == sc.getFilterRegistration("characterEncoding")) {
      sc.addFilter("characterEncoding", new CharacterEncodingFilter()).addMappingForUrlPatterns(
          EnumSet.of(REQUEST), true, "*.action");
    }
    if (null == sc.getFilterRegistration("accessMonitorFilter")) {
      sc.addFilter("accessMonitorFilter", DelegatingFilterProxy.class).addMappingForUrlPatterns(
          EnumSet.of(REQUEST), true, "*.action");
    }
    if (null == sc.getFilterRegistration("openSessionInViewFilter")) {
      sc.addFilter("openSessionInViewFilter", OpenSessionInViewFilter.class).addMappingForUrlPatterns(
          EnumSet.of(REQUEST), true, "*.action");
    }
    if (null == sc.getFilterRegistration("securityFilterChain")) {
      sc.addFilter("securityFilterChain", DelegatingFilterProxy.class).addMappingForUrlPatterns(
          EnumSet.of(REQUEST), true, "*.action");
    }

    if (null == sc.getServletRegistration("Action")) {
      ServletRegistration sr = sc.addServlet("action", new ActionServlet());
      sr.addMapping("*.action");
      sr.setInitParameter("configProviders", PropertyConstantProvider.class.getName());
    }
    if (null == sc.getServletRegistration("staticResource")) {
      sc.addServlet("staticResource", new StaticResourceServlet()).addMapping("/static/*");
    }
  }
}
