package org.example;

import lombok.extern.log4j.Log4j2;
import org.example.utils.JsonHelper;
import org.example.utils.LoggerHelper;

@Log4j2
public class Main {

    private static VideoGameRepository repository;

    public static void main(final String[] args) {

        repository = new VideoGameRepository();
        repository.add(JsonHelper.readJsonArrayFile("src/main/resources/videogames.json", VideoGame.class));

        printAllVideoGames();
        printMultiplayerGames();
        printVideoGameByTitle("Final Fantasy VII");
        printVideoGamesByGenre(Genre.HACK_AND_SLASH);
        printVideoGamesByGenreAndDeveloper(Genre.ROLE_PLAYING, "Square Enix");
        printFavouriteGenre();
        printVideoGamesByPlatform("Xbox One");
        printVideoGamesReleasedInYear(2017);
        printVideoGamesReleasedBeforeOrAfter(2000, 2018);
        printAveragePlayingTime();
        printShortestGame();
        printMostAwardedGame();
        printMostAwardedGameByAwardLabel("The game awards");
        printOldestMultiplayerGameToWinAnAward();
        printMostNominatedGames(5);
        printLessCommonPlatforms();

        LoggerHelper.shutDownLogs(Main.class);
    }

    private static void printAllVideoGames() {
    }

    private static void printVideoGameByTitle(final String title) {
    }

    private static void printVideoGamesByGenreAndDeveloper(final Genre genre, final String developer) {
    }

    private static void printVideoGamesByGenre(final Genre genre) {
    }

    private static void printFavouriteGenre() {
    }

    private static void printVideoGamesByPlatform(final String platform) {
    }

    private static void printLessCommonPlatforms() {
    }

    private static void printVideoGamesReleasedInYear(final int year) {
    }

    private static void printVideoGamesReleasedBeforeOrAfter(final int beforeYear, final int afterYear) {
    }

    private static void printAveragePlayingTime() {
    }

    private static void printShortestGame() {
    }

    private static void printMostNominatedGames(final int limit) {
    }

    private static void printMostAwardedGame() {
    }

    private static void printMostAwardedGameByAwardLabel(final String awardLabel) {
    }

    private static void printOldestMultiplayerGameToWinAnAward() {
    }

    private static void printMultiplayerGames() {
    }
}