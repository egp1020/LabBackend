package culturemedia.service.impl;
import culturemedia.exception.VideoNotFoundException;
import culturemedia.exception.DurationNotValidException;
import culturemedia.model.Video;
import culturemedia.model.View;
import culturemedia.service.CultureMediaService;
import culturemedia.repository.VideoRepository;
import culturemedia.repository.ViewRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CultureMediaServiceImpl implements CultureMediaService {
    final private VideoRepository videoRepository;
    final private ViewRepository viewRepository;

    public CultureMediaServiceImpl(VideoRepository videoRepository, ViewRepository viewRepository) {
        this.videoRepository = videoRepository;
        this.viewRepository = viewRepository;
    }

    @Override
    public List<Video> findAll() throws VideoNotFoundException {
        List<Video> videos = videoRepository.findAll();
        if (videos.isEmpty()) {
            throw new VideoNotFoundException();
        }
        return videos;
    }

    @Override
    public List<Video> find(String title) throws VideoNotFoundException {
        List<Video> videos = videoRepository.find(title);
        if (videos.isEmpty()) {
            throw new VideoNotFoundException();
        }
        return videos;
    }

    @Override
    public List<Video> find(Double fromDuration, Double toDuration) throws VideoNotFoundException {
        List<Video> videos = videoRepository.find(fromDuration, toDuration);
        if (videos.isEmpty()) {
            throw new VideoNotFoundException();
        }
        return videos;
    }

    public Video add(Video video) throws DurationNotValidException {
        validateVideoDuration(video);
        return this.videoRepository.save(video);
    }

    public View add(View view) {
        return this.viewRepository.save(view);
    }

    private static void validateVideoDuration(Video video) throws DurationNotValidException {
        if (video.duration() <= 0) {
            throw new DurationNotValidException(video.title(), video.duration());
        }
    }
}
