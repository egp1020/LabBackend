package culturemedia.repository.impl;

import culturemedia.model.Video;
import culturemedia.model.View;
import culturemedia.repository.ServiceRepository;
import culturemedia.repository.VideoRepository;
import culturemedia.repository.ViewRepository;
import culturemedia.exception.DurationNotValidException;

import java.util.List;

public class ServiceImpl implements ServiceRepository {
    final private VideoRepository videoRepository;
    final private ViewRepository viewRepository;

    public ServiceImpl(VideoRepository videoRepository, ViewRepository viewRepository) {
        this.videoRepository = videoRepository;
        this.viewRepository = viewRepository;
    }

    @Override
    public List<Video> findAll() {
        return this.videoRepository.findAll();
    }

    @Override
    public Video add(Video video) {
        if (video.duration() <= 0) {
            try {
                throw new DurationNotValidException(video.title(), video.duration());
            } catch (DurationNotValidException e) {
                throw new RuntimeException(e);
            }
        }
        return this.videoRepository.save(video);
    }

    @Override
    public View add(View view) {
        return this.viewRepository.save(view);
    }
}
