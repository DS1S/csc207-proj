package backend.systems.admin;

import backend.systems.MenuSystem;
import backend.systems.usermangement.managers.UserManager;
import frontend.AdminUI;

import java.util.Scanner;

/**
 * An extension of MenuSystem that displays an Admin their banning options and processes their input.
 */
class BanningSystem extends MenuSystem {
    private UserManager um;
    private AdminUI adminUI;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs a new BanningSystem with the given information.
     * @param um The UserManager that will be used by the AdminSystem
     */
    public BanningSystem(UserManager um){
        super(3);
        adminUI = new AdminUI();
        this.um = um;
    }
    /**
     * Displays banning options for the user to choose.
     */
    @Override
    protected void displayOptions() {
        adminUI.displayBanOptions();
    }

    /**
     * Processes input for the selection of a ban option.
     * @param index the index of the option chosen.
     */
    @Override
    protected void processInput(int index) {
        index--;
        adminUI.promptUserName();
        String username = scanner.nextLine();
        if (um.getUUIDWithUsername(username) == null){
            adminUI.displayInvalidUser();
            return;
        }
        um.setUserBan(um.getUUIDWithUsername(username), index==1);
        adminUI.displayBanSuccess(index==1, username);
    }
}
