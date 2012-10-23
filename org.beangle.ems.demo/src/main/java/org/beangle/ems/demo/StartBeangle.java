/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.demo;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.orm.hibernate.OpenSessionInViewFilter;
import org.beangle.commons.web.filter.CharacterEncodingFilter;
import org.beangle.commons.web.spring.ContextListener;
import org.beangle.commons.web.spring.DelegatingFilterProxy;
import org.beangle.security.web.session.HttpSessionEventPublisher;
import org.beangle.struts2.dispatcher.ActionServlet;
import org.beangle.struts2.dispatcher.StaticResourceServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.webapp.WebAppContext;

public class StartBeangle {

  public static void main(String[] args) throws Exception {
    WebAppContext ctx = new WebAppContext();
    ctx.setContextPath("/beangle");
    ctx.setResourceBase(".");
    ctx.setClassLoader(Thread.currentThread().getContextClassLoader());
    ctx.addFilter(CharacterEncodingFilter.class, "*.action", FilterMapping.DEFAULT);
    ctx.addFilter(OpenSessionInViewFilter.class, "*.action", FilterMapping.DEFAULT);
    FilterHolder security = new FilterHolder(DelegatingFilterProxy.class);
    security.setName("securityFilterChain");
    ctx.addFilter(security, "*.action", FilterMapping.DEFAULT);

    ctx.setWelcomeFiles(new String[] { "index.html" });
    ctx.addEventListener(new HttpSessionEventPublisher());
    ctx.addEventListener(new ContextListener());

    ctx.addServlet(ActionServlet.class, "*.action");
    ctx.addServlet(StaticResourceServlet.class, "/static/*");

    Map<String, String> params = CollectUtils.newHashMap();
    params.put("contextConfigLocation", "classpath:spring-context.xml");
    params.put("templatePath", "webapp:/pages,classpath:");

    ctx.setInitParams(params);
    Server server = new Server(8080);
    server.setHandler(ctx);
    server.start();
    server.join();
  }
}
