package csv_tools.json_parsing;

public class JsonDoubleLiteral extends JsonParseLiteral{
    private double value;

    public JsonDoubleLiteral(double value) {
        this.value = value;
    }

    @Override
    public String prettyPrint() {
        return ""+value;
    }

    public double getValue() {
        return value;
    }
}
