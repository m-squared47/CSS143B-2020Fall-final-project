package edu.uwb.css143b2020fall.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IndexerImpl implements Indexer {
    public Map<String, List<List<Integer>>> index(List<String> docs) {
        Map<String, List<List<Integer>>> indexes = new HashMap<>();
        // add your code

        //split name and find unique words
        List<String> uniqueWords = new ArrayList<>();
        for(String docName : docs){
            docName.replaceAll("\\s+", " ");
            String[] nameSplit = docName.split(" ");

            for(String word : nameSplit){
                //remove strings with length of 0 (not sure why this happens)
                if(word.length() == 0){
                    continue;
                }

                findUniqueWords(word, uniqueWords);
            }
        }

        //find the position of each word in each document name
        List<List<Integer>> docPositions = new ArrayList<>();

        for(String word : uniqueWords){
            if(word.length() == 0){
                continue;
            }

            for(String docName : docs){
                docPositions.add(findPositions(word, docName));
            }

            indexes.put(word, new ArrayList<>(docPositions));
            docPositions.clear();
        }

        return indexes;
    }

    /**
     * Makes a list of words with no repeats
     * @param word
     * @param listOfWords
     */
    private void findUniqueWords(String word, List<String> listOfWords){
        if(listOfWords.size() == 0){
            listOfWords.add(word);
        }else{
            if(!listOfWords.contains(word)){
                listOfWords.add(word);
            }
        }
    }

    /**
     * Finds the position of a target word within the given name of the document
     * @param wordToFind
     * @param docName
     * @return
     */
    private List<Integer> findPositions(String wordToFind, String docName){
        List<Integer> result = new ArrayList<>();
        String[] split = docName.split("\\s+");

        //strings with length of 0 are showing up so this will allow the index to be decremented by how many
        //are found
        int isNot = 0;

        for(int i = 0; i < split.length; i++){
            if(split[i].length() == 0){
                isNot++;
            }

            if(wordToFind.equals(split[i])){
                result.add(i - isNot);
            }
        }

        if(result.isEmpty()){
            return new ArrayList<>();
        }

        return result;
    }
}