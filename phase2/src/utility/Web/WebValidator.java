package utility.Web;

import backend.entities.users.Socials;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class which validates website links as valid social media websites
 */
public class WebValidator {
    private Map<Socials, String> socialsToRegex = new HashMap<>();

    /**
     * Constructs a WebValidator object and then validates each of the user's social
     * media links by checking the against a regex. Places valid lnks into a map
     * and anonymously logs invalid ones.
     */
    public WebValidator() {
        Socials[] socials = Socials.values();
        Class<LinksRegexContainer> classType = LinksRegexContainer.class;
        Field[] fields = classType.getDeclaredFields();
        LinksRegexContainer linksRegexContainer = new LinksRegexContainer();

        for (int i = 0; i < socials.length; ++i) {
            String fieldName = fields[i].getName();

            if (fieldName.toLowerCase().contains(socials[i].toString().toLowerCase())) {
                fields[i].setAccessible(true);

                try {
                    Object value = fields[i].get(linksRegexContainer);
                    socialsToRegex.put(socials[i], (String)value);
                }
                catch (IllegalAccessException e) {
                    Logger.getAnonymousLogger().log(Level.SEVERE, "Invalid social regex combination: error420");
                }
            }
        }
    }

    /**
     * Checks whether a given link is a valid social media link of this user's.
     * @param social the social to check against
     * @param link the link to check
     * @return true if it's valid, false otherwise
     */
    public boolean validateLink(Socials social, String link) {
        return link.matches(socialsToRegex.get(social));
    }

    private class LinksRegexContainer {
        public final String LINKEDIN_REGEX = "^(https:\\/\\/)?(www\\.)?linkedin\\.com\\/in\\/[-a-zA-Z0-9]*(\\/)?$";
        public final String INSTAGRAM_REGEX = "^(https:\\/\\/)?(www\\.)?instagram.com\\/.*(\\/)?$";
        public final String YOUTUBE_REGEX = "^(https:\\/\\/)?(www\\.)?youtube\\.com\\/channel\\/.*(\\/)?$";
        public final String FACEBOOK_REGEX = "^(https:\\/\\/)?(www\\.)?facebook\\.com\\/.*(\\/)?$";
        public final String GITHUB_REGEX = "^(https:\\/\\/)?(www\\.)?github\\.com\\/.*(\\/)?$";
        public final String REDDIT_REGEX = "^(https:\\/\\/)?(www\\.)?reddit.com/user/.*(\\/)?$";
    }
}
