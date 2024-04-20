package csv_tools.json_parsing;

public class JsonStringLiteral extends JsonParseLiteral{
    private String value;

    public JsonStringLiteral(String value) {
        this.value = value;
    }

    @Override
    public String prettyPrint() {
        return value;
    }
}
