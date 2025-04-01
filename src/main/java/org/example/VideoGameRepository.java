package org.example;

import org.example.dto.Genre;
import org.example.dto.Nomination;
import org.example.dto.VideoGame;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByKey;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.example.dto.Genre.HACK_AND_SLASH;

public class VideoGameRepository {

    private static final String EMPTY_TITLE = "";
    private final List<VideoGame> videoGames;

    private static final Predicate<Nomination> NOMINATION_WON_PREDICATE = Nomination::won;

    private static final BiPredicate<Nomination, String> NOMINATION_WON_FROM_AWARDEE_BiPREDICATE =
            (nomination, awarder) -> nomination.won() && awarder.equals(nomination.awards());

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

    public List<String> getAllMultiplayerTitles() {
        return videoGames.stream()
                .filter(VideoGame::multiplayer)
                .map(VideoGame::title)
                .collect(Collectors.toList());
    }

    public String getGameWithTitle(final String title) {
        return videoGames.stream()
                .map(VideoGame::title)
                .filter(title::equals)
                .findAny()
                .orElse(EMPTY_TITLE);
    }

    public List<String> getGameTitleForHackSlashGenre() {
        return videoGames.stream()
                .filter(game -> game.genres().contains(HACK_AND_SLASH))
                .map(VideoGame::title)
                .collect(Collectors.toList());
    }


    public List<String> getGameTitleForGenre(final Genre genre) {
        return videoGames.stream()
                .filter(game -> game.genres().contains(genre))
                .map(VideoGame::title)
                .collect(Collectors.toList());
    }

    public List<String> getGameTitleFoAnyGenres(final Set<Genre> genres) {
        return videoGames.stream()
                .filter(game -> !Collections.disjoint(game.genres(), genres))
                .map(VideoGame::title)
                .collect(Collectors.toList());
    }

    public List<String> getGameTitleFoAllGenres(final Set<Genre> genres) {
        return videoGames.stream()
                .filter(game -> game.genres().containsAll(genres))
                .map(VideoGame::title)
                .collect(Collectors.toList());
    }

    public Genre mostCommonGenre() {

        List<Genre> allGenres = new ArrayList<>();
        videoGames.stream()
                .map(VideoGame::genres)
                .forEach(allGenres::addAll);

        return allGenres.stream()
                .filter(Objects::nonNull)
                .collect(groupingBy(Function.identity(), counting()))
                .entrySet()
                .stream()
                .max(comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

    }

    public List<String> fetchTitlesPlayableOnPlatform(final String title) {
        return videoGames.stream()
                .filter(game -> game.platforms().contains(title))
                .map(VideoGame::title)
                .collect(Collectors.toList());
    }

    public List<String> fetchTitlesReleasedInYear(final int year) {
        return videoGames.stream()
                .filter(game -> game.releaseDate().getYear() == year)
                .map(VideoGame::title)
                .collect(Collectors.toList());
    }

    public List<String> fetchTitlesReleasedBeforeOrAfterYear(final int beforeYear, final int afterYear) {
        return videoGames.stream()
                .filter(game -> game.releaseDate().getYear() < beforeYear ||
                        game.releaseDate().getYear() > afterYear)
                .map(VideoGame::title)
                .collect(Collectors.toList());
    }

    public double getAveragePlayingTime() {
        return videoGames.stream()
                .collect(Collectors.averagingDouble(VideoGame::estimatedHours));
    }


    public String getTitleWithLeasHours() {
        return videoGames.stream()
                .reduce((game1, game2) -> game1.estimatedHours() <= game2.estimatedHours() ? game1 : game2)
                .map(VideoGame::title)
                .orElse("");
    }

    public String getTitleWithLeasHoursBySorting() {
        return videoGames.stream()
                .sorted(Comparator.comparingInt(VideoGame::estimatedHours))
                .map(VideoGame::title)
                .toList()
                .get(0);
    }

    public String mostAwardedTitle() {
        return videoGames.stream()
                .collect(Collectors.toMap(VideoGame::title, game -> game.nominations().stream()
                        .filter(NOMINATION_WON_PREDICATE)
                        .count()
                ))
                .entrySet()
                .stream()
                .max(comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
    }

    public String mostAwardedTitleByAwarder(final String awarder) {
        return videoGames.stream()
                .collect(Collectors.toMap(VideoGame::title, game -> game.nominations().stream()
                        .filter(nomination -> nomination.won() && awarder.equals(nomination.awards()))
                        .toList()
                        .size()
                ))
                .entrySet()
                .stream()
                .max(comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
    }

    public String getOldestMultiplayerGameToWinAward() {
        return videoGames.stream()
                .filter(VideoGame::multiplayer)
                .filter(game -> game.nominations().stream().anyMatch(NOMINATION_WON_PREDICATE))
                .reduce((game1, game2) -> game1.releaseDate().isBefore(game2.releaseDate()) ? game1 : game2)
                .map(VideoGame::title)
                .orElse("");

    }

    public List<String> topFiveNominatedTitles() {
        return videoGames.stream()
                .collect(Collectors.toMap(game -> game.nominations().stream()
                        .toList()
                        .size(), VideoGame::title, (nominations1, nominations2) -> nominations1, TreeMap::new))
                .entrySet()
                .stream()
                .limit(5)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public List<String> topKNominatedTitles(final int topK) {
        return videoGames.stream()
                .collect(Collectors.toMap(game -> game.nominations().stream()
                        .toList()
                        .size(), VideoGame::title, (nominations1, nominations2) -> nominations1, TreeMap::new))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, String>comparingByKey().reversed())
                .limit(topK)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public String leastCommonPlatform() {
        List<String> allPlatforms = new ArrayList<>();

        videoGames.stream()
                .map(VideoGame::platforms)
                .forEach(allPlatforms::addAll);

        return allPlatforms.stream()
                .collect(groupingBy(Function.identity(), counting()))
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
    }

    public List<String> lessKCommonPlatform(final int leastKCommon) {
        List<String> allPlatforms = new ArrayList<>();

        videoGames.stream()
                .map(VideoGame::platforms)
                .forEach(allPlatforms::addAll);

        return allPlatforms.stream()
                .collect(groupingBy(Function.identity(), TreeMap::new, counting()))
                .entrySet()
                .stream()
                .limit(leastKCommon)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }
}
