package backend.app;

import utility.RunnableSystem;

/**
 * Main class which runs the TecHConference program.
 */
class AppInitializer {
    /**
     * Main method which creates a system controller and runs it.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        RunnableSystem app = new MainSystem();
        app.run();
    }
}
