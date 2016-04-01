/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.ems.system.web.action;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.config.Version;
import org.beangle.commons.lang.SystemInfo;
import org.beangle.struts2.action.ActionSupport;

public class InfoAction extends ActionSupport {

  Version systemVersion;

  public String index() {
    Map<String, Object> clientProps = CollectUtils.newHashMap();
    clientProps.put("client.ip", getRemoteAddr());
    HttpServletRequest request = getRequest();
    clientProps.put("client.useragent", request.getHeader("USER-AGENT"));
    clientProps.put("client.scheme", request.getScheme());
    clientProps.put("client.secure", String.valueOf(request.isSecure()));
    put("clientProps", clientProps);
    return forward();
  }

  public String status() {
    put("MaxMem", Runtime.getRuntime().maxMemory());
    put("FreeMem", Runtime.getRuntime().freeMemory());
    put("TotalMem", Runtime.getRuntime().totalMemory());

    put("osMBean", ManagementFactory.getOperatingSystemMXBean());
    put("runtimeMBean", ManagementFactory.getRuntimeMXBean());
    put("upAt", new Date(ManagementFactory.getRuntimeMXBean().getStartTime()));

    put("memPoolMBeans", ManagementFactory.getMemoryPoolMXBeans());
    put("threadMBean", ManagementFactory.getThreadMXBean());

    Map<String, Object> serverProps = CollectUtils.newHashMap();
    HttpServletRequest request = getRequest();
    serverProps.put("server.hostname", request.getServerName());
    serverProps.put("server.port", request.getServerPort());
    serverProps.put("server.protocol", request.getProtocol());
    serverProps.put("server.path", request.getSession().getServletContext().getRealPath(""));
    serverProps.put("server.info", request.getSession().getServletContext().getServerInfo());
    serverProps.put("user.dir", System.getProperty("user.dir"));
    put("now", new Date());
    put("serverProps", serverProps);
    return forward();
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public String properties() {
    put("host", SystemInfo.getHost());
    put("os", SystemInfo.getOs());
    put("user", SystemInfo.getUser());

    put("java", SystemInfo.getJava());
    put("javaSpec", SystemInfo.getJavaSpec());
    put("jvm", SystemInfo.getJvm());
    put("jvmSpec", SystemInfo.getJvmSpec());
    put("javaRuntime", SystemInfo.getJavaRuntime());

    Map<String, String> extra = new HashMap(System.getProperties());
    for (String k : SystemInfo.getUsedproperties()) {
      extra.remove(k);
    }
    put("extra", extra);
    put("env", System.getenv());
    return forward();
  }

  public Version getSystemVersion() {
    return systemVersion;
  }

  public void setSystemVersion(Version systemVersion) {
    this.systemVersion = systemVersion;
  }

}
