package CoreEntities;

import java.time.LocalTime;
import java.util.*;

public class Schedule implements Iterable<Event> {
    private List<Event> events;

    public Schedule() {
        this.events = new ArrayList<>();
    }

    public Schedule(List<Event> events) { this.events = events; }

    public void addEvent(Event event) {
        int i = 0;
        while (events.get(i).getStartTime().isBefore(event.getStartTime())) {
            i++;
        }
        events.add(i, event);
    }

    public Schedule retrieveEventByTimeInterval(LocalTime start, LocalTime end) {
        List<Event> matchedEvents = new ArrayList<>();
        for (Event event: events) {
            if (event.getStartTime().compareTo(start) <= 0 && event.getEndTime().compareTo(start) >= 0
                    && event.getEndTime().compareTo(end) <= 0) {
                matchedEvents.add(event);
            }
            else if (event.getStartTime().compareTo(start) >= 0 && event.getStartTime().compareTo(end) < 0
                    && event.getEndTime().compareTo(end) > 0) {
                matchedEvents.add(event);
            }
        }
        return new Schedule(matchedEvents);
    }

    public Schedule retrieveEventBySpeaker(UUID speaker) {
        List<Event> matchedEvents = new ArrayList<>();
        for (Event event: events) {
            if (event.getSpeaker().equals(speaker)) {
                matchedEvents.add(event);
            }
        }
        return new Schedule(matchedEvents);
    }

    public Schedule retrieveEventByTitle(String title) {
        List<Event> matchedEvents = new ArrayList<>();
        for (Event event: events) {
            if (event.getTitle().equals(title)) {
                matchedEvents.add(event);
            }
        }
        return new Schedule(matchedEvents);
    }

    public Schedule retrieveEventByAttendee(UUID attendee) {
        List<Event> matchedEvents = new ArrayList<>();
        for (Event event: events) {
            if (event.checkAttendee(attendee)) {
                matchedEvents.add(event);
            }
        }
        return new Schedule(matchedEvents);
    }

    public Schedule retrieveSignupAbleEvents(UUID attendee) {
        List<Event> matchedEvents = new ArrayList<>();
        for (Event event: events) {
            if (!event.checkAttendee(attendee) && !event.atCapacity()) {
                matchedEvents.add(event);
            }
        }
        return new Schedule(matchedEvents);
    }

    public Event retrieveEventByIndex(int index) throws IndexOutOfBoundsException {
        return events.get(index);
    }

    public void removeEventByIndex(int index) throws IndexOutOfBoundsException {
        events.remove(index);
    }

    @Override
    public String toString() {
        int i = 0;
        StringBuilder scheduleStr = new StringBuilder();
        for (Event event: events) {
            String newRow = i + ") " + event + "\n\n";
            scheduleStr.append(newRow);
        }
        return scheduleStr.toString();
    }

    @Override
    public Iterator<Event> iterator() { return new ScheduleIterator(); }

    private class ScheduleIterator implements Iterator<Event> {

        /** The index of the next Event to return. */
        private int current = 0;

        /**
         * Returns whether there is another Event to return.
         * @return whether there is another Event to return.
         */
        @Override
        public boolean hasNext() {
            return current < events.size();
        }

        /**
         * Returns the next Event.
         * @return the next Event.
         */
        @Override
        public Event next() {
            Event res;

            // List.get(i) throws an IndexOutBoundsException if
            // we call it with i >= events.size().
            // But Iterator's next() needs to throw a
            // NoSuchElementException if there are no more elements.
            try {
                res = events.get(current);
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
            current += 1;
            return res;
        }
    }
}