package culturemedia.exception;


public class VideoNotFoundException extends CultureMediaException{

    public VideoNotFoundException() {
        super("Video not found");
    }

    public VideoNotFoundException(String title) {
        super("Video not found: " + title);
    }
}
