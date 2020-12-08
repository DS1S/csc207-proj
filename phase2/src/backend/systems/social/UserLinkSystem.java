package backend.systems.social;

import backend.entities.users.Socials;
import backend.systems.MenuSystem;
import backend.systems.usermangement.managers.UserManager;
import frontend.UserLinkUI;
import utility.Web.WebAccessible;
import utility.Web.WebValidator;
import utility.inputprocessors.InputProcessor;
import utility.inputprocessors.OptionInputProcessor;

import java.util.*;

/**
 * The class responsible for linking a user's profile to other social media services.
 */
public class UserLinkSystem extends MenuSystem {
    private WebValidator webValidator;
    private WebAccessible webOpener;
    private UserLinkUI userLinkUI;
    private UserManager userManager;

    /**
     * Constructs a new instance of UserLinkSystem using the given parameters.
     * @param webValidator the web validator used by the system
     * @param webOpener the web opener used by the system
     * @param userManager the user manager used by the system
     */
    public UserLinkSystem(WebValidator webValidator, WebAccessible webOpener, UserManager userManager) {
        super(5);
        this.webValidator = webValidator;
        this.webOpener = webOpener;
        this.userLinkUI = new UserLinkUI();
        this.userManager = userManager;
    }

    /**
     * An override of the displayOptions method.
     */
    @Override
    protected void displayOptions() {
        userLinkUI.displayUserLinkMenu();
    }

    /**
     * An override of the processInput method.
     * @param index The input to be processed.
     */
    @Override
    protected void processInput(int index) {
        switch (index) {
            case(1):
                // Add a link to your profile.
                addLink();
                break;
            case(2):
                // Remove a link from your profile.
                removeLink();
                break;
            case(3):
                // Explore other user's links.
                accessLinks();
                break;
            case(4):
                // See your own links.
                List<String> links = userManager.getUserLinks(userManager.getLoggedInUserUUID());

                if (links.size() > 0) {
                    userLinkUI.displayOwnLinksPrompt();
                    userLinkUI.displayOptions(links, false, false);
                }
                else { userLinkUI.displayNoLinksPrompt(); }

                break;
        }
    }

    private void addLink() {
        List<String> socials = socialsToStrings();

        InputProcessor<Integer> optionProcessor = new OptionInputProcessor(new Scanner(System.in), socials.size());
        userLinkUI.displayAddLinksPrompt();
        userLinkUI.displayOptions(socials, false, false);
        int index = optionProcessor.processInput() - 1;
        Socials social = Socials.values()[index];

        userLinkUI.displayLinkPrompt(social.toString());
        String link = askForString(social + " link");
        while (!webValidator.validateLink(social, link)) {
            userLinkUI.displayError("Invalid " + social + " link! Please enter a valid link.");
            link = askForString(social + " link");
        }

        if (!link.contains("https://www.") && !link.contains("www.") && !link.contains("https://")) {
            link = "https://www." + link;
        }

        userManager.setLoggedInUserLink(social, link);

        userLinkUI.displayLinkAddedPrompt(social.toString());
    }

    private List<String> socialsToStrings() {
        List<String> socials = new ArrayList<>();
        for (Socials social : Socials.values()) {
            socials.add(social.toString());
        }
        return socials;
    }

    private void accessLinks() {
        userLinkUI.displayExploreLinksPrompt();
        String userName = askForString("user name");

        while (!userManager.containsUserWithUsername(userName)) {
            userLinkUI.displayError("Invalid user name! Please enter a valid user name!");
            userName = askForString("user name");
        }

        UUID uuid = userManager.getUUIDWithUsername(userName);
        if (userManager.userHasLinks(uuid)) {
            List<String> userLinks = userManager.getUserLinks(uuid);
            userLinkUI.displayExploreChoicesPrompt(userManager.getNameWithUUID(uuid));
            userLinkUI.displayOptions(userLinks, false, false);
            InputProcessor<Integer> optionProcessor = new OptionInputProcessor(new Scanner(System.in),
                    userLinks.size());
            int index = optionProcessor.processInput() - 1;

            webOpener.openWeb(userLinks.get(index));
        }
        else {
            userLinkUI.displayEmptyLinksError();
        }
    }

    private void removeLink() {
        UUID uuid = userManager.getLoggedInUserUUID();

        if (userManager.userHasLinks(uuid)) {
            userLinkUI.displayLinkRemovalPrompt();

            List<String> userLinks = userManager.getUserLinks(uuid);
            userLinkUI.displayOptions(userLinks, false, false);
            InputProcessor<Integer> optionProcessor = new OptionInputProcessor(new Scanner(System.in),
                    userLinks.size());
            int index = optionProcessor.processInput() - 1;

            String social = userLinks.get(index).split(":")[0];
            userManager.removeUserLink(uuid, Socials.valueOf(social.toUpperCase()));

            userLinkUI.displayLinkRemovedPrompt(social);
        }
        else {
            userLinkUI.displayEmptyLinksError();
        }
    }

    /**
     * Overrides the built-in toString method.
     * @return the string "Social Media Links"
     */
    @Override
    public String toString() {
        return "Social Media Links";
    }
}
