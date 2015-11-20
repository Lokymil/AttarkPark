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
    private String pays;
    private LatLng latLng;
    private String imgUrl;

    public Park(Long id, String name, String description, String url, String pays, LatLng latLng, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.pays = pays;
        this.latLng = latLng;
        this.imgUrl = imgUrl;
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

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return name;
    }
}
