package backend.systems.admin;

import backend.systems.events.managers.EventManager;
import backend.systems.messaging.managers.MessageManager;
import backend.entities.users.PERMS;
import backend.systems.MenuSystem;
import backend.systems.usermangement.managers.UserManager;
import frontend.AdminUI;

import java.util.HashMap;
import java.util.Map;

public class AdminSystem extends MenuSystem {
    private UserManager um;
    private MessageManager messageManager;
    private EventManager em;
    private AdminUI adminUI;
    private boolean[] perms;
    private Map<Integer, Integer> optionToPerm;

    private final int NUM_PERMS = 3;
    private final int CAN_VIEW_STATS = 0;
    private final int CAN_BAN_USERS = 1;
    private final int CAN_SEE_ALL_MESSAGES = 2;

    public AdminSystem(UserManager um, MessageManager messageManager, EventManager eventManager){
        super();
        this.um = um;
        this.messageManager = messageManager;
        this.em = eventManager;
        adminUI = new AdminUI();
        optionToPerm = new HashMap<>();
        changeNumOptions(readyPerms() + 1);
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
        switch (optionToPerm.get(index-1)){
            case CAN_VIEW_STATS:
                StatisticsSystem statSys = new StatisticsSystem(em, um);
                statSys.run();
                break;
            case CAN_BAN_USERS:
                BanningSystem banSys = new BanningSystem(um);
                banSys.run();
                break;
            case CAN_SEE_ALL_MESSAGES:
                MessageViewingSystem msgViewSys = new MessageViewingSystem(um, messageManager);
                msgViewSys.run();
                break;
        }
    }

    @Override
    public String toString() {
        return "Admin Panel";
    }
}
