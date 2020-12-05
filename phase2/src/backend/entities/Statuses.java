package backend.entities;

/** Enumeration set for message status.
 */
public enum Statuses {
    READ(true, "Read"),
    UNREAD(true, "Unread"),
    ARCHIVED(false, "Archived");

    private boolean overReadable;
    private String statusName;

    Statuses(boolean overReadable, String statusName) {
        this.overReadable = overReadable;
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return statusName;
    }

    public boolean isOverReadable() {
        return overReadable;
    }
}
