package openings_tools;

import csv_tools.JsonCsvReader;
import csv_tools.json_parsing.JsonDoubleLiteral;
import csv_tools.json_parsing.JsonIntegerLiteral;
import csv_tools.json_parsing.JsonMapLiteralContainer;
import csv_tools.json_parsing.JsonParseLiteral;

import java.util.List;

public class OpeningsComparator extends JsonCsvReader {

    private final String TOTAL_ENEMY_RATING_KEY = "TotalEnemyRating";
    private final String NUMBER_OF_GAMES_KEY = "Number of games";
    private final String WINS_KEY = "Wins";
    private final String LOSSES_KEY = "Losses";
    private final String TIMES_OPENING_CAUSED_KEY = "Times opening caused";
    private final String AVERAGE_OPPONENT_RATING_KEY = "Average opponent rating";

    @Override
    protected void readLine(List<String> line) {
        OpeningsRecordEntry entry = new OpeningsRecordEntry();
        for(int index = 0 ; index < line.size() ; index++){
            entry.modifyForIndex(index, line.get(index));
        }
        JsonMapLiteralContainer openingData = new JsonMapLiteralContainer();
        if(!super.currentJson.containsKey(entry.getTitle())){
            openingData.putInMap(NUMBER_OF_GAMES_KEY, new JsonIntegerLiteral(1));
            openingData.putInMap(WINS_KEY, new JsonIntegerLiteral((entry.getWinState() == OpeningsRecordEntry.WinState.iWon ? 1 : 0)));
            openingData.putInMap(LOSSES_KEY, new JsonIntegerLiteral((entry.getWinState() == OpeningsRecordEntry.WinState.iLost ? 1 : 0)));
            openingData.putInMap(TIMES_OPENING_CAUSED_KEY, new JsonIntegerLiteral((entry.isOpeningCaused() ? 1 : 0)));
            openingData.putInMap(AVERAGE_OPPONENT_RATING_KEY, new JsonDoubleLiteral(entry.getTheirRating()));
            openingData.putInMap(TOTAL_ENEMY_RATING_KEY, new JsonIntegerLiteral(entry.getTheirRating()).setDoPrint(false));
            super.currentJson.put(entry.getTitle(), openingData);
        }else{
            openingData = (JsonMapLiteralContainer) super.currentJson.get(entry.getTitle());
            openingData.putInMap(NUMBER_OF_GAMES_KEY, new JsonIntegerLiteral(1 + ((JsonIntegerLiteral)openingData.getInMap(NUMBER_OF_GAMES_KEY)).getValue()));
            if(entry.getWinState() == OpeningsRecordEntry.WinState.iWon){
                openingData.putInMap(WINS_KEY, new JsonIntegerLiteral(1 + ((JsonIntegerLiteral)openingData.getInMap(WINS_KEY)).getValue()));
            }
            else if(entry.getWinState() == OpeningsRecordEntry.WinState.iLost){
                openingData.putInMap(LOSSES_KEY, new JsonIntegerLiteral(1 + ((JsonIntegerLiteral)openingData.getInMap(LOSSES_KEY)).getValue()));
            }
            if(entry.isOpeningCaused()){
                openingData.putInMap(TIMES_OPENING_CAUSED_KEY, new JsonIntegerLiteral(1 + ((JsonIntegerLiteral)openingData.getInMap(TIMES_OPENING_CAUSED_KEY)).getValue()));
            }
            openingData.putInMap(TOTAL_ENEMY_RATING_KEY, new JsonIntegerLiteral(entry.getTheirRating() + ((JsonIntegerLiteral)openingData.getInMap(TOTAL_ENEMY_RATING_KEY)).getValue()).setDoPrint(false));
            openingData.putInMap(AVERAGE_OPPONENT_RATING_KEY, new JsonDoubleLiteral(((double)((JsonIntegerLiteral)openingData.getInMap(TOTAL_ENEMY_RATING_KEY)).getValue() ) / ((double)((JsonIntegerLiteral)openingData.getInMap(NUMBER_OF_GAMES_KEY)).getValue())));

        }


    }
}
