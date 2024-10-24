package org.service;

import java.util.List;

import culturemedia.exception.DurationNotValidException;
import culturemedia.exception.VideoNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import culturemedia.model.Video;
import culturemedia.repository.VideoRepository;
import culturemedia.repository.impl.VideoRepositoryImpl;

import culturemedia.repository.ViewRepository;
import culturemedia.repository.impl.ViewRepositoryImpl;

import culturemedia.service.ServiceRepository;
import culturemedia.service.impl.ServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class ServiceRepositoryTest {
    private ServiceRepository serviceRepository;

    @BeforeEach
    void setUp() {
        VideoRepository videoRepository = new VideoRepositoryImpl();
        ViewRepository viewRepository = new ViewRepositoryImpl();
        serviceRepository = new ServiceImpl(videoRepository, viewRepository);
    }

    @Test
    void when_FindAll_all_videos_should_be_returned_successfully() throws VideoNotFoundException, DurationNotValidException {

        loadTestVideos();

        List<Video> videos = serviceRepository.findAll();

        assertNotNull(videos);
        assertFalse(videos.isEmpty());
        assertEquals(6, videos.size());
    }

    @Test
    void when_FindAll_does_not_find_any_video_an_VideoNotFoundException_should_be_throw_successfully() {
        assertThrows(VideoNotFoundException.class, () -> serviceRepository.findAll());
    }

    private void loadTestVideos() throws DurationNotValidException {
        List<Video> videos = List.of(
                new Video("01", "Título 1", "----", 4.5),
                new Video("02", "Título 2", "----", 5.5),
                new Video("03", "Título 3", "----", 4.4),
                new Video("04", "Título 4", "----", 3.5),
                new Video("05", "Clic 5", "----", 5.7),
                new Video("06", "Clic 6", "----", 5.1)
        );

        for (Video video : videos) {
            serviceRepository.add(video);
        }
    }
}
