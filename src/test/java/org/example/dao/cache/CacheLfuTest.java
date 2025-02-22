package org.example.dao.cache;

import org.example.cache.Cache;
import org.example.cache.impl.CacheLfu;
import org.example.dao.util.CacheLfuTestData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CacheLfuTest {
    @Test
    public void getByIdShouldReturnExpectedValueByKey() {
        Cache<Integer, String> cache = CacheLfuTestData.getCache();
        String value = cache.get(2);
        assertEquals("two", value);
    }

    @Test
    public void getByIdShouldReturnNullByNoExistingKey() {
        Cache<Integer, String> cache = CacheLfuTestData.getCache();
        String value = cache.get(4);
        assertNull(value);
    }

    @Test
    public void putShouldReturnAddedValueByNewKey() {
        Cache<Integer, String> cache = new CacheLfu<>(3);
        String oldValue = cache.put(1, "one");
        assertNull(oldValue);
        assertEquals("one", cache.get(1));
    }

    @Test
    public void putShouldReturnUpdatedValueByKey() {
        Cache<Integer, String> cache = new CacheLfu<>(3);
        cache.put(1, "one");
        String oldValue = cache.put(1, "newOne");
        assertEquals("one", oldValue);
        assertEquals("newOne", cache.get(1));
    }

    @Test
    public void removeRemovesValueByKey() {
        Cache<Integer, String> cache = new CacheLfu<>(3);
        cache.put(1, "one");
        String removedValue = cache.remove(1);
        assertEquals("one", removedValue);
        assertNull(cache.get(1));
    }

    @Test
    public void removeShouldReturnNullByNoExistingKey() {
        Cache<Integer, String> cache = new CacheLfu<>(3);
        cache.put(1, "one");
        String removedValue = cache.remove(2);
        assertNull(removedValue);
        assertEquals("one", cache.get(1));
    }

    @Test
    public void clearTest() {
        Cache<Integer, String> cache = CacheLfuTestData.getCache();
        cache.clear();
        assertNull(cache.get(1));
        assertNull(cache.get(2));
        assertNull(cache.get(3));
    }
}
