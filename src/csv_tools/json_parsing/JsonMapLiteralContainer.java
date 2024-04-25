package csv_tools.json_parsing;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonMapLiteralContainer extends JsonParseLiteral{
    private LinkedHashMap<String, JsonParseLiteral> value;

    private int indentLevel = 0;
    private OrderBy orderBy;

    //CONSTRUCTORS ======================================================================

    public JsonMapLiteralContainer(){
        value = new LinkedHashMap<>();
    }
    public JsonMapLiteralContainer(OrderBy orderBy){
        value = new LinkedHashMap<>();
        this.orderBy = orderBy;
    }

    //METHODS ============================================================================

    public void putInMap(String key, JsonParseLiteral value){
        this.value.put(key, value);
    }

    public JsonParseLiteral getInMap(String key){
        return value.get(key);
    }

    public boolean mapContainsKey(String key){
        return value.containsKey(key);
    }

    /**
     * Keep only the specified amount of results
     * @param limit
     */
    public void limit(int limit){
        LinkedHashMap<String, JsonParseLiteral> limitedMap = new LinkedHashMap<>();
        int count = 0;

        for (Map.Entry<String, JsonParseLiteral> entry : value.entrySet()) {
            limitedMap.put(entry.getKey(), entry.getValue());
            count++;
            if (count >= limit) {
                break;
            }
        }
        value = limitedMap;
    }

    @Override
    public String prettyPrint() {
        StringBuilder result = new StringBuilder();
        //Sort map
        Map<String, JsonParseLiteral> sorted = value;
        if(orderBy != null){
            sorted = value.entrySet()
                    .stream()
                    .sorted(orderBy.getComparator())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        }
        for(String key : sorted.keySet()){
            JsonParseLiteral nextLiteral = sorted.get(key);
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
