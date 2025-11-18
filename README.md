# 💰 Sistema de Transferência de Benefícios

Sistema completo para gerenciamento e transferência de valores entre benefícios corporativos, implementado em arquitetura distribuída de 3 camadas.

## 🏗️ Arquitetura

```
Angular (Frontend)  →  Spring Boot (API REST)  →  WildFly + EJB (Lógica de Negócio)
  Porta 4200              Porta 8090                  Porta 8080 + H2 Database
```

**Comunicação:**
- Angular ↔ Spring Boot: HTTP REST (JSON)
- Spring Boot ↔ EJB: Remote EJB via JNDI
- EJB ↔ Banco: JPA/Hibernate

## 🛠️ Tecnologias Principais

### EJB (Enterprise JavaBeans)
- **WildFly 30.0.1** - Application Server
- **Jakarta EE 10** - EJB 4.0, JPA 3.0
- Lógica de negócio e persistência
- Transações gerenciadas pelo container

### Spring Boot
- **Spring Boot 3.5.7**
- **WildFly EJB Client** - Comunicação remota
- API REST moderna
- Ponte entre frontend e EJB

### Angular
- **Angular 18** (Standalone Components)
- **TypeScript** + RxJS
- Interface web responsiva
- Servido via Nginx

## 📦 Estrutura do Projeto

```
beneficio/
├── beneficio-ejb/          # EJB + JPA + H2
├── beneficio-backend/      # Spring Boot + REST API
├── front/frontend/         # Angular + Nginx
└── docker-compose.yml      # Orquestração
```

## 🚀 Como Executar

### Subir toda a aplicação

```bash
docker compose up --build
```

Aguarde ~2 minutos para todos os serviços subirem.

### Acessar

- **Frontend:** http://localhost:4200
- **API REST:** http://localhost:8090/beneficio/list
- **WildFly Console:** http://localhost:9990 (admin/Admin@123)

### Comandos úteis

```bash
# Ver logs
docker compose logs -f

# Parar
docker compose down

# Rebuild específico
docker compose up --build spring-backend
```

## 📡 Endpoints

**Base:** `http://localhost:8090/beneficio`

- `GET /list` - Lista todos os benefícios
- `GET /{id}` - Busca por ID
- `POST /transfer` - Transfere valor entre benefícios

## 🗄️ Dados Iniciais

3 benefícios pré-cadastrados:
- Auxílio Alimentação (R$ 450,00)
- Auxílio Transporte (R$ 200,00)
- Plano de Saúde (R$ 300,00)

## 📚 Mais Informações

Cada módulo possui README próprio com detalhes técnicos:
- [beneficio-ejb/README.md](beneficio-ejb/README.md)
- [beneficio-backend/README.md](beneficio-backend/README.md)  
- [front/frontend/README.md](front/frontend/README.md)

---

**Stack:** EJB + Spring Boot + Angular



