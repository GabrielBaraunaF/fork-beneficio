package br.com.beneficiobackend.beneficiobackend.config;

import br.com.beneficio.ejb.service.BeneficioServiceRemote;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

@Configuration
public class EjbConfig {

    @Value("${wildfly.host:localhost}")
    private String wildflyHost;

    @Value("${wildfly.port:8080}")
    private int wildflyPort;

    @Value("${wildfly.username:ejbuser}")
    private String username;

    @Value("${wildfly.password:ejbpass123}")
    private String password;

    @Value("${wildfly.jndi}")
    private String jndiName;

    private InitialContext context;

    @Bean
    @Scope("singleton") // EJB remoto é thread-safe → singleton é seguro e recomendado
    public BeneficioServiceRemote beneficioServiceRemote() throws NamingException {
        Properties props = new Properties();
        
        // Fábrica correta para WildFly 26+
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        
        // URL do remoting (OBRIGATÓRIO http-remoting://)
        props.put(Context.PROVIDER_URL, "http-remoting://" + wildflyHost + ":" + wildflyPort);

        // Autenticação (OBRIGATÓRIO com Elytron)
        props.put(Context.SECURITY_PRINCIPAL, username);
        props.put(Context.SECURITY_CREDENTIALS, password);

        // ESSA PROPRIEDADE É OBRIGATÓRIA NO WILDFLY 26+
        props.put("jboss.naming.client.ejb.context", "true");

        // DICA EXTRA: evita warnings no log
        props.put("org.jboss.ejb.client.naming", "true");

        // Cria o contexto uma vez só
        context = new InitialContext(props);

        // Faz o lookup
        Object bean = context.lookup(jndiName);

        // Debug (remova em produção se quiser)
        System.out.println("EJB conectado com sucesso! JNDI: " + jndiName);

        return (BeneficioServiceRemote) bean;
    }

    // Boa prática: fechar o contexto ao desligar a aplicação
    @PreDestroy
    public void closeContext() {
        if (context != null) {
            try {
                context.close();
            } catch (NamingException e) {
                // ignore
            }
        }
    }
}