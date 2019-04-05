import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HashMapTest {

    HashMap<Integer, String> hashMap = new HashMap<>();


    @org.junit.jupiter.api.Test
    void put() {
        int size = 60000;
        for(int i = 0; i < size; i++){
            hashMap.put(i,"abcdefg");
        }
        assertEquals(hashMap.getSize(), size);
    }

    @org.junit.jupiter.api.Test
    void get() {
        for(Integer key : hashMap.keySet()){
            hashMap.get(key);
        }
    }

    @org.junit.jupiter.api.Test
    void setCapasity() {
        for(int i = 32; i < 1024; i *= 2)
        hashMap.setCapasity(i);
    }

    @org.junit.jupiter.api.Test
    void remove() {
        for(Integer key : hashMap.keySet()){
            hashMap.remove(key);
        }
        assertEquals(hashMap.getSize(), 0);
    }


}