package csv_tools.json_parsing;

import java.util.HashMap;
import java.util.Map;

public class JsonMapLiteralContainer extends JsonParseLiteral{
    private Map<String, JsonParseLiteral> value;

    private int indentLevel = 0;

    public JsonMapLiteralContainer(){
        value = new HashMap<>();
    }

    public JsonMapLiteralContainer(Map<String, JsonParseLiteral> value) {
        this.value = value;
    }

    public void putInMap(String key, JsonParseLiteral value){
        this.value.put(key, value);
    }
    public JsonParseLiteral getInMap(String key){
        return value.get(key);
    }

    @Override
    public String prettyPrint() {
        StringBuilder result = new StringBuilder();
        for(String key : value.keySet()){
            JsonParseLiteral nextLiteral = value.get(key);
            if(!nextLiteral.doPrint){
                continue;
            }
            if(nextLiteral instanceof JsonMapLiteralContainer){
                ((JsonMapLiteralContainer) nextLiteral).setIndentLevel(this.indentLevel + 1);
                result.append(getIndentSpaces()).append(key).append(": \n").append(nextLiteral.prettyPrint());
            }else{
                result.append(getIndentSpaces()).append(key).append(" : ").append(nextLiteral.prettyPrint()).append("\n");
            }
        }
        return result.toString();
    }

    private String getIndentSpaces(){
        StringBuilder result = new StringBuilder();
        for(int i = 0 ; i<indentLevel ; i++){
            result.append("    ");
        }
        return result.toString();
    }

    public void setIndentLevel(int indentLevel) {
        this.indentLevel = indentLevel;
    }
}
