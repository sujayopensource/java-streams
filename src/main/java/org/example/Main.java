package org.example;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.example.utils.JsonHelper;
import org.example.utils.TextUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
public class Main {

    private static VideoGameRepository repository;

    public static void main(String[] args) {

        repository = new VideoGameRepository();
        repository.add(JsonHelper.readJsonArrayFile("src/main/resources/videogames.json", VideoGame.class));

        printAllVideoGames();
        printVideoGameByTitle("Final Fantasy VII");
        printVideoGamesByDeveloper("Square Enix");
        printVideoGamesByGenre(Genre.ADVENTURE);
        printFavouriteGenre();
        printVideoGamesByPlatform("Xbox One");
        printFavouritePlatform();
        printVideoGamesReleasedInYear(2017);
        printVideoGamesReleasedBeforeYear(2000);
        printAveragePlayingHours();
        printShortestGame();
        printMostNominatedGame();
        printMostAwardedGame();
        printMostAwardedGameByAwardLabel("The game awards");
        printMultiplayerGames();
    }

    private static void printAllVideoGames() {
        StringBuilder stringBuilder = new StringBuilder("These are all the available video games:")
                .append(System.lineSeparator())
                .append(TextUtils.getListAsPrettyList(repository.getAll(), 0));
        log.info(stringBuilder.append(System.lineSeparator()));
    }

    private static void printVideoGameByTitle(final String title) {
        Optional<VideoGame> videoGame = repository.getByTitle(title);
        String introduction = (videoGame.isPresent() ? "Here's the video game " : "No video game found ") + "with title \"" + title + "\":";
        StringBuilder stringBuilder = new StringBuilder(introduction)
                .append(System.lineSeparator())
                .append(videoGame.isPresent() ? videoGame.get() : StringUtils.EMPTY);
        log.info(stringBuilder.append(System.lineSeparator()));
    }

    private static void printVideoGamesByDeveloper(final String developer) {
        List<String> result = repository.getByDeveloper(developer).stream()
                .map(VideoGame::title)
                .collect(Collectors.toList());

        StringBuilder stringBuilder = new StringBuilder("These are all the video games developed by \"" + developer + "\":")
                .append(System.lineSeparator())
                .append(TextUtils.getListAsPrettyString(result));
        log.info(stringBuilder.append(System.lineSeparator()));
    }

    private static void printVideoGamesByGenre(final Genre genre) {
        List<String> result = repository.getByGenre(genre).stream()
                .map(VideoGame::title)
                .collect(Collectors.toList());

        StringBuilder stringBuilder = new StringBuilder("These are all the \"" + genre + "\" genre video games:")
                .append(System.lineSeparator())
                .append(TextUtils.getListAsPrettyString(result));
        log.info(stringBuilder.append(System.lineSeparator()));
    }

    private static void printFavouriteGenre() {
        Optional<Map.Entry<Genre, Long>> result = repository.getFavouriteGenre();
        if (result.isPresent()) {
            Map.Entry<Genre, Long> favouriteGenre = result.get();
            log.info("The most common genre is \"{}\" with {} game(s).{}",
                    favouriteGenre.getKey(),
                    favouriteGenre.getValue(),
                    System.lineSeparator());
        } else {
            log.info("No game found.{}", System.lineSeparator());
        }
    }

    private static void printVideoGamesByPlatform(final String platform) {
        List<String> result = repository.getByPlatform(platform).stream()
                .map(VideoGame::title)
                .collect(Collectors.toList());

        StringBuilder stringBuilder = new StringBuilder("These are all the games available in \"" + platform + "\" platform:")
                .append(System.lineSeparator())
                .append(TextUtils.getListAsPrettyString(result));
        log.info(stringBuilder.append(System.lineSeparator()));
    }

    private static void printFavouritePlatform() {
        Optional<Map.Entry<String, Long>> result = repository.getFavouritePlatform();
        if (result.isPresent()) {
            Map.Entry<String, Long> mostUsedPlatform = result.get();
            log.info("The most common platform is \"{}\" with {} game(s).{}",
                    mostUsedPlatform.getKey(),
                    mostUsedPlatform.getValue(),
                    System.lineSeparator());
        } else {
            log.info("No used platform found.{}", System.lineSeparator());
        }
    }

    private static void printVideoGamesReleasedInYear(final int year) {
        List<String> result = repository.getByReleaseYear(year).stream()
                .map(VideoGame::title)
                .collect(Collectors.toList());

        StringBuilder stringBuilder = new StringBuilder("These are all the video games released in \"" + year + "\":")
                .append(System.lineSeparator())
                .append(TextUtils.getListAsPrettyString(result));
        log.info(stringBuilder.append(System.lineSeparator()));
    }

    private static void printVideoGamesReleasedBeforeYear(final int year) {
        List<String> result = repository.getReleasedBeforeYear(year).stream()
                .map(VideoGame::title)
                .collect(Collectors.toList());

        StringBuilder stringBuilder = new StringBuilder("These are all the video games released before \"" + (year + 1) + "\":")
                .append(System.lineSeparator())
                .append(TextUtils.getListAsPrettyString(result));
        log.info(stringBuilder.append(System.lineSeparator()));
    }

    private static void printAveragePlayingHours() {
        log.info("The average estimated playing time per game is {} hours.{}",
                String.format("%.2f", repository.getAveragePlayingHours()),
                System.lineSeparator());
    }

    private static void printShortestGame() {
        Optional<VideoGame> result = repository.getShortestGame();
        if (result.isPresent()) {
            VideoGame shortestGame = result.get();
            log.info("\"{}\" is the shortest game based on the estimated playing hours: {} hrs.{}",
                    shortestGame.title(),
                    shortestGame.estimatedHours(),
                    System.lineSeparator());
        } else {
            log.info("No game found.{}", System.lineSeparator());
        }
    }

    private static void printMostNominatedGame() {
        Optional<Map.Entry<String, Integer>> result = repository.getMostNominatedGame();
        if (result.isPresent()) {
            Map.Entry<String, Integer> mostNominatedGame = result.get();
            log.info("The most nominated game is \"{}\" with {} nomination(s).{}",
                    mostNominatedGame.getKey(),
                    mostNominatedGame.getValue(),
                    System.lineSeparator());
        } else {
            log.info("No nominated game found.{}", System.lineSeparator());
        }
    }

    private static void printMostAwardedGame() {
        Optional<Map.Entry<String, Long>> result = repository.getMostAwardedGame();
        if (result.isPresent()) {
            Map.Entry<String, Long> mostAwardedGame = result.get();
            log.info("The most awarded game is \"{}\" with {} win(s).{}",
                    mostAwardedGame.getKey(),
                    mostAwardedGame.getValue(),
                    System.lineSeparator());
        } else {
            log.info("No awarded game found.{}", System.lineSeparator());
        }
    }

    private static void printMostAwardedGameByAwardLabel(final String awardLabel) {
        Optional<Map.Entry<String, Long>> result = repository.getMostAwardedGameByAwardLabel(awardLabel);
        if (result.isPresent()) {
            Map.Entry<String, Long> mostAwardedGame = result.get();
            log.info("The most awarded game by \"{}\" is \"{}\" with {} win(s).{}",
                    awardLabel,
                    mostAwardedGame.getKey(),
                    mostAwardedGame.getValue(),
                    System.lineSeparator());
        } else {
            log.info("No  game awarded by {} found.{}", awardLabel, System.lineSeparator());
        }
    }

    private static void printMultiplayerGames() {
        List<String> result = repository.getMultiplayerGames().stream()
                .map(VideoGame::title)
                .collect(Collectors.toList());

        StringBuilder stringBuilder = new StringBuilder("These are all the multi player games:")
                .append(System.lineSeparator())
                .append(TextUtils.getListAsPrettyString(result));
        log.info(stringBuilder.append(System.lineSeparator()));
    }
}