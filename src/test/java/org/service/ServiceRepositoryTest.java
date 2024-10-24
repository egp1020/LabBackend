package org.service;

import java.time.LocalDateTime;
import java.util.List;

import culturemedia.exception.DurationNotValidException;
import culturemedia.exception.VideoNotFoundException;


import culturemedia.model.Video;
import culturemedia.repository.VideoRepository;
import culturemedia.repository.impl.VideoRepositoryImpl;

import culturemedia.model.View;
import culturemedia.repository.ViewRepository;
import culturemedia.repository.impl.ViewRepositoryImpl;

import culturemedia.service.ServiceRepository;
import culturemedia.service.impl.ServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @Test
    void when_FindByTitle_with_existing_title_videos_should_be_returned_successfully() throws VideoNotFoundException, DurationNotValidException {
        loadTestVideos();

        List<Video> videos = serviceRepository.find("Título");

        assertNotNull(videos);
        assertFalse(videos.isEmpty());
        assertEquals(4, videos.size());
        assertTrue(videos.stream().allMatch(video -> video.title().contains("Título")));
    }

    @Test
    void when_FindByTitle_with_non_existing_title_should_throw_VideoNotFoundException() throws DurationNotValidException {
        loadTestVideos();

        assertThrows(VideoNotFoundException.class, () -> serviceRepository.find("NonExistentTitle"));
    }

    @Test
    void when_FindByDuration_with_valid_range_videos_should_be_returned_successfully() throws VideoNotFoundException, DurationNotValidException {
        loadTestVideos();

        List<Video> videos = serviceRepository.find(4.0, 5.0);

        assertNotNull(videos);
        assertFalse(videos.isEmpty());
        assertTrue(videos.stream().allMatch(video -> video.duration() >= 4.0 && video.duration() <= 5.0));
    }

    @Test
    void when_FindByDuration_with_invalid_range_should_throw_VideoNotFoundException() throws DurationNotValidException {
        loadTestVideos();

        assertThrows(VideoNotFoundException.class, () -> serviceRepository.find(10.0, 15.0));
    }

    @Test
    void when_add_video_with_valid_duration_should_save_successfully() throws DurationNotValidException {
        Video video = new Video("07", "Test Video", "----", 3.0);

        Video savedVideo = serviceRepository.add(video);

        assertNotNull(savedVideo);
        assertEquals(video.title(), savedVideo.title());
        assertEquals(video.duration(), savedVideo.duration());
    }

    @Test
    void when_add_view_should_save_successfully() throws DurationNotValidException {
        Video video = new Video("01", "Test Video", "----", 3.0);
        serviceRepository.add(video);

        View view = new View(
                "John Doe",
                LocalDateTime.now(),
                25,
                video
        );

        View savedView = serviceRepository.add(view);
        assertNotNull(savedView);
        assertEquals(view.userFullName(), savedView.userFullName());
        assertEquals(view.date(), savedView.date());
        assertEquals(view.age(), savedView.age());
        assertEquals(view.video().title(), savedView.video().title());
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
