package ro.code4.monitorizarevot.net.model;

public class OnboardingItem {
    private Integer id;
    private String title;
    private String description;
    private int imageResource;

    public OnboardingItem(Integer id, String title, String description, int imageResource) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageResource = imageResource;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResource() {
        return imageResource;
    }
}
