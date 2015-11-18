package fr.esiea.mobile.attrackpark.domain;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Christophe on 12/11/2015.
 */
public class Park {

    private Long id;
    private String name;
    private String description;
    private String url;
    private String location;
    private String image;

    private LatLng latLng;

    public Park(Long id, String name, String description, String url, LatLng latLng) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.latLng = latLng;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setLatLng(double lat, double lng){
        this.latLng = new LatLng(lat,lng);
    }

    @Override
    public String toString() {
        return name;
    }
}
