package culturemedia.controller;
import java.util.*;

import culturemedia.exception.DurationNotValidException;
import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.service.CultureMediaService;

public class CultureMediaController {

    private final CultureMediaService cultureMediaService;

    public CultureMediaController(CultureMediaService cultureMediaService) {
        this.cultureMediaService = cultureMediaService;
    }

    public List<Video> findAllVideos() throws VideoNotFoundException {
        return cultureMediaService.findAll();
    }

    public Video addVideo(Video video) throws DurationNotValidException {
        return this.cultureMediaService.add(video);
    }
}
