package backend.app;

import backend.entities.users.PERMS;
import backend.systems.MenuSystem;
import backend.systems.conference.ConferenceManager;
import backend.systems.conference.ConferenceSystem;
import frontend.MainUI;
import utility.RunnableSystem;
import utility.filehandling.FileSerializer;
import utility.filehandling.TerminationWorker;
import backend.systems.usermangement.AuthenticationSystem;
import backend.systems.usermangement.SignupSystem;
import backend.systems.usermangement.managers.UserManager;
import backend.systems.messaging.managers.MessageManager;
import backend.systems.messaging.MessageSystem;
import backend.systems.events.managers.EventManager;
import backend.systems.events.EventSystem;

import java.io.IOException;
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

    public MainSystem(){
        initializeSubSystems();
        changeNumOptions(subSystems.size());
    }

    @Override
    protected void displayOptions() {
        mainMenu.displayMainMenu(subSystemNames);
    }

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
        initializeMessageSystem(userManager, eventManagers);
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

    private void initializeMessageSystem(UserManager userManager, List<EventManager> eventManagers) {
        String filePath = "phase2/database/MSManager.ser";
        FileSerializer<MessageManager> messageManagerLoader = new FileSerializer<>(filePath);
        MessageManager msManager = messageManagerLoader.loadObject();
        RunnableSystem messageSystem = new MessageSystem(msManager, userManager, eventManagers);
        if(!msManager.userHasInbox(userManager.getLoggedInUserUUID()))
            msManager.addBlankInbox(userManager.getLoggedInUserUUID());
        addSystemAndManager(filePath, messageSystem, msManager, subSystems.size());
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
        if (userManager.loggedInHasPermission(PERMS.canSignUpUser)) {
            RunnableSystem signUpSystem = new SignupSystem(userManager, "speaker");
            subSystems.put(subSystems.size(), signUpSystem);
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

