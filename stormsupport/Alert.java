package com.minlabs.stormsupport;

/**
 * Created by maddiemin on 9/7/17.
 */

public class Alert {
    private String title;
    private String description;
    private String url;
    private String locations;

    private String severity;
    public Alert(String t, String d, String u, String ti, String s)
    {
        title = t;
        description = d;
        url = u;
        locations = ti;
        severity = s;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }
    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
