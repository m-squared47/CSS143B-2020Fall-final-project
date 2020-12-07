package edu.uwb.css143b2020fall.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IndexerImpl implements Indexer {
    public Map<String, List<List<Integer>>> index(List<String> docs) {
        Map<String, List<List<Integer>>> indexes = new HashMap<>();
        // add your code

        //split doc name into strings for all docs
        List<List<String[]>> docNameSplit = new ArrayList<>();
        List<String[]> keyWords = new ArrayList<>();

        for(String name : docs) {
            keyWords.clear();
            keyWords.add(name.split("\\s+"));
            docNameSplit.add(keyWords);
        }

        //find keyword for all docs

        //find all location in each doc

        //add to hashmap

        return indexes;
    }
}