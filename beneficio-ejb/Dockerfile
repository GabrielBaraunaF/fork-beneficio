FROM quay.io/wildfly/wildfly:33.0.2.Final-jdk17
# (recomendo subir para a última versão 33.x ou pelo menos 32+ — versões 30.x têm alguns bugs conhecidos no EJB remoting + Elytron)

LABEL maintainer="Beneficio EJB Application"
LABEL description="Aplicação EJB de Benefícios rodando no WildFly"

ENV JBOSS_HOME=/opt/jboss/wildfly
ENV DEPLOYMENT_DIR=${JBOSS_HOME}/standalone/deployments

# 1. Usuário de gerenciamento (console/admin)
RUN ${JBOSS_HOME}/bin/add-user.sh admin Admin@123 --silent

# 2. Usuário de aplicação (necessário para EJB remoto + remoting)
#    O grupo "guest" não é suficiente. Tem que estar no grupo que tenha permissão EJB
RUN ${JBOSS_HOME}/bin/add-user.sh -a -u ejbuser -p ejbpass123 -g ApplicationUsers --silent

# 3. Copiar configuração personalizada do standalone (OBRIGATÓRIO para acesso remoto confiável)
COPY standalone.xml ${JBOSS_HOME}/standalone/configuration/standalone.xml

# 4. Copiar o JAR da aplicação
COPY target/beneficio-ejb-0.0.1-SNAPSHOT.jar ${DEPLOYMENT_DIR}/

# 5. Copiar datasource (se ainda quiser usar .xml em vez de CLI/module)
COPY src/main/resources/datasources/beneficio-h2-ds.xml ${DEPLOYMENT_DIR}/

# 6. Forçar deploy imediato
RUN touch ${DEPLOYMENT_DIR}/beneficio-ejb-0.0.1-SNAPSHOT.jar.dodeploy && \
    touch ${DEPLOYMENT_DIR}/beneficio-h2-ds.xml.dodeploy

# Portas
EXPOSE 8080 9990 8443

# 7. Comando correto e suficiente (não precisa mais do -b 0.0.0.0 se o standalone.xml tiver <any-address/>)
CMD ["/opt/jboss/wildfly/bin/standalone.sh", \
     "-bmanagement", "0.0.0.0", \
     "-c", "standalone.xml"]