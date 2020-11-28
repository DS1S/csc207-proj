package Main;

import CoreEntities.Users.PERMS;
import FileHandleSystem.FileSerializer;
import FileHandleSystem.TerminationWorker;
import LoginSystem.AuthenticationSystem;
import LoginSystem.SignupSystem;
import LoginSystem.UserManager;
import MessagingSystem.MessageManager;
import MessagingSystem.MessageSystem;
import Presenters.MainMenuUI;
import EventSystem.Managers.EventManager;
import EventSystem.EventSystem;
import coreUtil.IRunnable;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * Class which controls the interaction between all the subsystems.
 */
public class SystemController implements IRunnable {
    private final Map<String, Object> managers = new HashMap<>();
    private final Map<Integer, IRunnable> subSystems = new HashMap<>();
    private final MainMenuUI mainMenu = new MainMenuUI();

    /**
     * Runs all the subsystems by switching between the MainUI and the each subsystem depending on what the user inputs.
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
        UserManager userManager = initializeLoginSystem();
        EventManager eventManager = initializeEventSystem(userManager);

        subSystems.get(0).run();
        initializeUserCreatorSystem(userManager);
        initializeMessageSystem(userManager, eventManager);
        initializeShutDownHook();
    }

    private UserManager initializeLoginSystem() {
        String filePath = "phase1/database/UManager.ser";
        FileSerializer<UserManager> userManagerLoader = new FileSerializer<>(filePath);
        UserManager uManager = userManagerLoader.loadObject();
        IRunnable authenticationSystem = new AuthenticationSystem(uManager);
        addSystemAndManager(filePath, authenticationSystem, uManager, 0);
        return uManager;
    }

    private void initializeMessageSystem(UserManager userManager, EventManager eventManager) {
        String filePath = "phase1/database/MSManager.ser";
        FileSerializer<MessageManager> messageManagerLoader = new FileSerializer<>(filePath);
        MessageManager msManager = messageManagerLoader.loadObject();
        IRunnable messageSystem = new MessageSystem(msManager, userManager, eventManager);
        if(!msManager.userHasInbox(userManager.getLoggedInUserUUID()))
            msManager.addBlankInbox(userManager.getLoggedInUserUUID());
        addSystemAndManager(filePath, messageSystem, msManager, subSystems.size());
    }

    private EventManager initializeEventSystem(UserManager userManager) {
        String filePath = "phase1/database/ESManager.ser";
        FileSerializer<EventManager> eventManagerLoader = new FileSerializer<>(filePath);
        EventManager eventManager = eventManagerLoader.loadObject();
        IRunnable eventSystem = new EventSystem(eventManager, userManager);
        addSystemAndManager(filePath, eventSystem, eventManager, subSystems.size());
        return eventManager;
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

