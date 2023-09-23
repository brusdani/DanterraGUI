package cz.vse.danterragui.logika;

import java.util.Scanner;
/**
 * Class Riddle - Holds all the information about riddles in the game
 * @author Daniel Brus
 * @version 0.9, May 2023
 */

public class Riddle {

    private String riddle;
    private String answer;
    private Thing reward;
    private Thing hint;
    private Thing tool;

    /**
     * Constructor
     * @param riddle Text of the riddle
     * @param answer Answer for given riddle
     * @param reward Reward for completing given riddle
     * @param hint Item that includes hint to given riddle
     */

    public Riddle(String riddle, String answer, Thing reward, Thing hint) {
        this.riddle = riddle;
        this.answer = answer;
        this.reward = reward;
        this.hint = hint;
    }

    /**
     * Getter for riddle
     * @return String riddle
     */

    public String getRiddle() {
        return riddle;
    }

    /**
     * Getter for answer
     * @return String answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Getter for reward
     * @return Thing reward
     */
    public Thing getReward() {
        return reward;
    }
    /**
     * Getter for hint
     * @return Thing hint
     */
    public Thing getHint() {
        return hint;
    }

    //    public String userAnswer(){
//        Scanner scan = new Scanner(System.in);
//        System.out.println("Enter your answer ");
//        System.out.print("> ");
//        return scan.nextLine();
//    }
}

