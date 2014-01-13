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
