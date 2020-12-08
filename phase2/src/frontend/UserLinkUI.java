package frontend;

import backend.entities.users.Socials;

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
        options.add("Add a link");
        options.add("Remove a link");
        options.add("Explore other user's links");
        options.add("View your own links");
        displayOptions(options, true, true);
    }

    /**
     * Prompts the user to enter a valid link type to add.
     */
    public void displayAddLinksPrompt() {
        System.out.println("What link would you like to add?");
    }

    /**
     * Prompts the user that a link has been added.
     * @param social The social media platform of the link that was added.
     */
    public void displayLinkAddedPrompt(String social) { System.out.println("\n" + social + " link added!");}

    /**
     * Prompts the user to enter a link of the specified linkType.
     * @param linkType the type of link to be inputted by the user
     */
    public void displayLinkPrompt(String linkType) {
        System.out.println("Enter a " + linkType + " link.");
    }

    /**
     * Prompts the user that they are viewing their own links.
     */
    public void displayOwnLinksPrompt() { System.out.println("\nYour links");}

    /**
     * Prompts the user that they have no links added to their profile.
     */
    public void displayNoLinksPrompt() { System.out.println("\nYou have no links on your profile!"); }

    /**
     * Prompts the user to enter the username of the user they would like to view links for.
     */
    public void displayExploreLinksPrompt() {
        System.out.println("Enter the user name of whom you would like to explore.");
    }

    /**
     * Asks the user what link of the user they'd like to explore.
     * @param userFullName The full name of the user whose links are being explored.
     */
    public void displayExploreChoicesPrompt(String userFullName) {
        System.out.println("\n" + userFullName + "'s links:");
    }

    /**
     * Asks the user what link they would like to remove from their profile.
     */
    public void displayLinkRemovalPrompt() {
        System.out.println("\nWhich link would you like to remove from your profile?");
    }

    /**
     * Prompts the user that a link has been removed.
     * @param social The social media platform of the link that was removed.
     */
    public void displayLinkRemovedPrompt(String social) {
        System.out.println("\n" + social + " link removed!");
    }

    /**
     * Informs the user that a user has no links.
     */
    public void displayEmptyLinksError() {
        System.out.println("\nThere are no links on this profile!");
    }
}
