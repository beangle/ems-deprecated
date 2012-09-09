/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.log.service;

import java.util.Date;

import org.beangle.commons.context.event.BusinessEvent;
import org.beangle.commons.context.event.Event;
import org.beangle.commons.context.event.EventListener;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.log.model.BusinessLogBean;
import org.beangle.ems.log.model.BusinessLogDetailBean;
import org.beangle.security.Securities;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.web.auth.WebAuthenticationDetails;

/**
 * @author chaostone
 * @version $Id: BusinessEventLogger.java Jun 29, 2011 9:28:33 A M chaostone $
 */
public class BusinessEventLogger extends BaseServiceImpl implements EventListener<Event> {

  private SessionRegistry sessionRegistry;

  public void onEvent(Event event) {
    Authentication auth = Securities.getAuthentication();
    if (null == auth) return;
    BusinessLogBean log = new BusinessLogBean();
    log.setOperateAt(new Date(event.getTimestamp()));
    log.setOperation(Strings.defaultIfBlank(event.getSubject(), "  "));
    log.setResource(Strings.defaultIfBlank(event.getResource(), "  "));
    log.setOperator(auth.getName());
    Object details = auth.getDetails();
    if ((details instanceof WebAuthenticationDetails)) {
      WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
      log.setIp(webDetails.getAgent().getIp());
      log.setAgent(webDetails.getAgent().getOs() + " " + webDetails.getAgent().getBrowser());
      log.setEntry(Strings.defaultIfBlank(sessionRegistry.getResource(webDetails.getSessionId()), "--"));
    }
    if (null != event.getDetail()) {
      log.setDetail(new BusinessLogDetailBean(log, event.getDetail()));
    }
    entityDao.saveOrUpdate(log);
  }

  public void setSessionRegistry(SessionRegistry sessionRegistry) {
    this.sessionRegistry = sessionRegistry;
  }

  public boolean supportsEventType(Class<? extends Event> eventType) {
    return BusinessEvent.class.isAssignableFrom(eventType);
  }

  public boolean supportsSourceType(Class<?> sourceType) {
    return true;
  }

}
