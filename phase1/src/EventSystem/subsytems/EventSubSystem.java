package EventSystem.subsytems;

import EventSystem.Managers.EventManager;
import LoginSystem.UserManager;
import Main.SubSystem;
import Presenters.EventUI;
import coreUtil.InputProcessors.OptionIndexProcessor;


import java.util.Scanner;

public abstract class EventSubSystem extends SubSystem {

    protected final EventManager eventManager;
    protected final UserManager userManager;
    protected final EventUI eventUI;
    protected final int numOptions;


    public EventSubSystem(EventManager eventManager, UserManager userManager, EventUI eventUI, int numOptions){
        super(numOptions, new OptionIndexProcessor(new Scanner(System.in), numOptions));
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.eventUI = eventUI;
        this.numOptions = numOptions;
    }
}
