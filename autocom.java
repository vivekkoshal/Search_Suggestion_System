import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
public class autocom {
 
    public class autocomNode {
        Map<Character, autocomNode> children;
        char c;
        boolean isWord;
 
        public autocomNode(char c) {
            this.c = c;
            children = new HashMap<>();
        }
 
        public autocomNode() {
            children = new HashMap<>();
        }
 
        public void insert(String word) {
            if (word == null || word.isEmpty())
                return;
            char firstChar = word.charAt(0);
            autocomNode child = children.get(firstChar);
            if (child == null) {
                child = new autocomNode(firstChar);
                children.put(firstChar, child);
            }
 
            if (word.length() > 1)
                child.insert(word.substring(1));
            else
                child.isWord = true;
        }
 
    }
 
    autocomNode root;
 
    public autocom(List<String> words) {
        root = new autocomNode();
        for (String word : words)
            root.insert(word);
 
    }
 
    public boolean find(String prefix, boolean exact) {
        autocomNode lastNode = root;
        for (char c : prefix.toCharArray()) {
            lastNode = lastNode.children.get(c);
            if (lastNode == null)
                return false;
        }
        return !exact || lastNode.isWord;
    }
 
    public boolean find(String prefix) {
        return find(prefix, false);
    }
 
    public void suggestHelper(autocomNode root, List<String> list, StringBuffer curr) {
        if (root.isWord) {
            list.add(curr.toString());
        }
 
        if (root.children == null || root.children.isEmpty())
            return;
 
        for (autocomNode child : root.children.values()) {
            suggestHelper(child, list, curr.append(child.c));
            curr.setLength(curr.length() - 1);
        }
    }
 
    public List<String> suggest(String prefix) {
        List<String> list = new ArrayList<>();
        autocomNode lastNode = root;
        StringBuffer curr = new StringBuffer();
        for (char c : prefix.toCharArray()) {
            lastNode = lastNode.children.get(c);
            if (lastNode == null)
                return list;
            curr.append(c);
        }
        suggestHelper(lastNode, list, curr);
        return list;
    }
   
 
    public static void main(String[] args) {
        List<String> words = List.of("hello", "dog", "hell", "cat", "a", "hel","help","helps","helping");
        autocom autocom = new autocom(words);
     
        System.out.println(autocom.suggest("hel"));
    }
 
}