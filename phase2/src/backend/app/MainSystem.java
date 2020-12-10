package backend.app;

import backend.entities.users.Perms;
import backend.systems.MenuSystem;
import backend.systems.admin.AdminSystem;
import backend.systems.conference.ConferenceManager;
import backend.systems.conference.ConferenceSystem;
import frontend.MainUI;
import utility.RunnableSystem;
import utility.filehandling.FileSerializer;
import utility.filehandling.TerminationWorker;
import backend.systems.usermangement.AuthenticationSystem;
import backend.systems.usermangement.SignupSystem;
import backend.systems.usermangement.managers.UserManager;
import backend.systems.social.managers.MessageManager;
import backend.systems.social.SocialSystem;
import backend.systems.events.managers.EventManager;
import backend.systems.events.EventSystem;

import java.util.*;

/**
 * Class which controls the interaction between all the subsystems.
 */
class MainSystem extends MenuSystem {
    private final Map<String, Object> managers = new HashMap<>();
    private final Map<Integer, RunnableSystem> subSystems = new HashMap<>();
    private final MainUI mainMenu = new MainUI();

    private final String[] eventManagerFilePaths = {"phase2/database/ESManagerCon1.ser",
                                                    "phase2/database/ESManagerCon2.ser",
                                                    "phase2/database/ESManagerCon3.ser"};
    private List<String> subSystemNames = new ArrayList<>();

    /**
     * Constructs a MainSystem by initializing all subsystems and then running the first one
     */
    public MainSystem() {
        initializeSubSystems();
        changeNumOptions(subSystems.size());
    }

    /**
     * Displays the options for the main menu
     */
    @Override
    protected void displayOptions() {
        mainMenu.displayMainMenu(subSystemNames);
    }

    /**
     * Processes a user input by passing it to the appropriate subsystem
     * @param index The input to be processed.
     */
    @Override
    protected void processInput(int index) {
        if (subSystems.containsKey(index)) {
            subSystems.get(index).run();
        }
    }

    private void initializeSubSystems() {
        UserManager userManager = initializeAuthenticationSystem();
        subSystems.get(0).run();

        List<EventManager> eventManagers = initializeConferenceSystem(userManager);
        initializeUserCreatorSystem(userManager);
        MessageManager messageManager = initializeMessageSystem(userManager, eventManagers);
        initializeAdminSystem(userManager, eventManagers, messageManager);
        initializeShutDownHook();

        subSystemNames = convertSubSystemsToNames(subSystems);
        subSystemNames.remove(0);
    }

    private UserManager initializeAuthenticationSystem() {
        String filePath = "phase2/database/UManager.ser";
        FileSerializer<UserManager> userManagerLoader = new FileSerializer<>(filePath);
        UserManager uManager = userManagerLoader.loadObject();
        RunnableSystem authenticationSystem = new AuthenticationSystem(uManager);
        addSystemAndManager(filePath, authenticationSystem, uManager, 0);
        return uManager;
    }

    private MessageManager initializeMessageSystem(UserManager userManager, List<EventManager> eventManagers) {
        String filePath = "phase2/database/MSManager.ser";
        FileSerializer<MessageManager> messageManagerLoader = new FileSerializer<>(filePath);
        MessageManager msManager = messageManagerLoader.loadObject();
        RunnableSystem messageSystem = new SocialSystem(msManager, userManager, eventManagers);
        if(!msManager.userHasInbox(userManager.getLoggedInUserUUID()))
            msManager.addBlankInbox(userManager.getLoggedInUserUUID());
        addSystemAndManager(filePath, messageSystem, msManager, subSystems.size());
        return msManager;
    }

    private List<EventManager> initializeConferenceSystem(UserManager userManager){
        String filepath = "phase2/database/CCManager.ser";
        FileSerializer<ConferenceManager> conferenceManagerLoader = new FileSerializer<>(filepath);
        ConferenceManager conferenceManager = conferenceManagerLoader.loadObject();
        return initializeEventSystems(userManager, conferenceManager, filepath);
    }

    private List<EventManager> initializeEventSystems(UserManager userManager, ConferenceManager conferenceManager,
                                                      String conferenceMangerFilePath) {
        List<EventManager> eventManagers = new ArrayList<>();
        List<EventSystem> eventSystems = new ArrayList<>();

        for (String filePath: eventManagerFilePaths){
            FileSerializer<EventManager> eventManagerLoader = new FileSerializer<>(filePath);
            EventManager eventManager = eventManagerLoader.loadObject();
            EventSystem eventSystem = new EventSystem(eventManager, userManager);
            eventSystems.add(eventSystem);
            eventManagers.add(eventManager);
            managers.put(filePath, eventManager);
        }
        RunnableSystem conferenceSystem = new ConferenceSystem(eventSystems, conferenceManager);
        addSystemAndManager(conferenceMangerFilePath, conferenceSystem, conferenceManager, subSystems.size());
        return eventManagers;
    }

    private void initializeUserCreatorSystem(UserManager userManager) {
        if (userManager.loggedInHasPermission(Perms.CAN_SIGN_UP_USER)) {
            ArrayList<String> types = new ArrayList(){
                {
                    add("speaker");
                    add("organizer");
                    add("attendee");
                }
            };
            RunnableSystem signUpSystem = new SignupSystem(userManager, types);
            subSystems.put(subSystems.size(), signUpSystem);
        }
    }

    private void initializeAdminSystem(UserManager userManager, List<EventManager> eventManager,
                                       MessageManager messageManager){
        if(userManager.loggedInHasPermission(Perms.CAN_BAN_USERS) || userManager.loggedInHasPermission(Perms.CAN_VIEW_STATS)){
            RunnableSystem adminSystem = new AdminSystem(userManager, messageManager, eventManager);
            subSystems.put(subSystems.size(), adminSystem);
        }
    }

    private void addSystemAndManager(String filePath, RunnableSystem sys, Object manager, int index) {
        subSystems.put(index, sys);
        managers.put(filePath, manager);
    }


    private void initializeShutDownHook() {
        managers.forEach((s, o) -> {
            TerminationWorker<Object> objSaver = new TerminationWorker<>(o, s);
            Runtime.getRuntime().addShutdownHook(objSaver);
        });
    }
}

