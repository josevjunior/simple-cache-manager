
package br.com.simplecache.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class SimpleCacheManager implements CacheManager{

    private static final Logger LOGGER = Logger.getLogger(SimpleCacheManager.class.getName());
    
    private Map<CacheKey, CacheValue> cache;
    
    public SimpleCacheManager() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public CacheValue getValueFromCache(String cacheName, Object[] cacheKey) {
        return cache.get(new CacheKey(cacheName, cacheKey));
    }

    @Override
    public void putValueInCache(String cacheName, Object[] cacheKey, Object value) {
        cache.put(new CacheKey(cacheName, cacheKey), new CacheValue(System.currentTimeMillis(), value));
    }

    @Override
    public void removeValueInCache(String cacheName, Object[] params) {
        cache.remove(new CacheKey(cacheName, params));
    }

    @Override
    public void removeValuesOfCacheName(String cacheName) {
        
        Set<CacheKey> toRemove = new HashSet<>();
        
        Set<CacheKey> keySet = new HashSet<>(cache.keySet());
        for (CacheKey cacheKey : keySet) {
            if(cacheKey.cacheName.equals(cacheName)) {
                toRemove.add(cacheKey);
            }
        }
        
        for (CacheKey cacheKey : keySet) {
            cache.remove(cacheKey);
        }
    }

    @Override
    public void removeAll() {
        LOGGER.info("Limpando todo o cache");
        cache.clear();        
    }
    
    private static class CacheKey {
        
        private final String cacheName;
        private final Object[] cacheKeys;

        public CacheKey(String cacheName, Object[] cacheKeys) {
            this.cacheName = cacheName;
            this.cacheKeys = cacheKeys;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 23 * hash + Objects.hashCode(this.cacheName);
            hash = 23 * hash + Arrays.deepHashCode(this.cacheKeys);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final CacheKey other = (CacheKey) obj;
            if (!Objects.equals(this.cacheName, other.cacheName)) {
                return false;
            }
            if (!Arrays.deepEquals(this.cacheKeys, other.cacheKeys)) {
                return false;
            }
            return true;
        }
        
        
        
    }
    
    
}
