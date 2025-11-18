# 💰 Benefício Backend

API REST desenvolvida em Spring Boot para gerenciamento de benefícios corporativos. A aplicação atua como um cliente que se comunica com um servidor EJB remoto (WildFly) para expor funcionalidades através de endpoints HTTP modernos.

## 🛠️ Tecnologias

- **Java 17**
- **Spring Boot 3.5.7**
- **Maven**
- **Lombok** - Redução de código boilerplate
- **WildFly EJB Client 38.0** - Comunicação remota com servidor EJB
- **SpringDoc OpenAPI 2.8.3** - Documentação automática (Swagger)

## 🏗️ Arquitetura

```
Cliente → REST API (Spring Boot) → EJB Remoting → WildFly Server
```

O backend recebe requisições HTTP REST, realiza chamadas ao EJB remoto via JNDI lookup, e retorna as respostas formatadas em JSON.

## 📦 Estrutura de Pacotes

### `config/`
Configurações da aplicação:
- **EjbConfig** - Configura conexão JNDI com servidor WildFly e lookup do bean remoto
- **WebConfig** - Configuração de CORS para permitir requisições de outras origens

### `controller/`
Camada de apresentação:
- **BeneficioController** - Endpoints REST que expõem as funcionalidades da API

### `service/`
Camada de negócio:
- **BeneficioClientService** - Encapsula chamadas ao EJB remoto e adiciona lógica de integração

### `to/` (Transfer Objects/DTOs)
Objetos de transferência de dados:
- **BeneficioTO** - Representa um benefício (id, nome, descrição, valor, ativo)
- **TransferRequestTO** - Payload para requisição de transferência (fromId, toId, amount)
- **ErroOutBoundTO** - Estrutura padronizada de erro (status, message)

### `handler/`
Tratamento de exceções:
- **GlobalExceptionHandler** - Captura exceções globalmente e retorna erros padronizados

## 📡 Endpoints

**Base URL:** `http://localhost:8090/beneficio`

### `GET /beneficio/list`
Lista todos os benefícios disponíveis.

**Resposta:**
```json
[
  {
    "id": 1,
    "nome": "Vale Alimentação",
    "descricao": "Benefício para alimentação",
    "valor": 500.00,
    "ativo": true
  }
]
```

### `GET /beneficio/{id}`
Busca um benefício específico por ID.

**Parâmetros:**
- `id` (Long) - ID do benefício

**Resposta:**
```json
{
  "id": 1,
  "nome": "Vale Alimentação",
  "descricao": "Benefício para alimentação",
  "valor": 500.00,
  "ativo": true
}
```

### `POST /beneficio/transfer`
Realiza transferência de valor entre dois benefícios.

**Body:**
```json
{
  "fromId": 1,
  "toId": 2,
  "amount": 100.00
}
```

**Respostas:**
- `200 OK` - Transferência realizada com sucesso
- `400 Bad Request` - Erro de validação ou regra de negócio
- `500 Internal Server Error` - Erro no servidor

---

## 🚀 Como Executar

```bash
# Clonar repositório
git clone <url-do-repo>
cd beneficio-backend

# Compilar
mvn clean package

# Executar
java -jar target/beneficio-backend-0.0.1-SNAPSHOT.jar
```

Aplicação disponível em: **http://localhost:8090**

Documentação Swagger: **http://localhost:8090/swagger-ui.html**

---

**Requisito:** Servidor WildFly com o EJB de benefícios deve estar rodando em `localhost:8080`