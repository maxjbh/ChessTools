package openings_tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class used to reduce opening csvs, by keeping only as far up to a certain point in the prefixes
 * Precision is how deep to go, with 0 being highest prefixes only, example Vienna
 * The class contains a DAG which has ending nodes which are kept in the highestPrefixes list
 * The nodes of the graph represent the relation isPrefixOf
 */
public class PrefixPrecisionGraph {
    private List<Node> highestLevelPrefixes = new ArrayList<>();

    public PrefixPrecisionGraph(){}

    /**
     *
     * @param precision
     * @return all nodes of precision layers away from root, or terminal nodes of lower precision
     */
    public List<String> getNodesOfPrecision(int precision){
        return getNodesOfPrecisionRecursive(precision, 0, highestLevelPrefixes);
    }

    private List<String> getNodesOfPrecisionRecursive(int endLayer, int currentLayer, List<Node> nodes){
        if(currentLayer == endLayer){
            return nodes.stream().map(node -> node.prefix).collect(Collectors.toList());
        }
        List<String> result = new ArrayList<>();
        for(Node node : nodes){
            if(node.children.isEmpty()){
                result.add(node.prefix);
            }else{
                result.addAll(getNodesOfPrecisionRecursive(endLayer, currentLayer + 1, node.children));
            }
        }
        return result;
    }

    public void insertPrefix(String value){
        GetSuffixesOrAPrefixOfInResult getSuffixesOrAPrefixOfInResult = getSuffixesOrAPrefixOfIn(value, highestLevelPrefixes, null);
        Node parent = getSuffixesOrAPrefixOfInResult.parentNode;
        boolean isPrefix = getSuffixesOrAPrefixOfInResult.valueIsAPrefixOfAtLeastOnePrefix;
        if(!isPrefix){
            if(parent == null){
                highestLevelPrefixes.add(new Node(value, new ArrayList<>()));
                return;
            }
            parent.children.add(new Node(value, new ArrayList<>()));
            return;
        }
        List<Node> foundSuffixesOrPrefix = getSuffixesOrAPrefixOfInResult.foundSuffixesOrPrefix;
        List<Node> listToRemoveFrom = highestLevelPrefixes;
        if(parent != null){
            listToRemoveFrom = parent.children;
        }
        for(Node suffix : foundSuffixesOrPrefix){
            listToRemoveFrom.remove(suffix);
        }
        listToRemoveFrom.add(new Node(value, foundSuffixesOrPrefix));
    }

    /**
     * A recursive function, uses the booleans in the return object to decide if we keep going.
     * Stops if - our value is a prefix of some of the checkedNodes in the list
     *          - or if our value is neither a prefix nor a suffix of the checkedNodes in the list
     * Continues if - our value is a suffix of an element in the list
     * Caller handles creation of new node and list modification
     * @param value
     * @param checkedNodes
     * @param parent null, if first pass, otherwise used to determine which child links to remove
     * @return a GetSuffixesOrAPrefixOfInResult, containing a list to remove elements from if there is value is a prefix of, and if there is  parent.
     */
    private GetSuffixesOrAPrefixOfInResult getSuffixesOrAPrefixOfIn(String value, List<Node> checkedNodes, Node parent){
        boolean valueIsAPrefixOfAtLeastOnePrefix = false;
        boolean APrefixIsPrefixOfValue = false;
        Node foundPrefixOrSuffix = new Node("SomePrefix", null);
        int prefixIndex = 0;
        while(!valueIsAPrefixOfAtLeastOnePrefix && !APrefixIsPrefixOfValue && prefixIndex<checkedNodes.size()){
            foundPrefixOrSuffix = checkedNodes.get(prefixIndex);
            valueIsAPrefixOfAtLeastOnePrefix = foundPrefixOrSuffix.prefix.startsWith(value);
            APrefixIsPrefixOfValue = value.startsWith(foundPrefixOrSuffix.prefix);
            prefixIndex++;
        }
        //Case new word met, not a prefix or a suffix, return
        if(!valueIsAPrefixOfAtLeastOnePrefix && !APrefixIsPrefixOfValue){
            return new GetSuffixesOrAPrefixOfInResult(Collections.emptyList(), false, parent);
        }
        if(valueIsAPrefixOfAtLeastOnePrefix){
            //Get all children
            List<Node> children = new ArrayList<>();
            for(Node prefix : checkedNodes){
                if(prefix.prefix.startsWith(value)){
                    children.add(prefix);
                }
            }
            return new GetSuffixesOrAPrefixOfInResult(children, true, parent);
        }
        //repeat function
        return getSuffixesOrAPrefixOfIn(value, foundPrefixOrSuffix.children, foundPrefixOrSuffix);
    }

    private static class GetSuffixesOrAPrefixOfInResult{
        /**
         * Singleton if we found a prefix of our value
         * Empty if none found
         */
        List<Node> foundSuffixesOrPrefix;
        boolean valueIsAPrefixOfAtLeastOnePrefix;
        Node parentNode;

        public GetSuffixesOrAPrefixOfInResult(List<Node> foundSuffixesOrPrefix, boolean valueIsAPrefixOfAtLeastOnePrefix, Node parentNode) {
            this.foundSuffixesOrPrefix = foundSuffixesOrPrefix;
            this.valueIsAPrefixOfAtLeastOnePrefix = valueIsAPrefixOfAtLeastOnePrefix;
            this.parentNode = parentNode;
        }
    }

    private static class Node{
        String prefix;
        List<Node> children;

        public Node(String prefix, List<Node> children) {
            this.prefix = prefix;
            this.children = children;
        }
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        recursiveToString(new ArrayList<>(), highestLevelPrefixes, stringBuilder);
        return stringBuilder.toString();
    }

    private void recursiveToString(List<Node> encounteredNodes, List<Node> nodes, StringBuilder result){
        List<Node> nextGenEncounteredNodes = new ArrayList<>(encounteredNodes);
        for(Node node : nodes){
            nextGenEncounteredNodes.add(node);
            if(node.children.isEmpty()){
                for(Node terminal : nextGenEncounteredNodes){
                    result.append(terminal.prefix + " ; ");
                }
                result.append("\n");
                return;
            }
            recursiveToString(nextGenEncounteredNodes, node.children, result);
            nextGenEncounteredNodes.remove(node);
        }
    }

    public static void runTest(){
        PrefixPrecisionGraph test = new PrefixPrecisionGraph();
        test.insertPrefix("VG");
        test.insertPrefix("VGA");
        test.insertPrefix("V");
        test.insertPrefix("KG");
        test.insertPrefix("VZ");
        test.insertPrefix("VGD");
        test.insertPrefix("VGAQP");
        test.insertPrefix("KGD");
        System.out.println(test.toString());
        System.out.println(test.getNodesOfPrecision(0));
        System.out.println(test.getNodesOfPrecision(1));
        System.out.println(test.getNodesOfPrecision(2));
    }
}
