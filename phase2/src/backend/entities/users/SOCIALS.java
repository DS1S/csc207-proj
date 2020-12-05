package backend.entities.users;

public enum SOCIALS {
    linkedin("LinkedIn"), instagram("Instagram"), youtube("YouTube"), facebook("Facebook"), github("Github");

    private String social;

    SOCIALS(String social) {
        this.social = social;
    }

    @Override
    public String toString() {
        return social;
    }
}