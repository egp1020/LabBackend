package culturemedia.exception;


public class DurationNotValidException extends CultureMediaException {
    public DurationNotValidException(String title, double duration) {
        super("Invalid duration for video: " + title + ", Duration: " + duration);
    }
}
