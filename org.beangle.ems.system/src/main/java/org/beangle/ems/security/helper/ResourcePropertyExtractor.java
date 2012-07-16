/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.helper;

import org.beangle.commons.i18n.TextResource;
import org.beangle.commons.transfer.exporter.DefaultPropertyExtractor;
import org.beangle.security.blueprint.Resource;

/**
 * @author chaostone
 * @version $Id: ResourcePropertyExtractor.java Jul 17, 2011 9:41:20 PM chaostone $
 */
public class ResourcePropertyExtractor extends DefaultPropertyExtractor {

  public ResourcePropertyExtractor() {
    super();
  }

  public ResourcePropertyExtractor(TextResource textResource) {
    super(textResource);
  }

  public Object getPropertyValue(Object target, String property) throws Exception {
    Resource resource = (Resource) target;
    if ("enabled".equals(property)) {
      return resource.isEnabled() ? "激活" : "冻结";
    } else return super.getPropertyValue(target, property);
  }

}
