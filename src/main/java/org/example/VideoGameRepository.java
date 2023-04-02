package org.example;

import org.example.dto.VideoGame;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VideoGameRepository {

    private final List<VideoGame> videoGames;

    public VideoGameRepository() {
        this.videoGames = new ArrayList<>();
    }

    public boolean add(final VideoGame... videoGames) {
        return this.add(List.of(videoGames));
    }

    public boolean add(final List<VideoGame> videoGames) {
        return this.videoGames.addAll(videoGames);
    }

    public List<VideoGame> getAll() {
        return this.videoGames;
    }
}
