
package br.com.simplecache.main;

import br.com.simplecache.core.CacheManager;
import br.com.simplecache.core.CacheProxy;
import br.com.simplecache.core.SimpleCacheManager;
import br.com.simplecache.service.Message;
import br.com.simplecache.service.MessageService;
import java.util.List;

public class App {
    
    public static void main(String[] args) {
        
        CacheManager cacheManager = new SimpleCacheManager();
        
        // A partir daqui estamos usando o proxy
        MessageService messageService = CacheProxy.newCacheableProxy(cacheManager, new MessageService());
        
        // O primeiro acesso não deve vim do cache
        List<Message> messages = messageService.getMessagesOfUser(1L);
        
        // O segundo já deve vim do cache
        List<Message> cachedMessages = messageService.getMessagesOfUser(1L);
        
        // Nesse ponto as listas devem ter a mesma referencia
        System.out.println("É a mesma lista: " + (messages == cachedMessages));
        
        // Limpamos o cache
        cacheManager.removeAll();
        
        // Um segundo acesso sem passar pelo cache
        List<Message> messages2 = messageService.getMessagesOfUser(1L);
        
        // Não devem ter a mesma referencia
        System.out.println("É a mesma lista: " + (messages2 == cachedMessages));
        
    }
    
}
