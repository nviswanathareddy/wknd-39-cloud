package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Component(service = SafeResourceResolverProvider.class)
public class SafeResourceResolverProvider {

    private static final Logger logger = LoggerFactory.getLogger(SafeResourceResolverProvider.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    public ResourceResolver getResourceResolver() {
        ResourceResolver resourceResolver = null;
        final Map<String, Object> params = new HashMap<>();
        params.put(ResourceResolverFactory.SUBSERVICE, "wknd-safe-content-reader");
        try {
            resourceResolver = resourceResolverFactory.getServiceResourceResolver(params);
        } catch (LoginException e) {
            logger.error("Error while getting resource resolver: {}", e.getMessage());
            logger.debug("Check the logs for more information about the exception.", e);
        }
        logger.debug("End of getResourceResolver method with resourceResolver: {}", resourceResolver);
        return resourceResolver;
    }
}