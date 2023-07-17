package com.example.cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

class LRUCacheTest {

    private LRUCache lruCache;

    @BeforeEach
    void setUp() {
        lruCache = new LRUCache(3);
    }

    @AfterEach
    void tearDown() {
        lruCache = null;
    }


    @Test
    void getWhenCacheContainsKeyReturnsNodeValue() {
        lruCache.put(1, 11);
        int actualResult = lruCache.get(1);
        assertEquals(11, actualResult);
    }

    @Test
    void getWhenCacheContainsKeyMovesNodeToHead() {
        lruCache.put(1, 11);
        lruCache.put(2, 22);
        Node head = new Node(1, 11);
        lruCache.get(1);
        assertEquals(head.toString(), lruCache.getHead().toString());
    }

    @Test
    void getWhenKeyIsNotFoundInCacheReturnsNegative() {
        lruCache.put(1, 11);
        int value = lruCache.get(2);
        assertEquals(-1, value);
    }

    @Test
    void putWhenCacheContainsKeyThenValueIsUpdated() {
        lruCache.put(1, 11);
        lruCache.put(1, 22);
        assertEquals(22, lruCache.get(1));
    }

    @Test
    void putWhenCacheContainsKeyThenNodeIsMovedToHead() {
        lruCache.put(1, 11);
        lruCache.put(2, 22);
        lruCache.put(3, 33);

        Node head = new Node(1, 111);
        lruCache.put(1, 111);
        assertEquals(head.toString(), lruCache.getHead().toString());
    }

    @Test
    void putWhenKeyIsNotFoundInCacheThenNewElementIsAdded() {
        lruCache.put(1, 11);
        lruCache.put(2, 22);

        lruCache.put(3, 33);
        assertEquals(33, lruCache.get(3));
    }

    @Test
    void putWhenKeyIsNotFoundInCacheThenNewElementIsHead() {
        lruCache.put(1, 11);
        lruCache.put(2, 22);
        Node head = new Node(3, 33);
        lruCache.put(3, 33);
        assertEquals(head.toString(), lruCache.getHead().toString());
    }

    @Test
    void putWhenCacheCapacityExceedsTailIsRemoved() {
        lruCache.put(1, 11);
        lruCache.put(2, 22);
        lruCache.put(3, 33);

        Node tail = new Node(2, 22);
        lruCache.put(4, 44);

        assertEquals(-1, lruCache.get(1));
        assertEquals(tail.toString(), lruCache.getTail().toString());
    }
}