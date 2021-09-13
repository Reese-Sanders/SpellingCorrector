package spell;

public class Trie {
    private static final int ascii = 97;

    private int wordCount;
    private int nodeCount;
    private Node root;

    public Trie() {
        wordCount = 0;
        nodeCount = 0;
        root = new Node();
    }

    public Node getRoot(){
        return root;
    }

    /**
     * Adds the specified word to the trie (if necessary) and increments the word's frequency count.
     *
     * @param word the word being added to the trie
     */
    public void add(String word) {
        StringBuilder cutWord = new StringBuilder();
        cutWord.append(word);
        addHelper(root, cutWord);
    }

    private void addHelper(Node current, StringBuilder word){
        if (word.length() == 0) {
            if ( current.getValue() == 0){
                wordCount++;
            }
            current.incrementValue();
            return;
        }
        int index = castCharToInt(word.charAt(0));
        Node [] children = current.getChildren();
        if (children[index] == null){
            children[index] = new Node();
            word.deleteCharAt(0);
            nodeCount++;
            addHelper(children[index], word);
        } else {
            word.deleteCharAt(0);
            addHelper(children[index], word);
        }



    }

    private int castCharToInt(char cast){
        cast = Character.toLowerCase(cast);
        return (int) cast - ascii;
    }

    private char castIntToChar(int cast){
        cast += ascii;
        return (char) cast;
    }

    /**
     * Searches the trie for the specified word.
     *
     * @param word the word being searched for.
     *
     * @return a reference to the trie node that represents the word,
     * 			or null if the word is not in the trie
     */
    public INode find(String word){
        StringBuilder builtWord = new StringBuilder();
        builtWord.append(word);
        return findHelper(root, builtWord);
    }

    private INode findHelper(Node current, StringBuilder word){
        if (current == null){
            return null;
        }
        if (word.length() == 0){
            return current;
        } else {
            int index = castCharToInt(word.charAt(0));
            if (current.getChildren()[index] == null){
                return null;
            } else {
                word.deleteCharAt(0);
                return findHelper(current.getChildren()[index], word);
            }
        }

    }

    /**
     * Returns the number of unique words in the trie.
     *
     * @return the number of unique words in the trie
     */
    public int getWordCount(){
        return wordCount;
    }

    /**
     * Returns the number of nodes in the trie.
     *
     * @return the number of nodes in the trie
     */
    public int getNodeCount(){
        return nodeCount;
    }

    /**
     * The toString specification is as follows:
     * For each word, in alphabetical order:
     * <word>\n
     * MUST BE RECURSIVE.
     */
    @Override
    public String toString(){
        StringBuilder curWord = new StringBuilder();
        StringBuilder output = new StringBuilder();
        toStringHelper(root, curWord, output);
        return output.toString();
    }

    private void toStringHelper(Node current, StringBuilder curWord, StringBuilder output){
        if (current.getValue() > 0){
            output.append(curWord.toString());
            output.append("\n");
        }
        for (int i = 0; i < current.getChildren().length; ++i){
            Node child = current.getChildren()[i];
            if (child != null) {
                char childLetter = castIntToChar(i);
                curWord.append(childLetter);
                toStringHelper(child, curWord, output);
                curWord.deleteCharAt(curWord.length()-1);
            }
        }
    }

    /**
     * Returns the hashcode of this trie.
     * MUST be constant time.
     * @return a uniform, deterministic identifier for this trie.
     */
    @Override
    public int hashCode(){
        int hash = wordCount * nodeCount;
        for (int i = 0; i < root.getChildren().length; i++){
            if (root.getChildren()[i] != null){
                hash *= i+1;
            }
        }
        return hash;
    }

    /**
     * Checks if an object is equal to this trie.
     * MUST be recursive.
     * @param o Object to be compared against this trie
     * @return true if o is a Trie with same structure and node count for each node
     * 		   false otherwise
     */
    @Override
    public boolean equals(Object o){
        if (o == null) {
            System.out.println("O is null");
            return false;
        }
        if (o == this){
            System.out.println("O is same reference");
            return true;
        }
        if (o.getClass() != this.getClass()){
            System.out.println("O is not same class");
            return false;
        }
        Trie compDic = (Trie) o;
        if (this.wordCount != compDic.getWordCount() && this.nodeCount != compDic.getNodeCount()){
            System.out.println("Not same word count");
            return false;
        }
        return equalsHelper(this.root, compDic.getRoot());
    }

    private boolean equalsHelper(Node c1, Node c2){
        if (c1.getValue() != c2.getValue()){
            System.out.println("Word value is different");
            return false;
        }
        for (int i = 0; i < c1.getChildren().length; i++){
            if (c1.getChildren()[i] == null) {
                if (c2.getChildren()[i] != null) {
                    System.out.println("children differ 1");
                    return false;
                }
            } else {
                if (c2.getChildren()[i] == null){
                    System.out.println("children differ 2");
                    return false;
                }
            }
        }
        for (int i = 0; i < c1.getChildren().length; ++i){
            if (c1.getChildren()[i] != null) {
                if (!equalsHelper(c1.getChildren()[i], c2.getChildren()[i])) {
                    return false;
                }
            }
        }
        return true;
    }
}
