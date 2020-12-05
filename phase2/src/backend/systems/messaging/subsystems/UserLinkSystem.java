package backend.systems.messaging.subsystems;

import backend.entities.users.Socials;
import backend.systems.MenuSystem;
import backend.systems.usermangement.managers.UserManager;
import frontend.UserLinkUI;
import utility.Web.WebAccessible;
import utility.Web.WebValidator;
import utility.inputprocessors.IndexProcessor;
import utility.inputprocessors.OptionIndexProcessor;

import java.util.*;

public class UserLinkSystem extends MenuSystem {
    private WebValidator webValidator;
    private WebAccessible webOpener;
    private UserLinkUI userLinkUI;
    private UserManager userManager;

    public UserLinkSystem(WebValidator webValidator, WebAccessible webOpener, UserManager userManager, int numOptions) {
        super(numOptions);
        this.webValidator = webValidator;
        this.webOpener = webOpener;
        this.userLinkUI = new UserLinkUI();
        this.userManager = userManager;
    }

    @Override
    protected void displayOptions() {
        userLinkUI.displayUserLinkMenu();
    }

    @Override
    protected void processInput(int index) {
        switch (index) {
            case(1):
                // Add a link to your profile
                addLink();
                break;
            case(2):
                // Explore other user's links
                accessLinks();
                break;
            case(3):
                // See your own links
                userLinkUI.displayOptions(userManager.getUserLinks(userManager.getLoggedInUserUUID()), false);
                break;
        }
    }

    private void addLink() {
        List<String> socials = socialsToStrings();

        IndexProcessor<Integer> optionProcessor = new OptionIndexProcessor(new Scanner(System.in), socials.size());
        userLinkUI.displayAddLinksPrompt();
        userLinkUI.displayOptions(socials, false);
        int index = optionProcessor.processInput() - 1;
        Socials social = Socials.values()[index];

        userLinkUI.displayLinkPrompt(social.toString());
        String link = askForString(social + " link");
        while (!webValidator.validateLink(social, link)) {
            userLinkUI.displayError("Invalid " + social + " link! Please enter a valid link.");
            link = askForString(social + " link");
        }

        if (!link.contains("https://www.") && !link.contains("www.")) {
            link = "https://www." + link;
        }

        userManager.setLoggedInUserLink(social, link);
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
            userLinkUI.displayOptions(userLinks, false);
            IndexProcessor<Integer> optionProcessor = new OptionIndexProcessor(new Scanner(System.in),
                    userLinks.size());
            int index = optionProcessor.processInput() - 1;
            webOpener.openWeb(userLinks.get(index));
        }
        else {
            userLinkUI.displayEmptyLinksError();
        }
    }

    @Override
    public String toString() {
        return "Social Media Links";
    }
}
