# Sistema de Transferência de Benefícios

Sistema web para gerenciamento e transferência de valores entre diferentes tipos de benefícios.

## 🛠 Tecnologias

**Frontend:**
- Angular 18 (Standalone Components)
- TypeScript
- SCSS
- RxJS

**Backend:**
- Spring Boot (Java)
- REST API

**Infraestrutura:**
- Docker
- Nginx

## 📋 Requisitos

- Node.js 18+
- Docker Desktop
- Backend rodando na porta 8090

## 🔄 Fluxo e Comunicação

```
┌─────────────┐         HTTP          ┌─────────────┐
│   Angular   │ ◄──────────────────► │ Spring Boot │
│  (porta 80) │    REST API JSON     │ (porta 8090)│
└─────────────┘                       └─────────────┘
      │
      │ Servido por
      ▼
┌─────────────┐
│    Nginx    │
│  Container  │
└─────────────┘
```

### Endpoints da API:
- `GET /beneficio/list` - Lista todos os benefícios
- `POST /beneficio/transfer` - Realiza transferência

### Fluxo da aplicação:
1. Usuário acessa a interface Angular
2. Sistema carrega lista de benefícios via API
3. Usuário seleciona origem e destino
4. Sistema valida e envia requisição de transferência
5. Backend processa e retorna resultado
6. Interface atualiza dados e exibe notificação

## 🚀 Executando com Docker

### Build da imagem:
```bash
cd frontend
docker build -t frontend-beneficio .
```

### Executar o container:
```bash
docker run -d -p 4200:80 --name app-frontend frontend-beneficio
```

### Acessar:
```
http://localhost:4200
```

### Comandos úteis:
```bash
# Ver status
docker ps

# Parar
docker stop app-frontend

# Remover
docker rm app-frontend

# Ver logs
docker logs app-frontend
```

## ⚙️ Desenvolvimento Local

```bash
# Instalar dependências
npm install

# Executar em modo dev
npm start

# Acessar
http://localhost:4200
```


## 🐳 Estrutura Docker

O projeto utiliza **multi-stage build**:

1. **Stage 1**: Compila a aplicação Angular com Node.js
2. **Stage 2**: Serve os arquivos estáticos com Nginx

**Resultado**: Imagem final leve (~50MB) apenas com Nginx e arquivos buildados.


## 📁 Arquivos Docker

- `Dockerfile` - Instruções de build da imagem
- `nginx.conf` - Configuração do servidor web
- `.dockerignore` - Arquivos ignorados no build

---


