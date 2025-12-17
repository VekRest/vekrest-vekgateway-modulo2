# 🧬 Projeto VekRest - VekGateway - Módulo 2

Gateway VekRest: centraliza acessos via Spring Boot com Docker e Maven. **Módulo 2 / Gateway**

> ATENÇÃO: VÁ ATÉ OS REPOSITÓRIOS DAS PARTES DO MÓDULO 2 E SIGA AS INSTRUÇÕES DE EXECUÇÃO DO README DE CADA APLICAÇÃO PARA RODAR A APLICAÇÃO COMPLETA!

## 🧩 PARTES DO MÓDULO 2
| Aplicação      | Descrição                                      | Link                              |
|----------------|------------------------------------------------|-----------------------------------|
| VekGateway     | Gateway (este projeto) - Centraliza o acesso às outras aplicações | Este Repositório |
| VekClient      | Aplicação de CRUD de Pessoa                    | [Repositório VekClient Módulo 1](https://github.com/VekRest/vekrest-vekclient-modulo1) |
| VekSecurity    | Aplicação de Login e Segurança                 | [Repositório VekSecurity Módulo 2.1](https://github.com/VekRest/vekrest-veksecurity-modulo2.1) |

> Este projeto depende das outras duas aplicações (VekClient e VekSecurity) para funcionar corretamente.

> Faça o build no docker das outras aplicações ou utilize as imagens do DockerHub para rodar os containers necessários.

> Por último, suba os containers deste projeto (VekGateway) para completar o ambiente.

---

# 1.✨ Imagem Docker (DockerHub)

> A imagem desta aplicação é atualizada a cada nova tag ou pull request na [branch main](https://github.com/VekRest/vekrest-vekgateway-modulo2/tree/main)

> Link da imagem no DockerHub: [vek03/vekrest-vekgateway:latest](https://hub.docker.com/r/vek03/vekrest-vekgateway)

---

## 1.1 🧩 Containers necessários para rodar a aplicação:

| Container | Imagem | Link                                                                                                                                           | 
|---|---|------------------------------------------------------------------------------------------------------------------------------------------------|
| MongoDB | `mongo:latest` | https://hub.docker.com/_/mongo                                                                                                                 |
| Redis | `redis:latest` | https://hub.docker.com/_/redis                                                                                                                 |
| OpenSearch | `opensearchproject/opensearch:2.4.0` | https://hub.docker.com/layers/opensearchproject/opensearch/2.4.0/images/sha256-c8681472b70d46e7de61fe770d288a972f84b3f122f3c74ca06ea525264b6fd5 |
| Graylog | `graylog/graylog:5.1.5` | https://hub.docker.com/layers/graylog/graylog/5.1.5/images/sha256-3b6967572e88731eacfa661e6d7ca41da3e259bc5eb041e58fb10e4deb823dcb             |
| VekClient | `vek03/vekrest-vekclient:latest` | https://hub.docker.com/r/vek03/vekrest-vekclient                                                                                 |
| VekSecurity | `vek03/vekrest-veksecurity:latest` | https://hub.docker.com/r/vek03/vekrest-veksecurity                                                                               |

---

## 1.2 ⚙ Variáveis de ambiente necessárias para rodar o container:

| Variável                  | Descrição                        | Exemplo                                                                                 |
|---------------------------|----------------------------------|-----------------------------------------------------------------------------------------|
| `SERVER_PORT`             | Porta onde a aplicação irá rodar | `8080`                                                                                  |
| `SECRET_KEY`              | Chave do JWT                     | `vekrest!Afwedfuihosedwfbgri8uoef`                                                      |
| `VEKSECURITY_URI`         | URI do container de VekSecurity  | `http://veksecurity:8081`                                                                                  |
| `VEKCLIENT_URI`           | URI do container de VekClient    | `http://vekclient:8082` |

---

## 1.3 🐳 Como rodar o container

1️⃣ Para baixar a imagem do Docker Hub:
```bash
docker pull vek03/vekrest-vekgateway:latest
```

2️⃣ Para rodar o container localmente:
```bash
docker run -d \
  --name vekgateway \
  -e SERVER_PORT=8080 \
  -e SECRET_KEY=vekrest!Afwedfuihosedwfbgri8uoef \
  -e VEKSECURITY_URI=http://veksecurity:8081 \
  -e VEKCLIENT_URI=http://vekclient:8082 \
  -p 8080:8080 \
  vek03/vekrest-vekgateway:latest
```

3️⃣ Alternativamente, você pode adicionar o serviço no seu docker-compose.yml local, descomentando ou adicionando o seguinte trecho:
```bash
services:
    vekgateway:
    image: vek03/vekrest-vekgateway:latest
    hostname: vekgateway
    container_name: vekgateway
    ports:
      - "8080:8080"
    environment:
      SERVER_PORT: 8080
      SECRET_KEY: "vekrest!Afwedfuihosedwfbgri8uoef"
      VEKSECURITY_URI: http://veksecurity:8081
      VEKCLIENT_URI: http://vekclient:8082
    depends_on:
      mongodb:
        condition: service_healthy
      opensearch:
        condition: service_healthy
      graylog:
        condition: service_started
      redis:
        condition: service_healthy
      veksecurity:
        condition: service_started
      vekclient:
        condition: service_started
    healthcheck:
      test: [ "CMD-SHELL", "curl -f http://localhost:8080/actuator/health || exit 1" ]
      interval: 5s
      timeout: 15s
      retries: 10
      start_period: 30s
```

4️⃣ Depois de adicionar o serviço em docker-compose.yml, suba os containers:
```bash
docker-compose up -d
```

---

## 📦 Instalação e Configuração do Ambiente

### 1️⃣ Clone o projeto na sua máquina e baixe as dependências:
```bash
# Clonar
git clone https://github.com/VekRest/vekrest-vekgateway-modulo2.git

# Acesse a pasta do projeto
cd vekrest-vekgateway-modulo2
````

### 2️⃣ Suba os containers necessários e Rode o projeto na sua IDE de preferência (ou via comando Maven)
```bash
# Suba os containers necessários (MongoDB, Redis, OpenSearch, Graylog)
docker-compose up -d

# Rode o projeto via Maven
```

### 3️⃣ (Opcional) Alternativamente, se quiser rodar via container localmente:
```bash
# Dentro da pasta do projeto:
mvn clean package -DskipTests

# Agora faça deploy no Docker local:
docker build -t vekrest/vekgateway:latest .

# Descomente as últimas linhas do docker-compose.yml (relacionadas ao vekgateway) e rode:
docker-compose up -d
```

> Ou execute o script .bat (executar_tudo.bat) na pasta .commands para automatizar o processo.

> A API Gateway VekGateway fica disponível na porta 8080 do [Localhost](http://localhost:8080) ao rodar localmente via IDE.

### 4️⃣ (Opcional) Caso deseje, pode rodar o SonarQube localmente

```bash
# Após configurar o pom.xml com as informações do Sonar em Properties:
mvn clean install sonar:sonar -Dsonar.token={TOKEN_SONAR}
```

---

## 🧩 Tecnologias Utilizadas

- **Spring Boot** → Framework Back-End
- **Java** → Linguagem de programação
- **Maven** → Build
- **Docker** → Containers e virtualização
- **Docker Hub** → Repositório de imagens Docker
- **Redis** → Cache
- **OpenSearch e Graylog** → Logs da Aplicação
- **Swagger** → Documentação da API
- **SonarQube** → Qualidade
- **Github Actions** → CI/CD automatizado
- **.bat** → Scripts para automatizar processos no Windows

---

## ✅ Qualidade (SonarQube)

> Este projeto tem qualidade analisada pelo SonarQube Cloud. Verifique nos badges!

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-dark.svg)](https://sonarcloud.io/summary/new_code?id=vekgateway)

---

## 📦 Esteira CI/CD Automatizada com Github Actions

> A esteira CI/CD deste projeto é automatizada via Github Actions. A cada tag criada a esteira é disparada.

### Para executar a Esteira pelo trigger:
```bash
# Exemplo: Cria a tag
git tag <version>

# Envia a tag para o repositório remoto
git push origin <version>
```

[![VekGateway CI/CD Workflow](https://github.com/VekRest/vekrest-vekgateway-modulo2/actions/workflows/main.yml/badge.svg)](https://github.com/VekRest/vekrest-vekgateway-modulo2/actions/workflows/main.yml)

---

## Postman Collection

> Link para download da coleção Postman utilizada nos testes da API: [Postman Collection VekRest](https://www.postman.com/aviation-pilot-88658184/workspace/my-workspace/folder/33703402-dad9baf5-9c1b-4010-a4c7-7ace385191fd?action=share&source=copy-link&creator=33703402&ctx=documentation)

> Alternativamente, você pode utilizar o Swagger UI para testar a API:
[Swagger UI VekRest VekGateway Módulo 2](http://localhost:8080/swagger-ui/index.html) (rodando localmente)

---