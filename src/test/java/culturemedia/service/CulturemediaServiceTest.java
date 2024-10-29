package culturemedia.service;

import java.time.LocalDateTime;
import java.util.List;

import culturemedia.exception.DurationNotValidException;
import culturemedia.exception.VideoNotFoundException;


import culturemedia.model.Video;
import culturemedia.repository.impl.VideoRepositoryImpl;

import culturemedia.model.View;
import culturemedia.repository.impl.ViewRepositoryImpl;

import culturemedia.service.impl.CulturemediaServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CulturemediaServiceTest {
    private CulturemediaService culturemediaService;

    @BeforeEach
    void setUp() {
        culturemediaService = new CulturemediaServiceImpl(new VideoRepositoryImpl(), new ViewRepositoryImpl());
    }

    @Test
    void when_FindAll_all_videos_should_be_returned_successfully() throws VideoNotFoundException, DurationNotValidException {
        loadTestVideos();
        List<Video> videos = culturemediaService.findAll();
        assertFalse(videos.isEmpty());
    }

    @Test
    void when_FindAll_does_not_find_any_video_an_VideoNotFoundException_should_be_throw_successfully() {
        assertThrows(VideoNotFoundException.class, () -> culturemediaService.findAll());
    }

    @Test
    void when_FindByTitle_with_existing_title_videos_should_be_returned_successfully() throws VideoNotFoundException, DurationNotValidException {
        loadTestVideos();

        List<Video> videos = culturemediaService.find("Título");
        assertTrue(videos.stream().allMatch(video -> video.title().contains("Título")));
    }

    @Test
    void when_FindByTitle_with_non_existing_title_should_throw_VideoNotFoundException() throws DurationNotValidException {
        loadTestVideos();
        assertThrows(VideoNotFoundException.class, () -> culturemediaService.find("NonExistentTitle"));
    }

    @Test
    void when_FindByDuration_with_valid_range_videos_should_be_returned_successfully() throws VideoNotFoundException, DurationNotValidException {
        loadTestVideos();
        List<Video> videos = culturemediaService.find(4.0, 5.0);
        assertTrue(videos.stream().allMatch(video -> video.duration() >= 4.0 && video.duration() <= 5.0));
    }

    @Test
    void when_FindByDuration_with_invalid_range_should_throw_VideoNotFoundException() throws DurationNotValidException {
        loadTestVideos();
        assertThrows(VideoNotFoundException.class, () -> culturemediaService.find(10.0, 15.0));
    }

    @Test
    void when_add_video_with_valid_duration_should_save_successfully() throws DurationNotValidException {
        Video video = new Video("07", "Test Video", "----", 3.0);
        Video savedVideo = culturemediaService.add(video);
        assertEquals(video.duration(), savedVideo.duration());
    }

    @Test
    void when_add_view_should_save_successfully() throws DurationNotValidException {
        Video video = new Video("01", "Test Video", "----", 3.0);
        culturemediaService.add(video);

        View view = new View(
                "John Doe",
                LocalDateTime.now(),
                25,
                video
        );

        View savedView = culturemediaService.add(view);
        assertEquals(view.userFullName(), savedView.userFullName());
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
            culturemediaService.add(video);
        }
    }
}
