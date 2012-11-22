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

package org.beangle.ems.log;

import org.beangle.commons.entity.annotation.LogEntity;
import org.beangle.commons.entity.pojo.LongIdEntity;

/**
 * 业务日志明细
 * 
 * @author chaostone
 * @version $Id: BusinessLogDetail.java Aug 1, 2011 3:20:46 PM chaostone $
 */
@LogEntity
public interface BusinessLogDetail extends LongIdEntity {

  String getContent();

  BusinessLog getLog();

}
