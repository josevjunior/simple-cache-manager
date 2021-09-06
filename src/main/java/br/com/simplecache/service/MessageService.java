
package br.com.simplecache.service;

import br.com.simplecache.annotations.Cacheable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MessageService {
    
    private static final List<Message> MESSAGES = new ArrayList<Message>();
    private static final Logger LOGGER = Logger.getLogger(MessageService.class.getName());
    
    static {
        MESSAGES.add(new Message(1L, "Mensagem Importante", "A vingança nunca é plena, mata a alma e a envenena"));
        MESSAGES.add(new Message(1L, "Mensagem Importante", "Pessoas boas devem amar seus inimigos"));
    }
    
    @Cacheable(cacheName = "USER_MESSAGE")
    public List<Message> getMessagesOfUser(Long userId) {
        LOGGER.info("Acessando messagens do usuário...");
        return MESSAGES.stream()
                .filter(message -> message.getIdUsuario().equals(userId))
                .collect(Collectors.toList());
    }
    
}
