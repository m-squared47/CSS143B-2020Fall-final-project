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

        //get all docs that contain all words of the given phrase


        //get the intersection of lists for the list of docs

        //find if keyphrase found is in the right place and order for the list of words in docs


        return result;
    }
}