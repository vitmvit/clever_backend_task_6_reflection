package org.example.dao.util;

import org.example.cache.Cache;
import org.example.cache.impl.CacheLru;

public class CacheLruTestData {
    public static Cache<Integer, String> getCache() {
        Cache<Integer, String> cache = new CacheLru<>(3);
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");
        return cache;
    }
}
