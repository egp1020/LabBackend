package culturemedia.service.impl;
import java.util.List;

import culturemedia.exception.DurationNotValidException;
import culturemedia.model.Video;
import culturemedia.model.View;

public interface ServiceRepository {
    List<Video> findAll();
    Video add(Video video) throws DurationNotValidException;
    View add(View view);
}
