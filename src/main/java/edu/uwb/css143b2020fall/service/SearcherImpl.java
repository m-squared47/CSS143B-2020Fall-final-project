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
            indexWord_2 = index.get(word);

            //~~~~~traverse indexes in each document
            //find common docs using first 2 words
            //if not null and if word exists
            if(indexWord_1 != null && indexWord_2 != null) {
                //special case - word_1 and word_2 are the same words
                if(word_1.equals(word_2)){
                    findCommonDocsRepeat(indexWord_1, indexWord_2, result);
                }
                //normal case - word_1 and word_2 are different
                else if(indexWord_1.size() > 0 && indexWord_2.size() > 0){
                    findCommonDocs(indexWord_1, indexWord_2, result);
                }
                //fault is found, return clear result
                else{
                    return new ArrayList<>();
                }
            }
            //if one or both words do not exist, return empty result
            else{
                return new ArrayList<>();
            }

            //reinitialize indexWord_1 using indexWord_2
            indexWord_1.clear();
            for(List<Integer> element : indexWord_2){
                indexWord_1.add(element);
            }
        }

        /*
            Tasks                       Status (+ complete, ~ incomplete, - in progress)
            ----------------------------------
        Split keyPhrase                 +
        Solve for empty string          +
        Solve for single word input     +
        Solve for multi word input      +
        -Solve for 2+ word input        +
        -Solve for repeated words       +
        -Solve for white spaces         +
        Solve for if shows more than 2  -
         */

        System.out.println(result.toString() + " for target: " + keyPhrase);
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
    (List<List<Integer>> indexWord_1, List<List<Integer>> indexWord_2, List<Integer> common){
        //for every document
        for(int i = 0; i < indexWord_2.size(); i++){

            //if word 1 or word 2 do not exist in any documents, leave
            if(indexWord_1 == null || indexWord_2 == null)
                break;

            //if first word does not exist in the doc name
            //remove common doc if in the common list, continue
            if(indexWord_1.get(i).isEmpty()){
                if(common.contains(i)){
                    common.remove(Integer.valueOf(i));
                }
                continue;
            }
            List<Integer> indexes_1 = indexWord_1.get(i);

            //if second word does not exist in doc name, continue
            //remove common doc if in the common list, continue
            if(indexWord_2.get(i).isEmpty()){
                if(common.contains(i)){
                    common.remove(Integer.valueOf(i));
                }
                continue;
            }
            List<Integer> indexes_2 = indexWord_2.get(i);

            //for every element in indexes of word 1
            for(int f = 0; f < indexes_1.size(); f++){
                //for every element in indexes of word 2
                for(int j = 0; j < indexes_2.size(); j++){
                    //if the words are in order, add document id to the common list
                    if(indexes_1.get(f) == indexes_2.get(j) - 1){
                        //if not already in the list, add and break
                        if(!common.contains(i)) {
                            common.add(i);
                        }

                        break;
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

    /**
     * Special Case Method. Finds common documents if current word_1 and word_2 are the same.
     * @param indexWord_1
     * @param indexWord_2
     * @param common
     */
    private void findCommonDocsRepeat
            (List<List<Integer>> indexWord_1, List<List<Integer>> indexWord_2, List<Integer> common) {
        //for every document
        for(int i = 0; i < indexWord_2.size(); i++){
            //words are the same, only nee to compare one list
            //if word 1 or word 2 do not exist in any documents, leave
            if(indexWord_1 == null)
                break;


            if(indexWord_1.get(i).isEmpty() || indexWord_1.size() == 0)
                continue;
            List<Integer> indexes_1 = indexWord_1.get(i);
            List<Integer> indexes_2 = indexWord_2.get(i);

            //for every element in indexes_1 and indexes_2
            //indexes_2 is read 1 step ahead
            for(int f = 0; f < indexes_1.size(); f++){
                for(int j = f + 1; j < indexes_2.size() - 1; j++){
                    if(indexes_1.get(f) == indexes_2.get(j)){
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