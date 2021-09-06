
package br.com.simplecache.core;

import br.com.simplecache.annotations.Cacheable;
import br.com.simplecache.service.MessageService;
import java.lang.reflect.Method;
import java.util.logging.Logger;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Toda chamada de método é uma classe 'proxiada' passará pelo método intercept.
 * Através dele poderemos controloar o fluxo de execução e com isso salvar seu retorno 
 * em algum lugar
 */
public class CacheProxy implements MethodInterceptor {

    private static final Logger LOGGER = Logger.getLogger(CacheProxy.class.getName()); 
    
    private final CacheManager cacheManager;
    private final Object delegate;

    public CacheProxy(CacheManager cacheManager, Object delegate) {
        this.cacheManager = cacheManager;        
        this.delegate = delegate;
    }
        
    @Override
    public Object intercept(Object o, Method method, Object[] os, MethodProxy mp) throws Throwable {
        
        // Caso a anotação esteja presente, criamos o cache
        if(method.isAnnotationPresent(Cacheable.class)) {        
            
            LOGGER.info("Executando método cacheavel: " + method.getName());
            
            Cacheable cacheableAnnotation = method.getAnnotation(Cacheable.class);
            
            String cacheName = null;
            
            // Caso o cache não tenha nome vamos salvar o padroa {NOME_DA_CLASS}:{NOME_DO_METODO}
            if("".equals(cacheableAnnotation.cacheName())) {
                
                StringBuilder str = new StringBuilder();
                str.append(method.getDeclaringClass().getName());
                str.append(":");
                str.append(method.getName());
                
                cacheName = str.toString();
                
            } else {
                cacheName = cacheableAnnotation.cacheName();
            }
            
            LOGGER.info("Consultando valor no cache...");
            
            CacheValue cacheValue = cacheManager.getValueFromCache(cacheName, os);
            
            if(cacheValue != null) {
                
                LOGGER.info("Retornando valor cacheado");
                
                return cacheValue.getCacheValue();
            }
            
            LOGGER.info("Não foi encontrado nenhum valor no cache. Executando método original...");
            
            Object result = method.invoke(delegate, os);
            
            LOGGER.info("Salvando retorno no cache...");
            
            cacheManager.putValueInCache(cacheName.toString(), os, result);
            
            return result;
            
        }
        
        return mp.invoke(delegate, os);
    }
    
    public static <T> T newCacheableProxy(CacheManager cacheManager, T instance) {
        
        if(cacheManager == null) {
            throw new NullPointerException("O gerenciado de cache deve ser diferente de null");
        }
        
        if(instance == null) {
            throw new NullPointerException("A instancia a ser proxiada deve ser diferente de null");
        }
        
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(instance.getClass());
        enhancer.setCallback(new CacheProxy(cacheManager, instance));
        return (T) enhancer.create();
    }
    
}
