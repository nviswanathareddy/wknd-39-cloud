package com.adobe.aem.guides.wknd.core.models;

import java.util.List;

public class SitemapPageLinks {

    private String title;
    private String path;
    private boolean hideInNavigation;
    private List<SitemapPageLinks> childLinks;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isHideInNavigation() {
        return hideInNavigation;
    }

    public void setHideInNavigation(boolean hideInNavigation) {
        this.hideInNavigation = hideInNavigation;
    }

    public List<SitemapPageLinks> getChildLinks() {
        return childLinks;
    }

    public void setChildLinks(List<SitemapPageLinks> childLinks) {
        this.childLinks = childLinks;
    }
}
