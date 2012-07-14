/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.dev.hibernate.web.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.beangle.struts2.action.BaseAction;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

/**
 * @author chaostone
 * @version $Id: HibernateAction.java Nov 6, 2011 8:54:18 PM chaostone $
 */
public class CacheAction extends BaseAction {

  private SessionFactory sessionFactory;

  public String index() {
    Statistics statistics = sessionFactory.getStatistics();
    Date lastUpdate = new Date();
    Date activation = null;
    Date deactivation = null;

    List<Long> generalStatistics = Collections.synchronizedList(new ArrayList<Long>(18));
    final String action = get("do");
    final StringBuilder info = new StringBuilder(512);

    if ("activate".equals(action) && !statistics.isStatisticsEnabled()) {
      statistics.setStatisticsEnabled(true);
      activation = new Date();
      getSession().put("hibernate.stat.activation", activation);
      getSession().remove("hibernate.stat.deactivation");
      info.append("Statistics enabled\n");
    } else if ("deactivate".equals(action) && statistics.isStatisticsEnabled()) {
      statistics.setStatisticsEnabled(false);
      deactivation = new Date();
      getSession().put("hibernate.stat.deactivation", deactivation);
      activation=(Date)getSession().get("hibernate.stat.activation");
      info.append("Statistics disabled\n");
    } else if ("clear".equals(action)) {
      activation = null;
      deactivation = null;
      statistics.clear();
      getSession().remove("hibernate.stat.activation");
      getSession().remove("hibernate.stat.deactivation");
      generalStatistics.clear();
      info.append("Statistics cleared\n");
    }else{
      activation=(Date)getSession().get("hibernate.stat.activation");
      deactivation=(Date)getSession().get("hibernate.stat.deactivation");
    }
    
    addActionMessage(info.toString());
    boolean active = statistics.isStatisticsEnabled();
    if (active) {
      generalStatistics.add(statistics.getConnectCount());
      generalStatistics.add(statistics.getFlushCount());

      generalStatistics.add(statistics.getPrepareStatementCount());
      generalStatistics.add(statistics.getCloseStatementCount());

      generalStatistics.add(statistics.getSessionCloseCount());
      generalStatistics.add(statistics.getSessionOpenCount());

      generalStatistics.add(statistics.getTransactionCount());
      generalStatistics.add(statistics.getSuccessfulTransactionCount());
      generalStatistics.add(statistics.getOptimisticFailureCount());
    }
    put("active", active);
    put("lastUpdate", lastUpdate);
    if(null!=activation){
      if(null!=deactivation){
        put("duration",deactivation.getTime()-activation.getTime());
      }else{
        put("duration",lastUpdate.getTime()-activation.getTime());
      }
    }
    put("activation", activation);
    put("deactivation", deactivation);
    put("generalStatistics", generalStatistics);
    return forward();
  }

  public String conf() {
    return forward();
  }

  public String entity() {
    Statistics statistics = sessionFactory.getStatistics();
    put("statistics", statistics);
    return forward();
  }

  public String query() {
    Statistics statistics = sessionFactory.getStatistics();
    put("statistics", statistics);
    return forward("queryCache");
  }

  public String collection() {
    Statistics statistics = sessionFactory.getStatistics();
    put("statistics", statistics);
    return forward();
  }

  public String cache() {
    Statistics statistics = sessionFactory.getStatistics();
    put("statistics", statistics);
    return forward();
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
}
