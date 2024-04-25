import csv_tools.CSVParseResult;
import csv_tools.CsvReader;
import openings_tools.OpeningRecordListBuilder;
import openings_tools.OpeningsComparator;
import openings_tools.PrefixPrecisionGraph;

public class Main {
    public static void main(String[] args) {

        runTests();
        /*CsvReader csvReader = new OpeningsComparator();
        if(csvReader.tryToGetFileFromUserInput()){
            CSVParseResult parseResult = csvReader.parseFile();
            if(parseResult.isSuccess()){
                parseResult.printResult();
            }
        }*/
    }

    private static void runTests(){
        PrefixPrecisionGraph.runTest();
    }
}