/*
 *  Copyright WSO2 Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.wso2.carbon.appmgt.usage.client.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.appmgt.api.AppUsageStatisticsClient;
import org.wso2.carbon.appmgt.api.exception.AppUsageQueryServiceClientException;
import org.wso2.carbon.appmgt.impl.AppManagerConfiguration;
import org.wso2.carbon.appmgt.impl.AppManagerConfigurationService;
import org.wso2.carbon.appmgt.usage.client.impl.AppUsageStatisticsRdbmsClient;

/**
 * @scr.component name="org.wso2.appmgt.usage.client" immediate="true"
 * @scr.reference name="api.manager.config.service"
 * interface="org.wso2.carbon.appmgt.impl.AppManagerConfigurationService" cardinality="1..1"
 * policy="dynamic" bind="setAPIManagerConfigurationService" unbind="unsetAPIManagerConfigurationService"
 */
public class AppMUsageClientServiceComponent {

    private static final Log log = LogFactory.getLog(AppMUsageClientServiceComponent.class);

    private static AppManagerConfiguration configuration = null;

    protected void activate(ComponentContext componentContext)
            throws AppUsageQueryServiceClientException {
        if (log.isDebugEnabled()) {
            log.debug("org.wso2.appmgt.usage.client component has been activating");
        }
        BundleContext bundleContext = componentContext.getBundleContext();
        //Register the default App usage stat client as a OSGi service.
        bundleContext.registerService(AppUsageStatisticsClient.class.getName(), new AppUsageStatisticsRdbmsClient(),
                                      null);
    }

    protected void deactivate(ComponentContext componentContext) {
        log.debug("App usage client component deactivated");
    }

    protected void setAPIManagerConfigurationService(AppManagerConfigurationService amcService) {
        log.debug("App manager configuration service bound to the WebApp usage client component");
        configuration = amcService.getAPIManagerConfiguration();
    }

    protected void unsetAPIManagerConfigurationService(AppManagerConfigurationService amcService) {
        log.debug("App manager configuration service unbound from the WebApp usage client component");
        configuration = null;
    }

    public static AppManagerConfiguration getAPIManagerConfiguration() {
        return configuration;
    }


}
