package com.adobe.aem.guides.wknd.core.services;

import com.adobe.aem.guides.wknd.core.models.SitemapPageLinks;
import com.day.cq.wcm.api.Page;

import java.util.List;

public interface SitemapService {

 List<SitemapPageLinks> getSitemapItems(Page parentPage, int depth, List<String> excludedPaths);

}
