package backend.app;

/**
 * Main class which runs the TecHConference program.
 */
class AppInitializer {
    /**
     * Main method which creates a system controller and runs it.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        ConferenceSystem app = new ConferenceSystem();
        app.run();
    }
}
