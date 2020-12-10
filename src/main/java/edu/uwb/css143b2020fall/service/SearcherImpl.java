package edu.uwb.css143b2020fall.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
DO NOT CHANGE
 */

@Service
public class SearcherImpl implements Searcher {
    public List<Integer> search(String keyPhrase, Map<String, List<List<Integer>>> index) {
        List<Integer> result = new ArrayList<>();
        // add your code

        //split keyphrase by spaces
        String[] keyWords = keyPhrase.split("\\s+");

        //documents where there is no match
        List<Integer> notCommon = new ArrayList<>();

        if(keyWords.length == 0){           //no input
            return result;
        }

        else if(keyWords.length == 1){      //single word input
            if(index.get(keyWords[0]) == null)
                return result;

            findDocuments(keyWords[0], index.get(keyWords[0]), result);
            return result;
        }

        String word_1 = null;               //multi word input
        String word_2;
        List<List<Integer>> indexWord_1 = new ArrayList<>();
        List<List<Integer>> indexWord_2;

        for(String word : keyWords){
            //~~~~~traverse the keyphrase
            //next word into word 2
            word_2 = word;

            //if this is the first iteration (word 1 is null), put current word into word 1 and reiterate
            if(word_1 == null){
                word_1 = word;
                indexWord_1 = index.get(word);
                continue;
            }

            //~~~~~traverse documents
            //both word_1 and word_2 have elements
            //initialize list of documents for both words
            //      word_1 initialized on first iteration and after each iteration
            indexWord_2 = index.get(word_2);

            //~~~~~traverse indexes in each document
            //find common docs using first 2 words
            if(indexWord_1 != null && indexWord_2 != null) {
                findCommonDocs(indexWord_1, indexWord_2, result, notCommon);
            }
            //if one or both words do not exist, break and return empty result
            else{
                break;
            }

            //reinitialize indexWord_1 using indexWord_2
            indexWord_1.clear();
            for(List<Integer> element : indexWord_2){
                indexWord_1.add(element);
            }
        }

        //get all docs that contain all words of the given phrase
        //get the intersection of lists for the list of docs
        //if one word is given, just find the documents it is contained in
            //next list goes into list 2
            //if list 1 is not empty, find match
            //go through entire list of lists then return result to common

        return result;
    }

    /**
     * Finds which documents a SINGLE word can be found
     * @param word
     * @param index
     * @param common
     */
    private void findDocuments(String word, List<List<Integer>> index, List<Integer> common){
        for(int i = 0; i < index.size(); i++){
            if(index.get(i).isEmpty()){
                continue;
            }

            common.add(i);
        }
    }

    /**
     * Finds documents with MULTIPLE words in the correct order. Word 2 comes after Word 1. Matched Documents
     * are put into the "common" list. Does not return any objects
     * @param indexWord_1
     * @param indexWord_2
     * @param common
     */
    private void findCommonDocs
    (List<List<Integer>> indexWord_1, List<List<Integer>> indexWord_2, List<Integer> common, List<Integer> notCommon){
        //for every document
        for(int i = 0; i < indexWord_2.size(); i++){
            //if word 1 or word 2 do not exist in any documents, leave
            if(indexWord_1 == null || indexWord_2 == null)
                break;

            //if first word does not exist in the doc name, continue
            if(indexWord_1.get(i).isEmpty() || indexWord_1.size() == 0)
                continue;
            List<Integer> indexes_1 = indexWord_1.get(i);

            //if second word does not exist in doc name, continue
            if(indexWord_2.get(i).isEmpty() || indexWord_2.size() == 0)
                continue;
            List<Integer> indexes_2 = indexWord_2.get(i);

            //if the document is already found as not common, skip
            if(notCommon.contains(i))
                continue;

            //for every element in indexes of word 1
            for(int f = 0; f < indexes_1.size(); f++){
                //for every element in indexes of word 2
                for(int j = 0; j < indexes_2.size(); j++){
                    //if the words are in order, add document id to the common list
                    if(indexes_1.get(f) == indexes_2.get(j) - 1){
                        //if not already in the list
                        if(!common.contains(i)) {
                            common.add(i);
                        }
                    }
                    //if in common and found as a non-match, remove from common
                    else if(!common.isEmpty()){
                        if(common.contains(i)){
                            common.remove(Integer.valueOf(i));
                        }
                    }
                }
            }
        }
    }
}