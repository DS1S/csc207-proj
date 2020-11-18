package Main;

/**
 * Main class which runs the TecHConference program.
 */
public class TechConferenceMain {
    /**
     * Main method which creates a system controller and runs it
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        SystemController techConferenceController = new SystemController();
        techConferenceController.run();
    }
}
