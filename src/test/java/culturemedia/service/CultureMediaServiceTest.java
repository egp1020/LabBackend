package culturemedia.service;

import java.time.LocalDateTime;
import java.util.List;

import culturemedia.exception.DurationNotValidException;
import culturemedia.exception.VideoNotFoundException;


import culturemedia.model.Video;
import culturemedia.model.View;

import culturemedia.repository.VideoRepository;
import culturemedia.repository.ViewRepository;

import culturemedia.service.impl.CultureMediaServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CultureMediaServiceTest {
    @Mock
    private VideoRepository videoRepository;

    @Mock
    private ViewRepository viewRepository;

    @InjectMocks
    private CultureMediaServiceImpl culturemediaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void when_FindAll_all_videos_should_be_returned_successfully() throws VideoNotFoundException {
        mockFindAll(List.of(
                        new Video("01", "Título 1", "----", 3.0),
                        new Video("02", "Título 2", "----", 5.5)
        ));
        List<Video> videos = culturemediaService.findAll();
        assertFalse(videos.isEmpty());
    }

    @Test
    void when_FindAll_does_not_find_any_video_an_VideoNotFoundException_should_be_throw_successfully() {
        mockFindAll(List.of());
        assertThrows(VideoNotFoundException.class, () -> culturemediaService.findAll());
    }

    @Test
    void when_FindByTitle_with_existing_title_videos_should_be_returned_successfully() throws VideoNotFoundException {
        mockFindByTitle("Título", List.of(
                new Video("01", "Título 1", "----", 4.5)
        ));
        List<Video> videos = culturemediaService.find("Título");
        assertTrue(videos.stream().allMatch(video -> video.title().contains("Título")));
    }

    @Test
    void when_FindByTitle_with_non_existing_title_should_throw_VideoNotFoundException() {
        mockFindByTitle("NonExistentTitle", List.of());
        assertThrows(VideoNotFoundException.class, () -> culturemediaService.find("NonExistentTitle"));
    }

    @Test
    void when_FindByDuration_with_valid_range_videos_should_be_returned_successfully() throws VideoNotFoundException {
        mockFindByDuration(4.0, 5.0, List.of(
                new Video("01", "Título 1", "----", 4.5),
                new Video("02", "Título 2", "----", 4.7)
        ));
        List<Video> videos = culturemediaService.find(4.0, 5.0);
        assertTrue(videos.stream().allMatch(video -> video.duration() >= 4.0 && video.duration() <= 5.0));
    }

    @Test
    void when_FindByDuration_with_invalid_range_should_throw_VideoNotFoundException() {
        mockFindByDuration(10.0, 15.0, List.of());
        assertThrows(VideoNotFoundException.class, () -> culturemediaService.find(10.0, 15.0));
    }

    @Test
    void when_add_video_with_valid_duration_should_save_successfully() throws DurationNotValidException {
        Video video = new Video("07", "Test Video", "----", 3.0);
        mockSaveVideo(video, video);

        Video savedVideo = culturemediaService.add(video);
        assertEquals(video.duration(), savedVideo.duration());
    }

    @Test
    void when_add_view_should_save_successfully() {
        View view = new View("John Doe", LocalDateTime.now(), 25, new Video("01", "Test Video", "----", 3.0));
        mockSaveView(view, view);

        View savedView = culturemediaService.add(view);
        assertEquals(view.userFullName(), savedView.userFullName());
    }

    private void mockFindAll(List<Video> returnVideos) {
        when(videoRepository.findAll()).thenReturn(returnVideos);
    }

    private void mockFindByTitle(String title, List<Video> returnVideos) {
        when(videoRepository.find(title)).thenReturn(returnVideos);
    }

    private void mockFindByDuration(double minDuration, double maxDuration, List<Video> returnVideos){
        when(videoRepository.find(minDuration, maxDuration)).thenReturn(returnVideos);
    }

    private void mockSaveVideo(Video video, Video savedVideo) {
        when(videoRepository.save(savedVideo)).thenReturn(video);
    }

    private void mockSaveView(View view, View savedView) {
        when(viewRepository.save(savedView)).thenReturn(view);
    }

}
