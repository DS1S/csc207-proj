import CoreEntities.Users.Perms;
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

public class SystemController implements IRunnable {

    private final Map<String, Object> managers = new HashMap<>();
    private final Map<Integer, IRunnable> subSystems = new HashMap<>();
    private final MainMenuUI mainMenu = new MainMenuUI();

    public void run(){
        initializeSubSystems();

        Scanner input = new Scanner(System.in);
        int option = 0;
        while (option != subSystems.size() + 1){
            mainMenu.displayMainMenu(subSystems);

            try{
                option = input.nextInt();
                if (subSystems.containsKey(option)){
                    subSystems.get(option).run();
                }
            }
            catch (InputMismatchException e){
                mainMenu.displayInvalidInput(subSystems);
            }
        }
    }

    private void initializeSubSystems(){
        UserManager userManager = initializeLoginSystem();
        EventManager eventManager = initializeEventSystem(userManager);

        initializeMessageSystem(userManager, eventManager);
        initializeShutDownHook();

        subSystems.get(0).run();
        initializeUserCreatorSystem(userManager);

    }

    private UserManager initializeLoginSystem(){
        String filePath = "phase1/database/UManager.ser";
        FileSerializer<UserManager> userManagerLoader = new FileSerializer<>(filePath);
        UserManager uManager = userManagerLoader.loadObject();
        IRunnable authenticationSystem = new AuthenticationSystem(uManager);
        addSystemAndManager(filePath, authenticationSystem, uManager, 0);
        return uManager;
    }

    private void initializeMessageSystem(UserManager userManager, EventManager eventManager){
        String filePath = "phase1/database/MSManager.ser";
        FileSerializer<MessageManager> messageManagerLoader = new FileSerializer<>(filePath);
        MessageManager msManager = messageManagerLoader.loadObject();
        IRunnable messageSystem = new MessageSystem(msManager, userManager, eventManager);
        addSystemAndManager(filePath, messageSystem, msManager, subSystems.size() + 1);
    }

    private EventManager initializeEventSystem(UserManager userManager){
        String filePath = "phase1/database/ESManager.ser";
        FileSerializer<EventManager> eventManagerLoader = new FileSerializer<>(filePath);
        EventManager eventManager = eventManagerLoader.loadObject();
        IRunnable eventSystem = new EventSystem(eventManager, userManager);
        addSystemAndManager(filePath, eventSystem, eventManager, subSystems.size() + 1);
        return eventManager;
    }

    private void initializeUserCreatorSystem(UserManager userManager){
        if (userManager.loggedInHasPermission(Perms.canSignUpUser)){
            IRunnable signUpSystem = new SignupSystem(userManager, "speaker");
            subSystems.put(subSystems.size() + 1, signUpSystem);
        }
    }

    private void addSystemAndManager(String filePath, IRunnable sys, Object manager, int index){
        subSystems.put(index, sys);
        managers.put(filePath, manager);
    }

    private void initializeShutDownHook(){
        managers.forEach((s, o) -> {
            TerminationWorker<Object> objSaver = new TerminationWorker<>(o, s);
            Runtime.getRuntime().addShutdownHook(objSaver);
        });
    }
}

