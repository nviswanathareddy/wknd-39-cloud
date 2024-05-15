package com.adobe.aem.guides.wknd.core.models;

import com.adobe.aem.guides.wknd.core.services.SitemapService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class SitemapModelTest {

    private final AemContext context = new AemContext();

    @Mock
    private SitemapService sitemapService;

    @Mock
    private SafeResourceResolverProvider safeResourceResolverProvider;

    @Test
    void init() {
        String PAGE_RESOURCE_PATH = "/sitemap/PageResource.json";
        String SITEMAP_RESOURCE_PATH = "/sitemap/SitemapResource.json";
        String HOME_PATH = "/content/cf/demo/en_GB/home";
        String DUMMY_SITEMAP_PAGE_PATH = "/content/cf/demo/en_GB/home/dummy-sitemap-page";

        context.addModelsForClasses(SitemapModel.class);
        context.registerService(SitemapService.class, sitemapService);
        context.registerService(SafeResourceResolverProvider.class, safeResourceResolverProvider);

        when(safeResourceResolverProvider.getResourceResolver()).thenReturn(context.resourceResolver());

        context.load(true).json(PAGE_RESOURCE_PATH, HOME_PATH);
        Resource currentResource = context.load(true).json(SITEMAP_RESOURCE_PATH, DUMMY_SITEMAP_PAGE_PATH);
        context.currentResource(DUMMY_SITEMAP_PAGE_PATH);

        SitemapModel sitemapModel = currentResource.adaptTo(SitemapModel.class);

        assertNotNull(sitemapModel);

        List<SitemapPageLinks> sitemapPageLinks = sitemapModel.getSitemapPageLinks();
        assertNotNull(sitemapPageLinks);
        assertEquals(0, sitemapPageLinks.size());

        assertEquals(4, sitemapModel.getNumberOfColumns());
        assertEquals(3, sitemapModel.getMaxLevel());

        assertNotNull(sitemapModel.getSitemapPageLinks());

        assertEquals("Show more", sitemapModel.getShowMoreText());
        assertEquals("Show less", sitemapModel.getShowLessText());
    }
}
