package com.adobe.aem.guides.wknd.core.models;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SitemapModel {

    @ValueMapValue
    @Default(values = "")
    private String rootPagePath;

    @ValueMapValue
    @Default(intValues = 3)
    private int numberOfColumns;

    @ValueMapValue
    @Default(intValues = 2)
    private int maxLevel;

    @ValueMapValue(name = "excludedPaths")
    List<String> excludedPaths;

    @ValueMapValue
    @Default(values = "")
    private String showMoreText;

    @ValueMapValue
    @Default(values = "")
    private String showLessText;

    @OSGiService
    private SafeResourceResolverProvider safeResourceResolverProvider;

    @OSGiService
    private com.adobe.aem.guides.wknd.core.services.SitemapService sitemapService;

    private List<SitemapPageLinks> sitemapPageLinks;

    @PostConstruct
    protected void init() {
        ResourceResolver resourceResolver = safeResourceResolverProvider.getResourceResolver();
        if (resourceResolver != null) {
            Resource rootPathResource = resourceResolver.getResource(rootPagePath);
            if (rootPathResource != null) {
                Page rootPage = rootPathResource.adaptTo(Page.class);
                if (excludedPaths == null) {
                    excludedPaths = new ArrayList<>();
                }
                if (rootPage != null) {
                    this.sitemapPageLinks = sitemapService.getSitemapItems(rootPage, maxLevel, excludedPaths);
                }
            }
        }
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public String getShowMoreText() {
        return showMoreText;
    }

    public String getShowLessText() {
        return showLessText;
    }

    public List<SitemapPageLinks> getSitemapPageLinks() {
        return sitemapPageLinks;
    }

}
