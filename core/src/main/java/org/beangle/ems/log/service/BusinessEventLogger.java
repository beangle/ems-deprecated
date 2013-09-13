/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.ems.log.service;

import java.util.Date;

import static org.beangle.commons.bean.PropertyUtils.*;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.event.BusinessEvent;
import org.beangle.commons.event.Event;
import org.beangle.commons.event.EventListener;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.log.model.BusinessLogBean;
import org.beangle.ems.log.model.BusinessLogDetailBean;
import org.beangle.security.Securities;
import org.beangle.security.core.Authentication;

/**
 * @author chaostone
 * @version $Id: BusinessEventLogger.java Jun 29, 2011 9:28:33 A M chaostone $
 */
public class BusinessEventLogger extends BaseServiceImpl implements EventListener<Event> {

  public void onEvent(Event event) {
    Authentication auth = Securities.getAuthentication();
    if (null == auth) return;
    BusinessLogBean log = new BusinessLogBean();
    log.setOperateAt(new Date(event.getTimestamp()));
    log.setOperation(Strings.defaultIfBlank(event.getSubject(), "  "));
    log.setResource(Strings.defaultIfBlank(event.getResource(), "  "));
    log.setOperator(auth.getName());
    Object details = auth.getDetails();
    Object agent = getProperty(details, "agent");
    if (null != agent) {
      log.setIp((String) getProperty(agent, "ip"));
      log.setAgent((String) getProperty(agent, "os") + " " + (String) getProperty(agent, "browser"));
      String lastAccessUri = (String) getProperty(agent, "lastAccessUri");
      log.setEntry(Strings.defaultIfBlank(lastAccessUri, "--"));
    }
    if (null != event.getDetail()) {
      log.setDetail(new BusinessLogDetailBean(log, event.getDetail()));
    }
    entityDao.saveOrUpdate(log);
  }

  public boolean supportsEventType(Class<? extends Event> eventType) {
    return BusinessEvent.class.isAssignableFrom(eventType);
  }

  public boolean supportsSourceType(Class<?> sourceType) {
    return true;
  }

}
