import CoreEntities.Users.Perms;
import FileHandleSystem.FileSerializer;
import FileHandleSystem.TerminationWorker;
import LoginSystem.AuthenticationSystem;
import LoginSystem.UserManager;
import MessagingSystem.MessageManager;
import MessagingSystem.MessageSystem;
import SchedulingSystem.EventManager;
import SchedulingSystem.EventSystem;
import coreUtil.IRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SystemController implements IRunnable {

    private Map<String, Object> managers = new HashMap<>();
    private Map<Integer, IRunnable> subSystems = new HashMap<>();

    public void run(){
        initializeSubSystems();

        //TODO
        /*
        We need to essentially control the main menu from here, this will be the main loop
        all other subsystems will run within here until program terminates.

        Authenticate the user via authentication system;
        Add only the systems the user is allowed to access into subSystems;
        This can be done by adding some method that check if a user has a required permission.

        while(user is logged in):
            Present menu for all different systems; Might vary based on permissions
            Cycle between user's choice then run the required system;
            once the user exists that it will return to this loop and continue until fully
            exited;
        */

        subSystems.get(0).run();
        Scanner input = new Scanner(System.in);
        int option = input.nextInt();
        while (option != subSystems.size() + 1){
            // Walter display the menu here
            subSystems.get(option).run();
        }

    }

    private void initializeSubSystems(){
        UserManager userManager = initializeLoginSystem();

        initializeUserCreatorSystem(userManager);
        initializeMessageSystem(userManager);
        initializeEventSystem(userManager);

        initializeShutDownHook();
    }

    private UserManager initializeLoginSystem(){
        String filePath = "../database/UManager.ser";
        FileSerializer<UserManager> userManagerLoader = new FileSerializer<>(filePath);
        UserManager uManager = userManagerLoader.loadObject();
        IRunnable authenticationSystem = new AuthenticationSystem(uManager);
        subSystems.put(0, authenticationSystem);
        managers.put(filePath, uManager);
        return uManager;
    }

    private void initializeMessageSystem(UserManager userManager){
        String filePath = "../database/MSManager.ser";
        FileSerializer<MessageManager> messageManagerLoader = new FileSerializer<>(filePath);
        MessageManager msManager = messageManagerLoader.loadObject();
        IRunnable messageSystem = new MessageSystem(msManager, userManager);
        subSystems.put(subSystems.size() + 1, messageSystem);
        managers.put(filePath, msManager);
    }

    private void initializeEventSystem(UserManager userManager){
        String filePath = "../database/ESManager.ser";
        FileSerializer<EventManager> eventManagerLoader = new FileSerializer<>(filePath);
        EventManager eventManager = eventManagerLoader.loadObject();
        IRunnable eventSystem = new EventSystem();
        subSystems.put(subSystems.size() + 1, eventSystem);
        managers.put(filePath, eventManager);
    }

    private void initializeUserCreatorSystem(UserManager userManager){
        if (userManager.loggedInHasPermission(Perms.canSignUpUser)){
            //Need UserCreatorSystem
        }
    }

    private void initializeShutDownHook(){
        managers.forEach((s, o) -> {
            TerminationWorker<Object> objSaver = new TerminationWorker<>(o, s);
            Runtime.getRuntime().addShutdownHook(objSaver);
        });
    }
}

