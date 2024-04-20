package csv_tools.json_parsing;

public class JsonIntegerLiteral extends JsonParseLiteral{
    private int value;

    public JsonIntegerLiteral(int value) {
        this.value = value;
    }

    @Override
    public String prettyPrint() {
        return ""+value;
    }

    public int getValue() {
        return value;
    }
}
