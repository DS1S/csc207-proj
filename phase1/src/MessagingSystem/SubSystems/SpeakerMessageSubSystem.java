package MessagingSystem.SubSystems;

import EventSystem.Managers.EventManager;
import LoginSystem.UserManager;
import MessagingSystem.MessageManager;
import Presenters.OptionUI;
import coreUtil.InputProcessors.IndexProcessor;
import coreUtil.InputProcessors.OptionIndexProcessor;

import java.util.*;

/**
 * A subsystem of the MessageSystem that allows the user to perform actions related to a Speaker sending messages.
 */
public class SpeakerMessageSubSystem extends MessageSubSystem {

    private OptionUI optionUI;
    private EventManager eventManager;

    /**
     * Creates an object of SpeakerMessageSubSystem
     * @param userManager a UserManager object that is already instantiated at the point this is instantiated
     * @param messageManager a MessageManager object that is already instantiated at the point this is instantiated
     * @param numOptions number of options in the menu
     * @param eventManager a EventManager object that is already instantiated at the point this is instantiated
     */
    public SpeakerMessageSubSystem(UserManager userManager, MessageManager messageManager, int numOptions,
                                   EventManager eventManager) {
        super(userManager, messageManager, numOptions);
        this.optionUI = new OptionUI();
        this.eventManager = eventManager;
    }

    /**
     * Tells the inbox UI to display options in the speaker messaging page.
     */
    @Override
    protected void displayOptions() {
        inboxUI.displayTalkSpeakerMenuOptions();
    }

    /**
     * Processes an integer input in the speaker's messaging page.
     * @param index The input to be processed. 1 allows for displaying all messages to the speaker.
     *              2 allows for replying to an attendee. 3 allows for making an announcement to the
     *              speaker's talk(s).
     */
    @Override
    protected void processMainSignInput(int index) {
        switch (index) {
            case(1):
                processBaseInput(index);
                break;
            case(2):
                replyToAttendee();
                break;
            case(3):
                processMessageToTalks();
                break;
        }
    }

    private void processMessageToTalks() {
        if (eventManager.retrieveEventsBySpeaker(userManager.getLoggedInUserUUID()).isEmpty()) {
            inboxUI.displayError("You aren't hosting any talks!");
        }
        else{
            inboxUI.talksPrompt();
            String talks = askForString("Talk(s)");
            inboxUI.displayBodyPrompt();
            String message = processMessageBody();
            sendMessageToTalks(Arrays.asList(talks.split(",")), message);
        }
    }

    private void sendMessageToTalks(List<String> events, String msg) {
        List<UUID> attendeeUUIDs = new ArrayList<>();
        for (String event : events) {
            List<UUID> eventUUIDs = eventManager.retrieveAttendees(event, userManager.getLoggedInUserUUID());
            attendeeUUIDs.addAll(eventUUIDs);
        }
        if (attendeeUUIDs.isEmpty()) inboxUI.displayError("No one is attending your talks!");
        else inboxUI.sentPrompt();
        messageManager.sendMessageToMultiple(userManager.getLoggedInUserUUID(), attendeeUUIDs, msg);
    }

    private void replyToAttendee() {
        List<Map<String, Object>> messagesData = messageManager.getInboxData(userManager.getLoggedInUserUUID());
        int index = processMessages(messagesData) - 1;

        if(index != -1) {
            UUID replierUUID = (UUID)messagesData.get(index).get("sender");
            String message = processMessageBody();
            messageManager.sendMessageToIndividual(userManager.getLoggedInUserUUID(), replierUUID, message);
            inboxUI.sentPrompt();
        }
    }

    private int processMessages(List<Map<String, Object>> messagesData) {
        inboxUI.displayInbox(messagesData);
        if(!messagesData.isEmpty()) {
            IndexProcessor<Integer> eventProcessor = new OptionIndexProcessor(input, messagesData.size());
            return eventProcessor.processInput();
        }
        return 0;
    }
}
