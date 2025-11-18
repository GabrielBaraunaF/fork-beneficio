# Configuração do Banco de Dados H2

Este projeto utiliza o banco de dados H2 para desenvolvimento e testes.

## Arquivos Criados

1. **`pom.xml`** - Atualizado com:
   - Dependência do H2 Database
   - Plugin maven-ejb-plugin
   - Plugin maven-compiler-plugin

2. **`src/main/resources/META-INF/persistence.xml`** - Configuração JPA com:
   - Persistence Unit: `beneficioPU`
   - DataSource: `java:jboss/datasources/H2DS`
   - Dialeto: H2Dialect
   - Auto DDL: update (cria/atualiza tabelas automaticamente)

3. **`src/main/resources/datasources/beneficio-h2-ds.xml`** - Configuração do DataSource

## Como Configurar no WildFly

### Opção 1: Usar o arquivo datasource (Recomendado para desenvolvimento)

Copie o arquivo `beneficio-h2-ds.xml` para o diretório de deployments:

```bash
cp src/main/resources/datasources/beneficio-h2-ds.xml $WILDFLY_HOME/standalone/deployments/
```

### Opção 2: Configurar manualmente via CLI do WildFly

```bash
$WILDFLY_HOME/bin/jboss-cli.sh --connect

data-source add \
  --name=H2DS \
  --jndi-name=java:jboss/datasources/H2DS \
  --driver-name=h2 \
  --connection-url=jdbc:h2:mem:beneficiodb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE \
  --user-name=sa \
  --password= \
  --use-java-context=true \
  --enabled=true

exit
```

### Opção 3: Editar standalone.xml

Adicione dentro de `<datasources>`:

```xml
<datasource jndi-name="java:jboss/datasources/H2DS" pool-name="H2DS" enabled="true">
    <connection-url>jdbc:h2:mem:beneficiodb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE</connection-url>
    <driver>h2</driver>
    <security>
        <user-name>sa</user-name>
        <password></password>
    </security>
</datasource>
```

## Tipos de Configuração H2

### Banco em Memória (Desenvolvimento/Testes)
```
jdbc:h2:mem:beneficiodb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
```
- ✅ Rápido
- ✅ Ideal para testes
- ❌ Dados perdidos ao reiniciar

### Banco em Arquivo (Persistente)
```
jdbc:h2:file:~/beneficiodb;AUTO_SERVER=TRUE
```
- ✅ Dados persistem
- ✅ Pode ser acessado por múltiplas aplicações
- ❌ Um pouco mais lento

## Build e Deploy

```bash
# Compilar o projeto
mvn clean package

# Deploy no WildFly (copie o .jar gerado)
cp target/beneficio-ejb-0.0.1-SNAPSHOT.jar $WILDFLY_HOME/standalone/deployments/
```

## Testar a Conexão

Após o deploy, verifique os logs do WildFly:
```
tail -f $WILDFLY_HOME/standalone/log/server.log
```

Procure por:
- `Creating Subdeployment: beneficio-ejb-0.0.1-SNAPSHOT.jar`
- `Bound data source [java:jboss/datasources/H2DS]`
- Mensagens do Hibernate mostrando criação de tabelas

## Console Web do H2 (Opcional)

Para habilitar o console web do H2, adicione à URL de conexão:
```
jdbc:h2:mem:beneficiodb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;WEB_ALLOW_OTHERS=TRUE
```

E inicie o servidor H2:
```bash
java -cp ~/.m2/repository/com/h2database/h2/2.2.224/h2-2.2.224.jar org.h2.tools.Server
```

Acesse: http://localhost:8082

## Estrutura da Tabela BENEFICIO

Criada automaticamente pelo Hibernate:

```sql
CREATE TABLE BENEFICIO (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NOME VARCHAR(100) NOT NULL,
    DESCRICAO VARCHAR(255),
    VALOR DECIMAL(15,2) NOT NULL,
    ATIVO BOOLEAN,
    VERSION BIGINT
);
```

## Troubleshooting

### Erro: "IJ031084: Unable to create connection"
- Verifique se o DataSource está configurado
- Execute: `$WILDFLY_HOME/bin/jboss-cli.sh --connect --command="/subsystem=datasources:read-resource"`

### Erro: "No Persistence provider for EntityManager"
- Verifique se o `persistence.xml` está em `META-INF/`
- Confirme que o unitName está correto no `@PersistenceContext`

### Tabelas não são criadas
- Verifique se `hibernate.hbm2ddl.auto=update` está configurado
- Confira os logs do Hibernate






