
package br.com.simplecache.core;

/**
 * O gerenciador de cache. Através dos métodos desta interface podemos
 * acessar, remover e atualizar valores que desejamos cachear
 */
public interface CacheManager {
    
    /**
     * Acessa um valor no cache. Caso o valor não exista, irá return
     * null
     * 
     * @param cacheName O identificador do cache
     * @param params Um conjunto de parametros que identifica o valor
     * 
     * @return Um valor cacheado ou null
     */
    CacheValue getValueFromCache(String cacheName, Object[] params);
    
    /**
     * Adiciona ou atualiza um valor no cache
     * 
     * @param cacheName O identificador do cache
     * @param params Um conjunto de parametros que identifica o valor
     * @param value O valor a ser cacheado
     */
    void putValueInCache(String cacheName, Object[] params, Object value);
    
    /**
     * Remove um valor no cache
     * 
     * @param cacheName O identificador do cache
     * @param params Um conjunto de parametros que identifica o valor
     */
    void removeValueInCache(String cacheName, Object[] params);
    
    /**
     * Remove todos os valores que corresponda ao valor informado
     * 
     * @param cacheName O identificador do cache
     * @param params Um conjunto de parametros que identifica o valor
     */
    void removeValuesOfCacheName(String cacheName);
    
    /**
     * Remove todos os valores que estão em cache
     */
    void removeAll();
    
}
