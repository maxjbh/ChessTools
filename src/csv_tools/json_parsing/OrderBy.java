package csv_tools.json_parsing;

import java.util.Comparator;
import java.util.Map;

public class OrderBy {

    private Comparator<Map.Entry<String, JsonParseLiteral>> comparator;

    /**
     * Default order by key alphabetically
     */
    public OrderBy(){
        comparator = (entry1, entry2) -> {
            // Compare based on the key
            int keyComparison = entry1.getKey().compareTo(entry2.getKey());
            return keyComparison;
        /*if (keyComparison != 0) {
            // If keys are different, return comparison result
            return keyComparison;
        } else {
            // If keys are equal, compare based on the value
            // You need to define how you want to compare JsonParseLiteral objects
            // For example, assuming JsonParseLiteral implements Comparable
            return entry1.getValue().compareTo(entry2.getValue());
        }*/
        };
    }

    public OrderBy(Comparator<Map.Entry<String, JsonParseLiteral>> comparator){
        this.comparator = comparator;
    }

    public Comparator<Map.Entry<String, JsonParseLiteral>> getComparator(){
        return comparator;
    }
}
