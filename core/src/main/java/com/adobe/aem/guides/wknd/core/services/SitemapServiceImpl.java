package com.adobe.aem.guides.wknd.core.services;

import com.adobe.aem.guides.wknd.core.models.SitemapPageLinks;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = SitemapService.class, immediate = true)
public class SitemapServiceImpl implements SitemapService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SitemapServiceImpl.class);


  @Override
  public List<SitemapPageLinks> getSitemapItems(Page parentPage, int depth, List<String> excludedPaths) {
      LOGGER.debug(
              "Start of setSitemapItems method with parentPage: {}, depth: {}, excludedPaths: {}",
              parentPage, depth, excludedPaths);
      List<SitemapPageLinks> sitemapItems = new ArrayList<>();
      if (parentPage != null && !excludedPaths.contains(parentPage.getPath()) && depth > 0) {
          Iterator<Page> childPages = parentPage.listChildren();
          while(childPages.hasNext()) {
              Page childPage = childPages.next();
              if (!childPage.getName().equals(JcrConstants.JCR_CONTENT) && !excludedPaths.contains(childPage.getPath())) {
                  boolean hideInNavigation = isHiddenNavigation(childPage);
                  if (!hideInNavigation) {
                      SitemapPageLinks sitemapPageLinks = new SitemapPageLinks();
                      sitemapPageLinks.setPath(childPage.getPath());
                      sitemapPageLinks.setTitle(childPage.getTitle());
                      sitemapPageLinks.setHideInNavigation(hideInNavigation);
                      sitemapItems.add(sitemapPageLinks);
                      sitemapPageLinks.setChildLinks(getSitemapItems(childPage, depth-1, excludedPaths));
                  }
              }
          }
      }
      LOGGER.debug("End of setSitemapItems with sitemapItems: {}", sitemapItems);
      return sitemapItems;
  }

    private boolean isHiddenNavigation(Page page) {
        if (page.isHideInNav()) return true;
        if (page.getContentResource() == null) return false;
        return page.getProperties().containsKey("hideinNav") && Boolean.TRUE.equals(page.getProperties().get("hideinNav", Boolean.class));
    }

}
