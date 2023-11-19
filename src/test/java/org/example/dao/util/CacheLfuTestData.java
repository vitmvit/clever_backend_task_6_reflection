package org.example.dao.util;

import org.example.cache.Cache;
import org.example.cache.impl.CacheLfu;

public class CacheLfuTestData {
    public static Cache<Integer, String> getCache() {
        Cache<Integer, String> cache = new CacheLfu<>(3);
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");
        return cache;
    }
}
