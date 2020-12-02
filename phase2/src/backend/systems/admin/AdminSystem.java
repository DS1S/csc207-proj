package backend.systems.admin;

import backend.entities.users.PERMS;
import backend.systems.MenuSystem;
import backend.systems.usermangement.managers.UserManager;
import frontend.AdminUI;
import utility.inputprocessors.OptionIndexProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminSystem extends MenuSystem {
    private UserManager um;
    private AdminUI adminUI;
    private boolean[] perms;
    private Map<Integer, Integer> optionToPerm;

    private final int NUM_PERMS = 3;
    private final int CAN_VIEW_STATS = 0;
    private final int CAN_BAN_USERS = 1;
    private final int CAN_SEE_ALL_MESSAGES = 2;

    public AdminSystem(UserManager um){
        super();
        this.um = um;
        changeNumOptions(readyPerms());
        adminUI = new AdminUI();
    }

    private int readyPerms(){
        perms = new boolean[NUM_PERMS];
        perms[CAN_VIEW_STATS] = um.hasPermission(um.getLoggedInUserUUID(), PERMS.canViewStats);
        perms[CAN_BAN_USERS] = um.hasPermission(um.getLoggedInUserUUID(), PERMS.canBanUsers);
        perms[CAN_SEE_ALL_MESSAGES] = um.hasPermission(um.getLoggedInUserUUID(), PERMS.canSeeAllMessages);

        int numOptions = 0;
        for (int i = 0; i < NUM_PERMS; i++){
            if (perms[i]){
                optionToPerm.put(numOptions, i);
            }
            numOptions += perms[i]?1:0;
        }
        return numOptions;
    }


    @Override
    protected void displayOptions() {
        adminUI.displayAdminOptions(perms);
    }

    @Override
    protected void processInput(int index) {
        // TODO: maybe use subsystems?
        switch (optionToPerm.get(index)){
            case CAN_VIEW_STATS:
                //StatisticsSystem statSys = new StatisticsSystem();
                //statSys.run();
                break;
            case CAN_BAN_USERS:
                break;
            case CAN_SEE_ALL_MESSAGES:
                break;
        }
    }
}
