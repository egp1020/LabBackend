package culturemedia.repository.impl;

import java.util.ArrayList;
import java.util.List;

import culturemedia.model.View;
import culturemedia.repository.ViewRepository;
import org.springframework.stereotype.Component;

@Component
public class ViewRepositoryImpl implements ViewRepository {

    private final List<View> views;

    public ViewRepositoryImpl() {
        this.views = new ArrayList<>();
    }

    @Override
    public View save(View view) {
        this.views.add( view );
        return view;
    }
}