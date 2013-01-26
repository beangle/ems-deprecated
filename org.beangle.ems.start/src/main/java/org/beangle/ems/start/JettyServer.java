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

package org.beangle.ems.start;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.web.filter.CharacterEncodingFilter;
import org.beangle.commons.web.filter.DelegatingFilterProxy;
import org.beangle.commons.web.session.HttpSessionEventPublisher;
import org.beangle.inject.spring.web.ContextListener;
import org.beangle.orm.hibernate.OpenSessionInViewFilter;
import org.beangle.struts2.dispatcher.ActionServlet;
import org.beangle.struts2.dispatcher.StaticResourceServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServer {

  private static int port = 8080;

  private static Server server;

  public static void main(String[] args) throws Exception {
    shutdown();
    startup();
  }

  private static void startup() throws Exception {
    WebAppContext ctx = new WebAppContext();
    ctx.setContextPath("/beangle");
    ctx.setResourceBase(".");
    ctx.setClassLoader(Thread.currentThread().getContextClassLoader());
    ctx.addFilter(CharacterEncodingFilter.class, "*.action", FilterMapping.DEFAULT);

    FilterHolder access = new FilterHolder(DelegatingFilterProxy.class);
    access.setName("accessMonitorFilter");
    ctx.addFilter(access, "*.action", FilterMapping.DEFAULT);

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
    params.put("templatePath", "webapp://pages,class://");

    ctx.setInitParams(params);
    server = new Server(port);
    server.setHandler(ctx);
    server.start();
    server.join();

  }

  private static void shutdown() {
    if (null != server) {
      try {
        server.stop();
      } catch (Exception e) {
      }
    }
  }
}
