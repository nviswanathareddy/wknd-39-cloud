package com.adobe.aem.guides.wknd.core.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.adobe.aem.guides.wknd.core.models.SitemapPageLinks;
import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class SitemapServiceImplTest {

    private final  AemContext context = new AemContext();

    @Test
    void setSitemapItems() {
        SitemapService sitemapService = context.registerService(new SitemapServiceImpl());
        Resource resource = context.load(true)
                .json("/sitemap/PageResource.json",
                        "/content/cf/demo/en_GB/home");
        List<String> excludedPaths = new ArrayList<>();
        excludedPaths.add("/content/wknd/es");
        Page page = resource.adaptTo(Page.class);
        assertNotNull(sitemapService);
        List<SitemapPageLinks> sitemapPageLinks = sitemapService.getSitemapItems(page, 2, excludedPaths);
        assertNotNull(sitemapPageLinks);
        assertEquals(10, sitemapPageLinks.size());
        assertEquals(0, sitemapPageLinks.get(0).getChildLinks().size());
        assertEquals(0, sitemapPageLinks.get(1).getChildLinks().size());
        assertEquals(0, sitemapPageLinks.get(2).getChildLinks().size());
        assertEquals(0, sitemapPageLinks.get(3).getChildLinks().size());
    }
}
