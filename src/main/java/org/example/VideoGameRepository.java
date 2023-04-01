package org.example;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

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

    public Optional<VideoGame> getByTitle(String title) {
        Predicate<VideoGame> titlePredicate = videoGame -> title.equalsIgnoreCase(videoGame.title());

        return CollectionUtils.emptyIfNull(this.videoGames).stream()
                .filter(titlePredicate)
                .findFirst();
    }

    public List<VideoGame> getByDeveloper(final String developer) {
        Predicate<VideoGame> samePublisher = videoGame -> developer.equalsIgnoreCase(videoGame.developer());

        return CollectionUtils.emptyIfNull(this.videoGames).stream()
                .filter(samePublisher)
                .collect(Collectors.toList());
    }

    public List<VideoGame> getByGenre(final Genre genre) {
        Predicate<VideoGame> sameGenre = videoGame -> CollectionUtils.emptyIfNull(videoGame.genres()).stream()
                .anyMatch(genre::equals);

        return CollectionUtils.emptyIfNull(this.videoGames).stream()
                .filter(sameGenre)
                .collect(Collectors.toList());
    }

    public Optional<Map.Entry<Genre, Long>> getFavouriteGenre() {
        return CollectionUtils.emptyIfNull(this.videoGames).stream()
                .map(VideoGame::genres)
                .flatMap(Set::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());
    }

    public List<VideoGame> getByPlatform(final String platform) {
        Predicate<VideoGame> samePlatform = videoGame -> CollectionUtils.emptyIfNull(videoGame.platforms()).stream()
                .anyMatch(platform::equalsIgnoreCase);

        return CollectionUtils.emptyIfNull(this.videoGames).stream()
                .filter(samePlatform)
                .collect(Collectors.toList());
    }

    public List<VideoGame> getByReleaseYear(final int year) {
        Predicate<VideoGame> yearPredicate = videoGame -> videoGame.releaseDate().getYear() == year;

        return CollectionUtils.emptyIfNull(this.videoGames).stream()
                .filter(yearPredicate)
                .collect(Collectors.toList());
    }

    public List<VideoGame> getReleasedBeforeYear(final int year) {
        Predicate<VideoGame> yearPredicate = videoGame -> videoGame.releaseDate().getYear() <= year;

        return CollectionUtils.emptyIfNull(this.videoGames).stream()
                .filter(yearPredicate)
                .collect(Collectors.toList());
    }

    public Optional<Map.Entry<String, Long>> getFavouritePlatform() {
        return CollectionUtils.emptyIfNull(this.videoGames).stream()
                .map(VideoGame::platforms)
                .flatMap(Set::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());
    }

    public double getAveragePlayingHours() {
        return CollectionUtils.emptyIfNull(this.videoGames).stream()
                .map(VideoGame::estimatedHours)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0d);
    }

    public Optional<VideoGame> getShortestGame() {
        return CollectionUtils.emptyIfNull(this.videoGames).stream()
                .min(Comparator.comparing(VideoGame::estimatedHours));
    }

    public Optional<Map.Entry<String, Integer>> getMostNominatedGame() {
        Predicate<VideoGame> hasNominations = vg -> CollectionUtils.isNotEmpty(vg.nominations());
        ToIntFunction<VideoGame> nominationsCount = vg -> vg.nominations().size();

        return CollectionUtils.emptyIfNull(this.videoGames).stream()
                .filter(hasNominations)
                .collect(Collectors.groupingBy(VideoGame::title, Collectors.summingInt(nominationsCount)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());
    }

    public Optional<Map.Entry<String, Long>> getMostAwardedGame() {
        Predicate<VideoGame> awarded = vg -> CollectionUtils.emptyIfNull(vg.nominations()).stream()
                .anyMatch(Nomination::won);
        ToLongFunction<VideoGame> wins = vg -> vg.nominations().stream()
                .filter(Nomination::won)
                .count();

        return CollectionUtils.emptyIfNull(this.videoGames).stream()
                .filter(awarded)
                .collect(Collectors.groupingBy(VideoGame::title, Collectors.summingLong(wins)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());
    }

    public Optional<Map.Entry<String, Long>> getMostAwardedGameByAwardLabel(final String awardLabel) {
        Predicate<VideoGame> awarded = vg -> CollectionUtils.emptyIfNull(vg.nominations()).stream()
                .anyMatch(Nomination::won);
        Predicate<Nomination> awardedByLabel = nomination -> awardLabel.equalsIgnoreCase(nomination.awards());
        ToLongFunction<VideoGame> winsByAwardLabel = vg -> vg.nominations().stream()
                .filter(awardedByLabel)
                .filter(Nomination::won)
                .count();

        return CollectionUtils.emptyIfNull(this.videoGames).stream()
                .filter(awarded)
                .collect(Collectors.groupingBy(VideoGame::title, Collectors.summingLong(winsByAwardLabel)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());
    }

    public List<VideoGame> getMultiplayerGames() {
        return CollectionUtils.emptyIfNull(this.videoGames).stream()
                .filter(VideoGame::multiplayer)
                .collect(Collectors.toList());
    }
}
