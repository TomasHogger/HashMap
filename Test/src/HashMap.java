import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class HashMap<K,V> {

    private int capasity = 16;
    private int size = 0;
    private Entry<K,V>[] entryArray;

    HashMap(){
        entryArray = new Entry[capasity];
    }

    HashMap(int capasity){
        this.capasity = capasity;
        entryArray = new Entry[capasity];
    }


    void put(K key, V value){
        synchronized (entryArray) {
            if (key == null) {
                if (entryArray[0] == null) {
                    entryArray[0] = new Entry<>(null, value);
                    size++;
                } else {
                    entryArray[0].setValue(value);
                }
            }

            int num = hash(key.hashCode());

            if (entryArray[num] == null) {
                entryArray[num] = new Entry<>(key, value);
                size++;
            } else {
                Entry<K, V> entry = entryArray[num];

                while (true) {
                    if (entry.getKey().equals(key)) {
                        entry.setValue(value);
                        return;
                    }

                    if (entry.getNext() == null) {
                        entry.setNext(new Entry<>(key, value));
                        size++;
                        break;
                    }

                    entry = entry.getNext();
                }

            }
        }
    }

    V get(K key){
        if(key == null){
            if (entryArray[0] == null){
                return null;
            }
            return entryArray[0].getValue();
        }

        int num = hash(key.hashCode());

        if(entryArray[num] == null){
            return null;
        } else {
            Entry<K,V> entry = entryArray[num];

            while (true){
                if(entry.getKey().equals(key)){
                    return entry.getValue();
                }

                if(entry.getNext() == null){
                    return null;
                }

                entry = entry.getNext();
            }
        }
    }


    public void remove(K key){
        synchronized (entryArray) {
            if (key == null) {
                if (entryArray[0] != null) {
                    size--;
                    entryArray[0] = entryArray[0].getNext();
                }
                return;
            }

            int num = hash(key.hashCode());

            if (entryArray[num] != null) {
                Entry<K, V> prev = entryArray[num];
                Entry<K, V> entry = prev.getNext();
                Entry<K, V> next = entry.next;

                if (prev.getKey().equals(key)) {
                    entryArray[num] = entry;
                    return;
                }

                while (true) {
                    if (entry.getKey().equals(key)) {
                        prev.setNext(next);
                        size--;
                        return;
                    }

                    if (next == null) {
                        return;
                    }

                    prev = entry;
                    entry = next;
                    next = entry.getNext();
                }
            }
        }
    }

    private int hash(int hash){
        return Math.abs(hash) % capasity;
    }

    public int getSize() {
        return size;
    }

    public void setCapasity(int capasity){
        this.capasity = capasity;
        reHash();
    }

    public Set<K> keySet(){
        Set<K> set = new TreeSet<>();
        for(Entry<K,V> entry : entryArray){
            while (entry != null) {
                set.add(entry.getKey());
                entry = entry.getNext();
            }
        }
        return set;
    }

    private void reHash(){
        synchronized (entryArray) {
            Entry<K, V>[] entries = entryArray;
            entryArray = new Entry[capasity];
            for (Entry<K, V> entry : entries) {
                while (entry != null) {
                    put(entry.getKey(), entry.getValue());
                    entry = entry.getNext();
                }
            }
        }
    }

    public int getCapasity(){
        return capasity;
    }


    class Entry<K,V>{

        private K key;
        private V value;
        private Entry<K,V> next;

        Entry(K key, V value){
            this.key = key;
            this.value = value;
        }


        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Entry<K,V> getNext() {
            return next;
        }

        public void setNext(Entry<K,V> next) {
            this.next = next;
        }
    }

}

