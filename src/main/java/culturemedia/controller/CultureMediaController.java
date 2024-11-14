package culturemedia.controller;
import java.util.*;

import culturemedia.exception.DurationNotValidException;
import culturemedia.exception.VideoNotFoundException;
import culturemedia.model.Video;
import culturemedia.service.CultureMediaService;
import org.springframework.web.bind.annotation.*;

@RestController
public class CultureMediaController {

    private final CultureMediaService cultureMediaService;

    public CultureMediaController(CultureMediaService cultureMediaService) {
        this.cultureMediaService = cultureMediaService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/videos")
    public List<Video> findAllVideos() throws VideoNotFoundException {
        return cultureMediaService.findAll();
    }

    @PostMapping("/videos")
    public Video addVideo(@RequestBody Video video) throws DurationNotValidException {
        return this.cultureMediaService.add(video);
    }
}
