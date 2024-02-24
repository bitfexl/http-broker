package com.github.bitfexl.httpbroker.util;

import java.util.Arrays;

public class FixedSizeCache<T> {
    private final Object cacheLock = new Object();

    private final Object[] cache;

    private int nextIndex;

    /**
     * Init a new cache which saves up to n last added elements.
     * @param size The maximum number of elements in the cache.
     */
    public FixedSizeCache(int size) {
        cache = new Object[size];
        nextIndex = 0;
    }

    /**
     * Get the size of the cache.
     * @return The maximum number of elements which fit in the cache.
     */
    public int getSize() {
        return cache.length;
    }

    /**
     * Add an element. May overrides older elements no more free space left.
     * @param element The element to add.
     */
    public void add(T element) {
        synchronized (cacheLock) {
            cache[nextIndex] = element;
            if (++nextIndex == cache.length) {
                nextIndex = 0;
            }
        }
    }

    /**
     * Get the nth last element added.
     * @param n The nth last element added with 0 being the last added element.
     * @return The element or null if not existent.
     */
    @SuppressWarnings("unchecked")
    public T get(int n) {
        synchronized (cacheLock) {
            int index = nextIndex - (n + 1);
            if (index < 0) {
                index = cache.length + index;
            }
            return (T) cache[index];
        }
    }

    /**
     * Clear the cache (set all null).
     */
    public void clear() {
        synchronized (cacheLock) {
            Arrays.fill(cache, null);
        }
    }
}
