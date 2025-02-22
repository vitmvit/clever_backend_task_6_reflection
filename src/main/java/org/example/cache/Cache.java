package org.example.cache;

public interface Cache<K, V> {

    V get(K key);

    V put(K key, V value);

    V remove(K key);

    void clear();
}
