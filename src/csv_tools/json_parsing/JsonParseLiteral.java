package csv_tools.json_parsing;

public abstract class JsonParseLiteral {
    protected boolean doPrint = true;
    public abstract String prettyPrint();

    public JsonParseLiteral setDoPrint(boolean doPrint){
        this.doPrint = doPrint;
        return this;
    }
}
