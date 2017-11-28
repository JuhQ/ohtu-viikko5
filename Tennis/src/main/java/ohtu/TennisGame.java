package ohtu;

import java.util.HashMap;

public class TennisGame {

    public static final int SCORE_LOVE = 0;
    public static final int SCORE_FIFTEEN = 1;
    public static final int SCORE_THIRTY = 2;
    public static final int SCORE_FORTY = 3;
    public static final int SCORE_WIN = 4;

    public static final int DIFF_ADVANTAGE_PLAYER_1 = 1;
    public static final int DIFF_ADVANTAGE_PLAYER_2 = -1;
    public static final int DIFF_WIN_PLAYER_1 = 2;

    private String player1;
    private String player2;
    private HashMap<String, Integer> players;

    public TennisGame(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
        initPlayers();
    }

    private void initPlayers() {
        this.players = new HashMap<>();
        players.put(player1, 0);
        players.put(player2, 0);
    }

    public void wonPoint(String playerName) {
        players.put(playerName, players.get(playerName) + 1);
    }

	private int getPlayerScore(String player) {
		return players.get(player);
	}

    private boolean gameIsTied() {
        return getPlayerScore(player1) == getPlayerScore(player2);
    }

    private String gameTiedSituation() {
        if (getPlayerScore(player1) < SCORE_WIN) {
            return buildScoreStringForPlayer(player1) + "-All";
        }

        return "Deuce";
    }

    private int getScoreDifference() {
        return getPlayerScore(player1) - getPlayerScore(player2);
    }

    private String getWinner() {
        return getScoreDifference() >= DIFF_WIN_PLAYER_1 ?
            player1 :
            player2;
    }

    private String getAdvantage() {
        int scoreDifference = getScoreDifference();
        if (scoreDifference == DIFF_ADVANTAGE_PLAYER_1) {
            return player1;
        }

        if (scoreDifference == DIFF_ADVANTAGE_PLAYER_2) {
            return player2;
        }

        return "";
    }

    private boolean gameIsOverFourPoints() {
        return getPlayerScore(player1) >= SCORE_WIN || getPlayerScore(player2) >= SCORE_WIN;
    }

    private String overFourPointsSituation() {
        int scoreDifference = getScoreDifference();
        if (scoreDifference == DIFF_ADVANTAGE_PLAYER_1 || scoreDifference == DIFF_ADVANTAGE_PLAYER_2) {
            return "Advantage " + getAdvantage();
        }

        return "Win for " + getWinner();
    }

    private String buildScoreStringForPlayer(String player) {
        switch(getPlayerScore(player)) {
            case SCORE_LOVE:
                return "Love";
            case SCORE_FIFTEEN:
                return "Fifteen";
            case SCORE_THIRTY:
                return "Thirty";
            case SCORE_FORTY:
                return "Forty";
        }

        return "";
    }

    private String buildScoreString() {
        return buildScoreStringForPlayer(player1) + "-" + buildScoreStringForPlayer(player2);
    }

    public String getScore() {
        if (gameIsTied()) {
            return gameTiedSituation();
        }

        if (gameIsOverFourPoints()) {
            return overFourPointsSituation();
        }

        return buildScoreString();
    }
}