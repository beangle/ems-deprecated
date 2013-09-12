package org.beangle.ems.web;

import static javax.servlet.DispatcherType.REQUEST;

import java.util.EnumSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.beangle.commons.web.filter.CharacterEncodingFilter;
import org.beangle.commons.web.filter.DelegatingFilterProxy;
import org.beangle.commons.web.init.StartupInitializer;
import org.beangle.commons.web.resource.StaticResourceServlet;
import org.beangle.commons.web.session.HttpSessionEventPublisher;
import org.beangle.inject.spring.web.ContextListener;
import org.beangle.orm.hibernate.OpenSessionInViewFilter;
import org.beangle.struts2.dispatcher.ActionServlet;

public class BootstrapInitializer implements StartupInitializer {

  @Override
  public void onStartup(ServletContext sc) throws ServletException {
    sc.setInitParameter("templatePath", "webapp://pages,class://");
    sc.addListener(new HttpSessionEventPublisher());
    sc.addListener(new ContextListener());

    sc.addFilter("characterEncoding", new CharacterEncodingFilter()).addMappingForUrlPatterns(
        EnumSet.of(REQUEST), true, "*.action");
    sc.addFilter("accessMonitorFilter", DelegatingFilterProxy.class).addMappingForUrlPatterns(
        EnumSet.of(REQUEST), true, "*.action");
    sc.addFilter("OpenSessionInViewFilter", OpenSessionInViewFilter.class).addMappingForUrlPatterns(
        EnumSet.of(REQUEST), true, "*.action");
    sc.addFilter("securityFilterChain", DelegatingFilterProxy.class).addMappingForUrlPatterns(
        EnumSet.of(REQUEST), true, "*.action");

    sc.addServlet("Action", new ActionServlet()).addMapping("*.action");
    sc.addServlet("StaticResource", new StaticResourceServlet()).addMapping("/static/*");
  }

}
