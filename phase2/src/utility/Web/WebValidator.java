package utility.Web;

import backend.entities.users.Socials;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that is used to validate web links given a Socials enum.
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

        // Access the fields of LinksRegexContainer, and maps each SOCIALS enum to those fields (i.e. the
        // corresponding regex/website format for each social media platform.
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
     * Determines if a given link is a valid link in a given social media platform.
     * @param social The given social media platform in the form of a Socials enum.
     * @param link The given link to be validated
     * @return True, if and only if the link is valid for the given social media platform.
     */
    public boolean validateLink(Socials social, String link) {
        return link.matches(socialsToRegex.get(social));
    }

    /**
     * A container for the corresponding regexes (website formats) of each social media platform represented
     * by the Socials enum.
     */
    private class LinksRegexContainer {
        public final String LINKEDIN_REGEX = "^(https:\\/\\/)?(www\\.)?linkedin\\.com\\/in\\/[-a-zA-Z0-9]*(\\/)?$";
        public final String INSTAGRAM_REGEX = "^(https:\\/\\/)?(www\\.)?instagram.com\\/.*(\\/)?$";
        public final String YOUTUBE_REGEX = "^(https:\\/\\/)?(www\\.)?youtube\\.com\\/channel\\/.*(\\/)?$";
        public final String FACEBOOK_REGEX = "^(https:\\/\\/)?(www\\.)?facebook\\.com\\/.*(\\/)?$";
        public final String GITHUB_REGEX = "^(https:\\/\\/)?(www\\.)?github\\.com\\/.*(\\/)?$";
        public final String REDDIT_REGEX = "^(https:\\/\\/)?(www\\.)?reddit.com/user/.*(\\/)?$";
    }
}
