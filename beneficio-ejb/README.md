# 💼 Benefício EJB

Enterprise JavaBean (EJB) para gerenciamento de benefícios corporativos. Implementa a lógica de negócio e persistência de dados, expondo serviços remotos para aplicações cliente.

## 🛠️ Tecnologias

- **Java 17**
- **Jakarta EE 10** (EJB 4.0, JPA 3.0)
- **WildFly 30.0.1** - Application Server
- **Hibernate** - ORM
- **H2 Database** - Banco em memória
- **Maven**

## 🏗️ Propósito

O EJB roda no WildFly e fornece:
- **Gestão de Benefícios** - CRUD de benefícios corporativos
- **Transferências** - Movimentação de valores entre benefícios
- **Persistência** - Gerenciamento de dados via JPA/H2
- **Acesso Remoto** - Interface remota para clientes (Spring Boot)

## 📦 Estrutura

```
entity/         → Entidades JPA (Beneficio)
service/        → Beans remotos (BeneficioServiceBean)
to/             → Transfer Objects (BeneficioTO)
exception/      → Exceções de negócio (BusinessException)
```

## 🚀 Executando com Docker

```bash
# Build da imagem
docker build -t beneficio-ejb .

# Executar container
docker run -d -p 8080:8080 -p 9990:9990 --name wildfly-ejb beneficio-ejb

# Ver logs
docker logs -f wildfly-ejb
```

**Acessar:**
- Aplicação: `http://localhost:8080`
- Management Console: `http://localhost:9990` (admin/Admin@123)

## ⚙️ Desenvolvimento Local

```bash
# Compilar
mvn clean package

# Deploy manual
cp target/beneficio-ejb-0.0.1-SNAPSHOT.jar $WILDFLY_HOME/standalone/deployments/
cp src/main/resources/datasources/beneficio-h2-ds.xml $WILDFLY_HOME/standalone/deployments/

# Iniciar WildFly
$WILDFLY_HOME/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 -bprivate 0.0.0.0
```

## 📡 JNDI Lookup

Para acessar o EJB remotamente:

```
ejb:/beneficio-ejb/BeneficioServiceBean!br.com.beneficio.ejb.service.BeneficioServiceRemote
```

---

**Requisito:** WildFly configurado com DataSource H2 (`java:jboss/datasources/H2DS`)



