package csv_tools.json_parsing;

import csv_tools.CSVParseResult;

import java.util.Map;

public class JsonCsvParseResult extends CSVParseResult {

    private JsonMapLiteralContainer result;

    public JsonCsvParseResult(boolean success, JsonMapLiteralContainer result) {
        super(success);
        this.result = result;
    }

    @Override
    public void printResult() {
        if(!super.success){
            System.out.println("Csv parse failed.");
            return;
        }
        System.out.println(result.prettyPrint());
    }
}
