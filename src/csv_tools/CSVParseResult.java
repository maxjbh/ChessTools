package csv_tools;

public abstract class CSVParseResult {
    protected boolean success;

    public CSVParseResult(boolean success) {
        this.success = success;
    }

    public abstract void printResult();

    public boolean isSuccess() {
        return success;
    }
}
