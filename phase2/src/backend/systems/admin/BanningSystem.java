package backend.systems.admin;

import backend.systems.MenuSystem;
import backend.systems.usermangement.managers.UserManager;
import frontend.AdminUI;

import java.util.Scanner;

public class BanningSystem extends MenuSystem {
    UserManager um;
    private AdminUI adminUI;
    private final Scanner scanner = new Scanner(System.in);

    public BanningSystem(UserManager um){
        super(2);
        adminUI = new AdminUI();
        this.um = um;
    }

    @Override
    protected void displayOptions() {
        adminUI.displayBanOptions();
    }

    @Override
    protected void processInput(int index) {
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
