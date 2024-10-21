package culturemedia.exception;


import java.text.MessageFormat;

public class DurationNotValidException extends CultureMediaException {
    public DurationNotValidException(String title, double duration) {
        super(MessageFormat.format("Invalid duration for video: {}. Duration: {}", title, duration));
    }
}
