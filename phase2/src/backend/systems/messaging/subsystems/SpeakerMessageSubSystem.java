package backend.systems.messaging.subsystems;

import backend.entities.users.PERMS;
import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import backend.systems.messaging.managers.MessageManager;
import frontend.OptionUI;
import utility.inputprocessors.IndexProcessor;
import utility.inputprocessors.OptionIndexProcessor;

import java.util.*;

/**
 * A subsystem of the MessageSystem that allows the user to perform actions related to a Speaker sending messages.
 */
class SpeakerMessageSubSystem extends MessageSubSystem {
    private OptionUI optionUI;
    private List<EventManager> eventManagers;

    /**
     * Creates a SpeakerMessageSubSystem object.
     * @param userManager A UserManager object that is already instantiated at the point this is instantiated.
     * @param messageManager A MessageManager object that is already instantiated at the point this is instantiated.
     * @param numOptions The Number of options in the menu.
     * @param eventManagers A EventManager object that is already instantiated at the point this is instantiated.
     */
    public SpeakerMessageSubSystem(UserManager userManager, MessageManager messageManager, int numOptions,
                                   List<EventManager> eventManagers) {
        super(userManager, messageManager, numOptions);
        this.optionUI = new OptionUI();
        this.eventManagers = eventManagers;
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
    protected void processInput(int index) {
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
        List<Map<String, Object>> eventsData = new ArrayList<>();
        for (EventManager eventManager: eventManagers){
            eventsData.addAll(eventManager.retrieveEventsBySpeaker(userManager.getLoggedInUserUUID()));
        }

        if (eventsData.isEmpty()) {
            inboxUI.displayError("You aren't hosting any talks!");
        }
        else{
            inboxUI.talksPrompt();
            String talks = askForString("Talk(s)");
            String message = processMessageBody();
            String title = processTitle();
            sendMessageToTalks(Arrays.asList(talks.split(",")), message, title);
        }
    }

    private void sendMessageToTalks(List<String> events, String msg, String title) {
        List<UUID> attendeeUUIDs = new ArrayList<>();
        for (String event : events) {
            List<UUID> eventUUIDs = new ArrayList<>();
            for(EventManager eventManager: eventManagers){
                eventManager.retrieveAttendees(event, userManager.getLoggedInUserUUID());
            }
            attendeeUUIDs.addAll(eventUUIDs);
        }
        if (attendeeUUIDs.isEmpty()) inboxUI.displayError("No one is attending your talks!");
        else inboxUI.sentPrompt();
        messageManager.sendMessageToMultiple(userManager.getLoggedInUserUUID(), attendeeUUIDs, msg, title);
    }

    private void replyToAttendee() {
        List<Map<String, Object>> messagesData = messageManager.getInboxData(userManager.getLoggedInUserUUID());
        int index = processMessages(messagesData) - 1;

        if(index != -1) {
            UUID replierUUID = (UUID)messagesData.get(index).get("sender");
            if(userManager.hasPermission(replierUUID, PERMS.canBeMessaged)) {
                String message = processMessageBody();
                String title = processTitle();
                messageManager.sendMessageToIndividual(userManager.getLoggedInUserUUID(), replierUUID, message, title);
                inboxUI.sentPrompt();
            }
            else{
                inboxUI.displayError("That User cannot be messaged.");
            }
        }
    }

    private int processMessages(List<Map<String, Object>> messagesData) {
        inboxUI.displayInbox(messagesData);
        if(!messagesData.isEmpty()) {
            IndexProcessor<Integer> optionIndexProcessor = new OptionIndexProcessor(input, messagesData.size());
            return optionIndexProcessor.processInput();
        }
        return 0;
    }
}
