package CoreEntities;

import java.util.UUID;
import java.lang.String;
import java.time.LocalTime;

public class Message {
    private UUID sender;
    private UUID recipient;
    private String body;
    private LocalTime timeSent;
}
