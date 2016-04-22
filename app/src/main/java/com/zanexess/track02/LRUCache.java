package com.zanexess.track02;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

// LRU Cache в реализации синглтона.
public class LRUCache {
    private LruCache<String, Bitmap> _memoryCache;
    private static LRUCache _instance;

    private LRUCache() {}

    private void init() {
        // Инициализация кэша
        if (_memoryCache == null) {
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            final int cacheSize = maxMemory / 8;
            _memoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                }
            };
        }
    }

    public static void createInstance() {
        if (null == _instance) {
            _instance = new LRUCache();
            _instance.init();
        }
    }

    public static LRUCache instance() {
        return _instance;
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        _memoryCache.put(key, bitmap);
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return _memoryCache.get(key);
    }
}
