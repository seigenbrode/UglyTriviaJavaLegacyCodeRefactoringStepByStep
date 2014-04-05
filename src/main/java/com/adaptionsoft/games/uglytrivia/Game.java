package com.adaptionsoft.games.uglytrivia;

import java.util.LinkedList;
import java.util.Random;

public class Game {
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    boolean notAWinner = true;

    private Random rand;
    private Rules rules;
    Players players;

    public Game(Random rand, Players players) {
        this.players = players;
        this.rules = new Rules();
        this.rand = rand;

        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast("Rock Question " + i);
        }
    }

    public void run() {
        do {
            runOneTurn();
        } while (notAWinner);
    }

    private void runOneTurn() {
        System.out.println(currentPlayer().name() + " is the current player");

        int roll = currentPlayerThrowDice();

        if (currentPlayer().inPenaltyBox()) {
            currentPlayer().tryToGetOutOfPenaltyBox(roll, this.rules);
        }

        if (!currentPlayer().inPenaltyBox()) {
            advancePlayerBy(roll);

            askQuestion();

            reactToAnswer();
        }

        players.next();
    }

    private void reactToAnswer() {
        if (answerWasWrong()) {
            reactToWrongAnswer();
        } else {
            reactToCorrectAnswer();
        }
    }

    private void reactToCorrectAnswer() {
        System.out.println("Answer was correct!!!!");
        currentPlayer().winGoldCoin();
        notAWinner = !currentPlayer().hasWonAccordingTo(rules);
    }

    private boolean answerWasWrong() {
        return rand.nextInt(9) == 7;
    }

    private Player currentPlayer() {
        return players.getCurrentPlayer();
    }

    private void advancePlayerBy(int places) {
        int place = advanceInBoard(places);

        currentPlayer().moveTo(place);

        System.out.println("The category is " + this.currentCategory());
    }

    private int advanceInBoard(int places) {
        int place = currentPlayer().place() + places;
        final int BOARDS_PLACES_NUMBER = 12;
        if (place > BOARDS_PLACES_NUMBER - 1) {
            place = place - BOARDS_PLACES_NUMBER;
        }
        return place;
    }

    private int currentPlayerThrowDice() {
        int roll = rand.nextInt(5) + 1;
        System.out.println("They have rolled a " + roll);
        return roll;
    }

    private void askQuestion() {
        if (currentCategory() == "Pop")
            System.out.println(popQuestions.removeFirst());
        if (currentCategory() == "Science")
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory() == "Sports")
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory() == "Rock")
            System.out.println(rockQuestions.removeFirst());
    }

    private String currentCategory() {
        if (currentPlayer().isAt(0))
            return "Pop";
        if (currentPlayer().isAt(4))
            return "Pop";
        if (currentPlayer().isAt(8))
            return "Pop";
        if (currentPlayer().isAt(1))
            return "Science";
        if (currentPlayer().isAt(5))
            return "Science";
        if (currentPlayer().isAt(9))
            return "Science";
        if (currentPlayer().isAt(2))
            return "Sports";
        if (currentPlayer().isAt(6))
            return "Sports";
        if (currentPlayer().isAt(10))
            return "Sports";
        return "Rock";
    }

    private void reactToWrongAnswer() {
        System.out.println("Question was incorrectly answered");
        currentPlayer().enterPenaltyBox();
        notAWinner = true;
    }
}
