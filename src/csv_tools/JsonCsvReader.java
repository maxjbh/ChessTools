package csv_tools;

import csv_tools.json_parsing.JsonCsvParseResult;
import csv_tools.json_parsing.JsonMapLiteralContainer;
import csv_tools.json_parsing.OrderBy;

import java.util.*;

public abstract class JsonCsvReader extends CsvReader{
    protected JsonMapLiteralContainer result = new JsonMapLiteralContainer(new OrderBy());

    protected abstract void readLine(List<String> line);

    @Override
    public CSVParseResult parseFile(){

        //Read line
        while (super.fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();

            //Scan the line for tokens
            try (Scanner rowScanner = new Scanner(line)) {
                List<String> nextRow = new ArrayList<>();
                rowScanner.useDelimiter(";");
                while (rowScanner.hasNext()) {
                    nextRow.add(rowScanner.next());
                }
                readLine(nextRow);
            }
        }
        return new JsonCsvParseResult(true, result);
    }
}
