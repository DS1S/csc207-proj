package backend.entities.users;

/**
 * Enumeration set for different social media.
 */
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

    /**
     * Overrides the built-in toString method.
     * @return the social platform name in string format
     */
    @Override
    public String toString() {
        return social;
    }
}