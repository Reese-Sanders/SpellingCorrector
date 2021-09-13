package spell;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector{
    Trie dictionary;

    public SpellCorrector(){
        dictionary = new Trie();
    }

    /**
     * Tells this <code>SpellCorrector</code> to use the given file as its dictionary
     * for generating suggestions.
     * @pre SpellCorrector will have had empty-param constructor called, but dictionary has nothing in it.
     * @param dictionaryFileName the file containing the words to be used
     * @throws IOException If the file cannot be read
     * @post SpellCorrector will have dictionary filled and be ready to suggestSimilarWord any number of times.
     */
    public void useDictionary(String dictionaryFileName) throws IOException{
        File dicFile = new File(dictionaryFileName);
        Scanner dicReader = new Scanner(dicFile);

        while (dicReader.hasNext()) {
            String toTrie = dicReader.next();
            dictionary.add(toTrie);
        }
        //past this is for test
/*
        File dicFile2 = new File("spell/words.txt");
        Scanner dicReader2= new Scanner(dicFile2);
        Trie dic2 = new Trie();
        while (dicReader2.hasNext()) {
            String toTrie = dicReader2.next();
            dic2.add(toTrie);
        }

        File dicFile3 = new File("spell/words.txt");
        Scanner dicReader3= new Scanner(dicFile3);
        Trie dic3 = new Trie();
        while (dicReader3.hasNext()) {
            String toTrie = dicReader3.next();
            dic3.add(toTrie);
        }*/
    }

    /**
     * Suggest a word from the dictionary that most closely matches
     * <code>inputWord</code>.
     * @param inputWord the word we are trying to find or find a suggestion for
     * @return the suggestion or null if there is no similar word in the dictionary
     */
    public String suggestSimilarWord(String inputWord){
        if (dictionary.find(inputWord) != null){
            if (dictionary.find(inputWord).getValue() > 0) {
                return inputWord;
            }
        }

        return suggestSimilarWordHelper(inputWord);
    }

    private String suggestSimilarWordHelper(String word){
        ArrayList<String> potWords = generateWordList(word);
        String simWord = findClosestWord(potWords);
        if (simWord == null) {
            ArrayList<String> potWords2 = new ArrayList<String>();
            for (int i = 0; i < potWords.size(); i++) {
                potWords2.addAll(generateWordList(potWords.get(i)));
            }
            simWord = findClosestWord(potWords2);
        }

        return simWord;
    }

    private String findClosestWord(ArrayList<String> potWords){
        //System.out.println("In FindClosestWord");
        String closeWord = null;
        Node maxNode = null;
        for (int i = 0; i < potWords.size(); i++){
            INode potNode = dictionary.find(potWords.get(i));
            if (potNode != null && potNode.getValue() > 0){
                if (closeWord == null){
                    closeWord = potWords.get(i);
                    maxNode = (Node) potNode;
                    //System.out.println("Current max word is (first occurance)" + closeWord + " with size " + maxNode.getValue());
                } else {
                    if (maxNode.getValue() < potNode.getValue()){
                        closeWord = potWords.get(i);
                        maxNode = (Node) potNode;
                        //System.out.println("Current max word is (In comp value) " + closeWord + " with size " + maxNode.getValue());
                    } else if (maxNode.getValue() == potNode.getValue()){
                        int comp = closeWord.compareToIgnoreCase(potWords.get(i));
                        if (comp > 0) {
                            closeWord = potWords.get(i);
                            maxNode = (Node) potNode;
                            //System.out.println("Current max word is (In compare)" + closeWord + " with size " + maxNode.getValue());
                        }
                    }
                }
            }
        }
        return closeWord;
    }

    private ArrayList<String> generateWordList (String word){
        ArrayList<String> potWords = new ArrayList();
        deletionDistance(word, potWords);
        transpositionDistance(word, potWords);
        alterationDistance(word, potWords);
        insertionDistance(word, potWords);
        return potWords;
    }

    private void deletionDistance(String word, ArrayList<String> potWords){
         for (int i = 0; i < word.length(); i++){
             StringBuilder toDel = new StringBuilder();
             toDel.append(word);
             toDel.deleteCharAt(i);
             potWords.add(toDel.toString());
         }
    }

    private void transpositionDistance(String word, ArrayList<String> potWords){
        for (int i = 0; i < word.length()-1; i++){
            StringBuilder toSwitch = new StringBuilder();
            toSwitch.append(word);
            char temp = toSwitch.charAt(i);
            toSwitch.setCharAt(i, toSwitch.charAt(i+1));
            toSwitch.setCharAt(i+1, temp);
            potWords.add(toSwitch.toString());
        }
    }

    private void alterationDistance(String word, ArrayList<String> potWords){
        for (int i = 0; i < word.length(); i++){
            StringBuilder toAlt = new StringBuilder();
            toAlt.append(word);
            for (int j = 0; j < 26; j++){
                toAlt.setCharAt(i, (char) (j + 97));
                potWords.add(toAlt.toString());
            }
        }
    }
    private void insertionDistance(String word, ArrayList<String> potWords){
        for (int i = 0; i < word.length()+1; i++){
            StringBuilder toIns = new StringBuilder();
            toIns.append(word);
            if(i == word.length()){
                toIns.append("a");
                potWords.add(toIns.toString());
            } else {
                toIns.insert(i, (char) (0 + 97));
                potWords.add(toIns.toString());
            }
            for (int j = 0; j < 26; j++){
                toIns.setCharAt(i, (char) (j + 97));
                potWords.add(toIns.toString());
            }
        }
    }
}
