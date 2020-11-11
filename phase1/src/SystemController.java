import FileHandleSystem.FileSerializer;
import FileHandleSystem.TerminationWorker;
import LoginSystem.AuthenticationSystem;
import LoginSystem.UserManager;

import java.util.HashMap;
import java.util.Map;

public class SystemController implements Runnable{

    private Map<String, Object> managers = new HashMap<>();
    private Map<Integer, Runnable> subSystems = new HashMap<>();

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


    }

    private void initializeSubSystems(){
        initializeLoginSystem();
        initializeEventSystem();
        initializeMessageSystem();

        initializeShutDownHook();
    }

    private void initializeLoginSystem(){
        String filePath = "../database/UManager.ser";
        FileSerializer<UserManager> userManagerLoader = new FileSerializer<>(filePath);
        UserManager uManager = userManagerLoader.loadObject();
        AuthenticationSystem authenticationSystem = new AuthenticationSystem(uManager);
        subSystems.put(0, authenticationSystem);
        managers.put(filePath, uManager);
    }

    private void initializeMessageSystem(){

    }

    private void initializeEventSystem(){

    }

    private void initializeShutDownHook(){
        managers.forEach((s, o) -> {
            TerminationWorker<Object> objSaver = new TerminationWorker<>(o, s);
            Runtime.getRuntime().addShutdownHook(objSaver);
        });
    }
}

