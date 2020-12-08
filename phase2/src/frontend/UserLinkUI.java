package frontend;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that displays prompts and messages for actions related to users' social network links.
 */
public class UserLinkUI extends MenuUI {

    /**
     * Displays a list of options pertaining to users' social network links.
     */
    public void displayUserLinkMenu() {
        List<String> options = new ArrayList<>();
        options.add("Add link");
        options.add("Explore other user's links");
        options.add("View your own links");
        displayOptions(options, true);
    }

    /**
     * Prompts the user to enter a valid link type to add.
     */
    public void displayAddLinksPrompt() {
        System.out.println("What link would you like to add?");
    }

    /**
     * Prompts the user to enter a link of the specified linkType.
     * @param linkType the type of link to be inputted by the user
     */
    public void displayLinkPrompt(String linkType) {
        System.out.println("Enter a " + linkType + " link.");
    }

    /**
     * Prompts the user to enter the username of the user they would like to view links for.
     */
    public void displayExploreLinksPrompt() {
        System.out.println("Enter the user name of whom you would like to explore.");
    }

    /**
     * Informs the user that a user has no links.
     */
    public void displayEmptyLinksError() {
        System.out.println("This user has no links!");
    }
}
