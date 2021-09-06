
package br.com.simplecache.core;

public class CacheValue {
    
    private long creationTime;
    private Object cacheValue;

    public CacheValue(long creationTime, Object cacheValue) {
        this.creationTime = creationTime;
        this.cacheValue = cacheValue;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public Object getCacheValue() {
        return cacheValue;
    }
    
}
