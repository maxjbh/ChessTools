package csv_tools.json_parsing;

import java.util.List;

public class JsonListLiteral extends JsonParseLiteral{
    private List<JsonParseLiteral> value;

    public JsonListLiteral(List<JsonParseLiteral> value) {
        this.value = value;
    }

    @Override
    public String prettyPrint() {
        return value.toString();
    }
}
