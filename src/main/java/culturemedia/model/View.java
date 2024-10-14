package culturemedia.model;

import java.time.LocalDateTime;

public record View (
        String userFullName,
        LocalDateTime date,
        Integer age,
        Video video){
}
