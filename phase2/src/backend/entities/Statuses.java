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

    /**
     * Overrides the built-in toString method.
     * @return name of a status as a string object
     */
    @Override
    public String toString() {
        return statusName;
    }

    /**
     * Indicates if the status of a message is overwritten with "Read" when message is read,
     * @return true if status of a message is changed to "Read" when message is read, false otherwise
     */
    public boolean isOverReadable() {
        return overReadable;
    }
}
