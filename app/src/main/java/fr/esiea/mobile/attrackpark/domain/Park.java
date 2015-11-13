package fr.esiea.mobile.attrackpark.domain;

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
    private String style;

    public Park(Long id, String name, String description, String url, String style) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.style = style;
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

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Override
    public String toString() {
        return name;
    }
}
