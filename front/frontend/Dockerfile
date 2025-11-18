# STAGE 1: Build da aplicação Angular
FROM node:20-alpine AS build

WORKDIR /app

# Copia os arquivos de dependências
COPY package*.json ./

# Instala as dependências
RUN npm ci --legacy-peer-deps

# Copia o código fonte
COPY . .

# Faz o build de produção
RUN npm run build --configuration=production

# STAGE 2: Serve a aplicação com Nginx
FROM nginx:alpine

# Copia os arquivos buildados do stage anterior
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

# Copia configuração customizada do Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expõe a porta 80
EXPOSE 80

# Comando para iniciar o Nginx
CMD ["nginx", "-g", "daemon off;"]


