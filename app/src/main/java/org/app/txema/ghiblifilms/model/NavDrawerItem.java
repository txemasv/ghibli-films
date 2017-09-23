package org.app.txema.ghiblifilms.model;

/**
 * Created by Txema on 13/08/2017.
 */

public class NavDrawerItem {

    public static final int FILMS = 0;
    public static final int CHARACTERS = 1;
    public static final int LOCATIONS = 2;
    public static int SELECTED = -1;

    private boolean showNotify;
    private String title;

    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
