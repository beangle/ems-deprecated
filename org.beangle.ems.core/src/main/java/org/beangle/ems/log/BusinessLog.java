/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.log;

import java.util.Date;

import org.beangle.dao.pojo.LongIdEntity;

/**
 * 业务日志
 * 
 * @author chaostone
 * @version $Id: BusinessLog.java Jun 27, 2011 7:28:23 PM chaostone $
 */
public interface BusinessLog extends LongIdEntity {

	/**
	 * 操作人员
	 * 
	 * @return
	 */
	public String getOperator();

	/**
	 * 操作内容
	 * 
	 * @return
	 */
	public String getOperation();

	/**
	 * 操作资源
	 * 
	 * @return
	 */
	public String getResource();

	/**
	 * 操作时间
	 * 
	 * @return
	 */
	public Date getOperateAt();

	/**
	 * 操作地址
	 * 
	 * @return
	 */
	public String getIp();

	/**
	 * 操作的系统入口
	 * 
	 * @return
	 */
	public String getEntry();

	/**
	 * 客户端代理
	 * 
	 * @return
	 */
	public String getAgent();

	/**
	 * 详细内容
	 * 
	 * @return
	 */
	public BusinessLogDetail getDetail();

}
