package csv_tools;

import java.io.*;
import java.util.*;

public class CsvReader {

    protected Scanner fileScanner;

    public boolean tryToGetFileFromUserInput() {
        System.out.println("Enter a csv file path...");
        String path;
        try{
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));
            path = reader.readLine();
        } catch (IOException e){
            System.out.println("Path input error.");
            return false;
        }
        try{
            this.fileScanner = new Scanner(new File(path));
        } catch (FileNotFoundException e){
            System.out.println("Failed to locate csv.");
            return false;
        }
        return true;
    }

    public CSVParseResult parseFile(){
        List<List<String>> parsedCsv = new ArrayList<>();

        //Read line
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();

            //Scan the line for tokens
            try (Scanner rowScanner = new Scanner(line)) {
                List<String> nextRow = new ArrayList<>();
                rowScanner.useDelimiter(";");
                while (rowScanner.hasNext()) {
                    nextRow.add(rowScanner.next());
                }
                parsedCsv.add(nextRow);
            }
        }
        return new StandardCsvParseResult(true, parsedCsv);
    }

}
