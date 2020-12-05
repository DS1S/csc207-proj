package backend.entities.users;

public enum Socials {
    LINKEDIN("LinkedIn"),
    INSTAGRAM("Instagram"),
    YOUTUBE("YouTube"),
    FACEBOOK("Facebook"),
    GITHUB("Github"),
    REDDIT("Reddit");

    private String social;

    Socials(String social) {
        this.social = social;
    }

    @Override
    public String toString() {
        return social;
    }
}