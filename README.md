# RaacSystem API

API REST para gestão integrada de operações médicas e faturamento de planos de saúde. O projeto centraliza autenticação multi-inquilino, cadastro de pacientes e médicos, prontuários, consultas, autorizações de procedimentos, guias e lotes TISS.

## Funcionalidades

- Autenticação JWT com suporte a `access token` e `refresh token`
- Isolamento por tenant por meio do header `X-Tenant-ID`
- Cadastro de pacientes, médicos, convênios e configurações de operadora
- Gestão de consultas e prontuários
- Solicitação assíncrona de autorizações
- Criação e validação de guias e lotes de faturamento TISS
- Integrações configuráveis com adaptadores TISS
- Notificações em tempo real por WebSocket/STOMP
- Armazenamento de arquivos compatível com S3/MinIO
- Documentação interativa da API com SpringDoc OpenAPI

## Stack

- Java 21
- Spring Boot 3.5.11
- Spring Web, Security, Validation e WebSocket
- Spring Data JPA e PostgreSQL 16
- Flyway
- Redis 7
- JWT/JJWt
- AWS SDK S3 / MinIO
- MapStruct e Lombok
- TISS 3.05.00
- Maven
- Docker e Docker Compose

## Arquitetura

```text
src/main/
├── java/br/com/raac_system/
│   ├── config/       Configurações de segurança, Redis, S3, OpenAPI e WebSocket
│   ├── controller/   Endpoints REST da aplicação
│   ├── dto/          Objetos de entrada e saída da API
│   ├── entity/       Entidades JPA e enums de domínio
│   ├── exception/    Tratamento global e exceções de negócio
│   ├── integration/  Portas e adaptadores TISS
│   ├── mapper/       Conversão entre entidades e DTOs
│   ├── repository/   Repositórios Spring Data JPA
│   ├── security/     JWT, filtros, tenant context e controle de acesso
│   ├── service/      Regras de negócio
│   ├── task/         Execução assíncrona e agendada
│   └── util/         Constantes e utilitários
└── resources/
    ├── application.yml         Configuração da aplicação
    ├── db/migration/           Migrações Flyway
    └── tiss/xsd/               XSD da especificação TISS
```

A aplicação segue uma divisão em camadas: os `controllers` recebem as requisições, os `services` aplicam as regras de negócio, os `repositories` persistem as entidades e os `mappers` convertem DTOs. Recursos multi-tenant são resolvidos pelo `TenantFilter` e pelo `TenantContext` a partir do header `X-Tenant-ID`.

O fluxo de autorização cria um registro, dispara uma tarefa assíncrona e atualiza o status por meio dos adaptadores TISS. Os lotes de faturamento são validados antes do envio e podem gerar eventos para o canal WebSocket.

## Pré-requisitos

- Docker
- Docker Compose

Para executar sem containers:

- JDK 21
- Maven 3.9+
- PostgreSQL 16
- Redis 7
- MinIO ou outro endpoint compatível com S3

## Executando com Docker Compose

Na raiz do projeto:

```bash
docker compose up --build
```

A API ficará disponível em:

- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- PostgreSQL: `localhost:5432`
- Redis: `localhost:6379`
- MinIO API: `http://localhost:7000`
- MinIO Console: `http://localhost:7001`
- MailHog UI: `http://localhost:8025`

Para encerrar os containers:

```bash
docker compose down
```

Para remover também os volumes persistentes:

```bash
docker compose down -v
```

## Executando localmente

Inicie PostgreSQL e Redis e defina as variáveis conforme necessário:

```bash
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=raac_system
export DB_USER=postgres
export DB_PASS=postgres

export REDIS_HOST=localhost
export REDIS_PORT=6379

export S3_ENDPOINT=http://localhost:9000
export S3_REGION=us-east-1
export S3_BUCKET=raac-system-bucket
export S3_ACCESS_KEY=minioadmin
export S3_SECRET_KEY=minioadmin

export RAAC_ENCRYPTION_KEY=12345678901234567890123456789012

mvn spring-boot:run
```

Os scripts Flyway em `src/main/resources/db/migration` criam e populam a estrutura inicial do banco.

## Testes

```bash
mvn test
```

O projeto inclui dependências de Spring Boot Test, Spring Security Test e Testcontainers para PostgreSQL.

## Endpoints principais

| Método | Caminho | Descrição |
|---|---|---|
| `POST` | `/api/v1/auth/login` | Autenticação |
| `POST` | `/api/v1/auth/logout` | Encerramento da sessão |
| `POST` | `/api/v1/authorizations` | Solicitar autorização |
| `GET` | `/api/v1/authorizations/{id}/status` | Consultar autorização |
| `POST` | `/api/v1/billing/guides` | Criar guia de faturamento |
| `POST` | `/api/v1/billing/batches` | Criar lote de faturamento |
| `GET` | `/api/v1/billing/batches/{id}/validate` | Validar lote |
| `POST` | `/api/v1/billing/batches/{id}/submit` | Enviar lote |
| `GET` | `/ws` | Endpoint SockJS/STOMP |

A maioria dos recursos requer autenticação JWT e o header `X-Tenant-ID`. A documentação completa pode ser consultada no Swagger UI.

Exemplo de autenticação:

```http
POST /api/v1/auth/login
X-Tenant-ID: <tenant-id>
Content-Type: application/json

{
  "tenantId": "<tenant-id>",
  "email": "usuario@example.com",
  "password": "senha"
}
```

Nas requisições autenticadas:

```http
Authorization: Bearer <access-token>
X-Tenant-ID: <tenant-id>
```

## Configuração

As principais configurações podem ser fornecidas por variáveis de ambiente:

| Variável | Padrão | Uso |
|---|---|---|
| `DB_HOST` | `localhost` | Host PostgreSQL |
| `DB_PORT` | `5432` | Porta PostgreSQL |
| `DB_NAME` | `raac_system` | Banco PostgreSQL |
| `DB_USER` | `postgres` | Usuário PostgreSQL |
| `DB_PASS` | `postgres` | Senha PostgreSQL |
| `REDIS_HOST` | `localhost` | Host Redis |
| `REDIS_PORT` | `6379` | Porta Redis |
| `MAIL_HOST` | `localhost` | Host SMTP |
| `MAIL_PORT` | `1025` | Porta SMTP |
| `S3_ENDPOINT` | `http://localhost:9000` | Endpoint S3/MinIO |
| `S3_REGION` | `us-east-1` | Região S3 |
| `S3_BUCKET` | `raac-system-bucket` | Bucket padrão |
| `S3_ACCESS_KEY` | `minioadmin` | Credencial S3 |
| `S3_SECRET_KEY` | `minioadmin` | Credencial S3 |
| `RAAC_ENCRYPTION_KEY` | chave definida no projeto | Chave de criptografia |

A configuração TISS usa o cenário `ALWAYS_APPROVE` por padrão. Cenários disponíveis incluem `ALWAYS_APPROVE`, `ALWAYS_DENY`, `PENDING`, `TIMEOUT`, `EXPIRED_CARD` e `INVALID_CRED`.

## Segurança

Antes de colocar a aplicação em produção:

- substitua todas as credenciais padrão
- forneça uma chave de criptografia própria com o tamanho adequado
- configure CORS para origens explícitas
- proteja a API do Swagger ou desabilite-a no ambiente produtivo
- configure armazenamento seguro para chaves e segredos
- revise as regras de autorização por perfil e tenant
