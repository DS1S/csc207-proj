package EventSystem.subsytems;

import EventSystem.Managers.EventManager;
import LoginSystem.UserManager;
import Main.SubSystem;
import Presenters.EventUI;
import coreUtil.InputProcessors.OptionIndexProcessor;


import java.util.Scanner;

/**
 * An abstract subsystem of EventSystem that allows the user to perform a particular action related to the sign up
 * of Events.
 */
public abstract class EventSubSystem extends SubSystem {

    protected final EventManager eventManager;
    protected final UserManager userManager;
    protected final EventUI eventUI;
    protected final int numOptions;

    /**
     * Constructs a new EventSubSystem with the given information.
     * @param eventManager the EventManager that will be used by the EventSubSystem
     * @param userManager the UserManager that will be used by the EventSubSystem
     * @param eventUI the EventUI that will be used by the EventSubSystem
     * @param numOptions the number of menu options given by the EventSubSystem
     */
    public EventSubSystem(EventManager eventManager, UserManager userManager, EventUI eventUI, int numOptions){
        super(numOptions, new OptionIndexProcessor(new Scanner(System.in), numOptions));
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.eventUI = eventUI;
        this.numOptions = numOptions;
    }
}
