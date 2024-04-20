package openings_tools;

import java.time.Instant;
import java.util.List;

public class OpeningsRecordEntry {
    private String title;
    private WinState winState;
    private Color color;
    private Instant date;
    private boolean openingCaused;
    private boolean endGameCaused;
    private boolean tacticCaused;
    private boolean blunderCaused;
    private boolean timeCaused;
    private boolean dnf;
    private int myRating;
    private int theirRating;


    /**
     * Modifies this in function of csv order, defined in this function
     * @param index current index in csv line
     * @param input the string value of cell
     */
    public void modifyForIndex(int index, String input) {
        switch (index) {
            case 0:
                this.title = input;
                break;
            case 1:
                if(input.trim().equals("-1")){
                    this.winState = WinState.iLost;
                    break;
                }
                if(input.trim().equals("0")){
                    this.winState = WinState.draw;
                    break;
                }
                if(input.trim().equals("1")){
                    this.winState = WinState.iWon;
                    break;
                }
                break;
            case 2:
                if(input.trim().equals("white")){
                    this.color = Color.white;
                    break;
                }
                if(input.trim().equals("black")){
                    this.color = Color.black;
                    break;
                }
                break;
            case 3:
                String[] dataValues = input.split("/");
                this.date = Instant.parse( dataValues[2]+"-" + dataValues[1]+"-" + dataValues[0] + "T00:00:00.00Z");
                break;
            case 4:
                this.openingCaused = input.trim().equals("1");
                break;
            case 5:
                this.endGameCaused = input.trim().equals("1");
                break;
            case 6:
                this.tacticCaused = input.trim().equals("1");
                break;
            case 7:
                this.blunderCaused = input.trim().equals("1");
                break;
            case 8:
                this.timeCaused = input.trim().equals("1");
                break;
            case 9:
                this.dnf = input.trim().equals("1");
                break;
            case 10:
                this.myRating = Integer.parseInt(input);
                break;
            case 11:
                this.theirRating = Integer.parseInt(input);;
                break;
        }
    }

    public String getTitle() {
        return title;
    }

    public WinState getWinState() {
        return winState;
    }

    public Instant getDate() {
        return date;
    }

    public boolean isOpeningCaused() {
        return openingCaused;
    }

    public boolean isEndGameCaused() {
        return endGameCaused;
    }

    public boolean isTacticCaused() {
        return tacticCaused;
    }

    public boolean isBlunderCaused() {
        return blunderCaused;
    }

    public boolean isTimeCaused() {
        return timeCaused;
    }

    public boolean isDnf() {
        return dnf;
    }

    public int getMyRating() {
        return myRating;
    }

    public int getTheirRating() {
        return theirRating;
    }


    public enum WinState{
        iWon,
        iLost,
        draw
    }

    private enum Color{
        white,
        black
    }
}
