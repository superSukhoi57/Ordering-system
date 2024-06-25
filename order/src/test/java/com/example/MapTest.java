package com.example;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MapTest {
    @Test
    public void test(){
        Map<Integer,Integer> map=new HashMap<>();
        map.put(1,2);
        map.put(3,4);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());

        }
    }
}
