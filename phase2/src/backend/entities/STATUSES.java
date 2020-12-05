package backend.entities;

/** Enumeration set for message status.
 */
public enum STATUSES {
    read(true),
    unread(true),
    archived(false);
    private boolean overReadable;

    private STATUSES(boolean overReadable) {
        this.overReadable = overReadable;
    }

    public boolean isOverReadable() {
        return overReadable;
    }
}
