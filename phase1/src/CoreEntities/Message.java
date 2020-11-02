package CoreEntities;

import java.util.UUID;
import java.lang.String;
import java.time.LocalTime;
import java.io.Serializable;

public class Message implements Serializable {
    private UUID sender;
    private UUID recipient;
    private String body;
    private LocalTime timeSent;
}
