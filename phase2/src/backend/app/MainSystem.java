package backend.app;

import backend.entities.users.PERMS;
import backend.systems.SubSystem;
import backend.systems.conference.ConferenceManager;
import backend.systems.conference.ConferenceSystem;
import utility.filehandling.FileSerializer;
import utility.filehandling.TerminationWorker;
import backend.systems.usermangement.AuthenticationSystem;
import backend.systems.usermangement.SignupSystem;
import backend.systems.usermangement.managers.UserManager;
import backend.systems.messaging.managers.MessageManager;
import backend.systems.messaging.MessageSystem;
import frontend.MainMenuUI;
import backend.systems.events.managers.EventManager;
import backend.systems.events.EventSystem;
import utility.IRunnable;

import java.util.*;

/**
 * Class which controls the interaction between all the subsystems.
 */
class MainSystem implements IRunnable {
    private final Map<String, Object> managers = new HashMap<>();
    private final Map<Integer, IRunnable> subSystems = new HashMap<>();
    private final MainMenuUI mainMenu = new MainMenuUI();

    private final String[] eventManagerFilePaths = {"phase2/database/ESManagerCon1.ser",
                                                    "phase2/database/ESManagerCon2.ser",
                                                    "phase2/database/ESManagerCon3.ser"};


    /**
     * Runs all the subsystems by switching between the MainMenuUI and the each subsystem depending on what the user inputs.
     */
    public void run() {
        initializeSubSystems();

        Scanner input = new Scanner(System.in);
        int option = 0;
        while (option != subSystems.size()) {
            mainMenu.displayMainMenu(subSystems);

            try{
                option = input.nextInt();
                if (subSystems.containsKey(option)) {
                    subSystems.get(option).run();
                }
            }
            catch (InputMismatchException e) {
                mainMenu.displayInvalidInput(subSystems);
                input.nextLine();
            }
        }
        input.close();
    }

    private void initializeSubSystems() {
        UserManager userManager = initializeAuthenticationSystem();
        List<EventManager> eventManagers = initializeConferenceSystem(userManager);

        subSystems.get(0).run();
        initializeUserCreatorSystem(userManager);
        initializeMessageSystem(userManager, eventManagers);
        initializeShutDownHook();
    }

    private UserManager initializeAuthenticationSystem() {
        String filePath = "phase2/database/UManager.ser";
        FileSerializer<UserManager> userManagerLoader = new FileSerializer<>(filePath);
        UserManager uManager = userManagerLoader.loadObject();
        IRunnable authenticationSystem = new AuthenticationSystem(uManager);
        addSystemAndManager(filePath, authenticationSystem, uManager, 0);
        return uManager;
    }

    private void initializeMessageSystem(UserManager userManager, List<EventManager> eventManagers) {
        String filePath = "phase2/database/MSManager.ser";
        FileSerializer<MessageManager> messageManagerLoader = new FileSerializer<>(filePath);
        MessageManager msManager = messageManagerLoader.loadObject();
        IRunnable messageSystem = new MessageSystem(msManager, userManager, eventManagers);
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
        IRunnable conferenceSystem = new ConferenceSystem(eventSystems, conferenceManager);
        addSystemAndManager(conferenceMangerFilePath, conferenceSystem, conferenceManager, subSystems.size());
        return eventManagers;
    }

    private void initializeUserCreatorSystem(UserManager userManager) {
        if (userManager.loggedInHasPermission(PERMS.canSignUpUser)) {
            IRunnable signUpSystem = new SignupSystem(userManager, "speaker");
            subSystems.put(subSystems.size(), signUpSystem);
        }
    }

    private void addSystemAndManager(String filePath, IRunnable sys, Object manager, int index) {
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

