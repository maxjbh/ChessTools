package csv_tools;

import java.util.List;

public class StandardCsvParseResult extends CSVParseResult{

    private List<List<String>> result;
    private List<String> columnHeaders;

    public StandardCsvParseResult(boolean success, List<List<String>> result) {
        super(success);
        this.result = result;
    }

    public StandardCsvParseResult(boolean success, List<List<String>> result, List<String> columnHeaders) {
        super(success);
        this.result = result;
        this.columnHeaders = columnHeaders;
    }

    @Override
    public void printResult() {
        if(!super.success){
            System.out.println("Csv parse failed.");
            return;
        }
        if(columnHeaders != null){
            System.out.println("Column order: " + columnHeaders.toString());
        }
        for(List<String> line : result){
            System.out.println(line.toString());
        }
    }
}
