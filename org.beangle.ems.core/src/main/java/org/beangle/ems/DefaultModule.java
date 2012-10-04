/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems;

import org.beangle.commons.context.inject.AbstractBindModule;
import org.beangle.commons.context.property.PropertyConfigBean;
import org.beangle.commons.context.property.UrlPropertyConfigProvider;
import org.beangle.commons.context.spring.SpringResources;
import org.beangle.commons.web.io.SplitStreamDownloader;
import org.beangle.ems.avatar.service.FileSystemAvatarBase;
import org.beangle.ems.config.service.DaoPropertyConfigProvider;
import org.beangle.ems.dictionary.service.impl.BaseCodeServiceImpl;
import org.beangle.ems.dictionary.service.impl.SeqCodeGenerator;
import org.beangle.ems.io.ClasspathDocLoader;
import org.beangle.ems.log.service.BusinessEventLogger;
import org.beangle.ems.rule.engine.impl.DefaultRuleExecutorBuilder;
import org.beangle.ems.rule.impl.RuleBaseImpl;

public class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind(FileSystemAvatarBase.class).lazy();
    bind(ClasspathDocLoader.class).shortName();
    bind("streamDownloader", SplitStreamDownloader.class);
    bind("baseCodeService", BaseCodeServiceImpl.class);
    bind(SeqCodeGenerator.class);
    bind(BusinessEventLogger.class);

    // rule bean
    bind(DefaultRuleExecutorBuilder.class, RuleBaseImpl.class);

    // properties config bean
    bind(UrlPropertyConfigProvider.class).property(
        "resources",
        bean(SpringResources.class).property("globals", "classpath*:system-default.properties")
            .property("locations", "classpath*:META-INF/system.properties")
            .property("users", "classpath*:system.properties"));

    bind(PropertyConfigBean.class).property("providers",
        list(ref(UrlPropertyConfigProvider.class), DaoPropertyConfigProvider.class)).lazy();

  }

}
