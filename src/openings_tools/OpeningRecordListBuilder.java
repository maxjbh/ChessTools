package openings_tools;

import java.util.ArrayList;
import java.util.List;

public class OpeningRecordListBuilder {
    private List<OpeningsRecordEntry> records = new ArrayList<>();

    public void buildRecords(List<List<String>> parsedCsv){
        records = new ArrayList<>();
        for(List<String> nextRow : parsedCsv){
            OpeningsRecordEntry nextEntry = new OpeningsRecordEntry();
            for(int index = 0 ; index < nextRow.size() ; index++){
                nextEntry.modifyForIndex(index, nextRow.get(index));
            }
            records.add(nextEntry);
        }
    }

}
