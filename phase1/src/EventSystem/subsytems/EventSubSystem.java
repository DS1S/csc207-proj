package EventSystem.subsytems;

import EventSystem.Managers.EventManager;
import LoginSystem.UserManager;
import Presenters.EventUI;
import coreUtil.IRunnable;
import coreUtil.InputProcessors.EventIndexProcessor;
import coreUtil.InputProcessors.IndexProcessor;

import java.util.Scanner;

public abstract class EventSubSystem implements IRunnable {

    protected final EventManager eventManager;
    protected final UserManager userManager;
    protected final EventUI eventUI;
    protected final int numOptions;

    protected final Scanner input = new Scanner(System.in);
    private final IndexProcessor<Integer> optionProcessor;

    public EventSubSystem(EventManager eventManager, UserManager userManager, EventUI eventUI, int numOptions){
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.eventUI = eventUI;
        this.numOptions = numOptions;
        optionProcessor = new EventIndexProcessor(input, eventUI, numOptions);
    }

    @Override
    public void run() {
        int option = 0;
        do{
            eventUI.displaySignupOptions();
            option = optionProcessor.processInput();
            processMainSignInput(option);
        }while(option != numOptions);
        input.close();
    }

    protected abstract void processMainSignInput(int index);
}
