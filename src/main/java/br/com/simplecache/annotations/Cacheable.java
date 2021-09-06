
package br.com.simplecache.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Essa anotação será acessada em tempo de exeução
@Target(ElementType.METHOD) // E apenas em métodos
public @interface Cacheable {
    
    
    /**
     * O nome do cache usado como seu indentificador. Caso seja vazio, o padrão
     * {NOME_DA_CLASSE}:{NOME_DO_METODO} será assumido. Ex: br.com.service.MyService:doService
     * @return 
     */
    String cacheName() default "";
}
