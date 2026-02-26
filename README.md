# Production Optimizer API

## 🎯 Objetivo do Projeto

Esta é a API back-end para o **Teste Prático Full Stack - P&D**. O objetivo é construir uma aplicação para gerenciamento de insumos (matérias-primas) e, principalmente, otimizar a produção industrial, sugerindo o que fabricar para maximizar o lucro com base no estoque disponível.

A aplicação foi desenvolvida seguindo os princípios de Clean Code, SOLID e as melhores práticas de mercado para APIs RESTful com Spring Boot.

## ✨ Tecnologias Utilizadas

| Categoria | Tecnologia |
| :--- | :--- |
| **Linguagem** | Java 21 |
| **Framework** | Spring Boot 3 |
| **Banco de Dados** | PostgreSQL (Produção/Dev) & H2 (Testes) |
| **Persistência** | Spring Data JPA / Hibernate |
| **Build & Dependências**| Apache Maven |
| **Documentação API** | Springdoc (Swagger UI) |
| **Containerização** | Docker |

## ✅ Funcionalidades Implementadas

- [x] **CRUD Completo de Matérias-Primas**: Cadastro, listagem, atualização e exclusão de insumos.
- [x] **CRUD Completo de Produtos**: Cadastro, listagem, atualização e exclusão de produtos, incluindo sua composição de matérias-primas.
- [x] **API de Otimização de Produção**: Endpoint que implementa a lógica de negócio principal:
    - [x] Prioriza a fabricação de produtos de **maior valor de venda**.
    - [x] Resolve conflitos de estoque quando múltiplos produtos dependem da mesma matéria-prima.
    - [x] Retorna uma sugestão clara de produção e o lucro total projetado.
- [x] **Testes Unitários**: Cobertura de teste para a lógica de negócio crítica (serviço de otimização), garantindo a corretude do algoritmo.
- [x] **Validação de Dados**: Uso de Bean Validation para garantir a integridade dos dados de entrada na API.
- [x] **Tratamento de Exceções**: Handler global para fornecer respostas de erro claras e padronizadas.
- [x] **Internacionalização**: A base de código está em inglês, mas a documentação da API e as mensagens de erro estão em português, demonstrando flexibilidade.
- [x] **Containerização**: `Dockerfile` otimizado com *multi-stage build* para criar uma imagem leve e segura para produção.

## 📚 Documentação da API (Swagger)

A API está 100% documentada com Swagger (OpenAPI). Após iniciar a aplicação, a documentação interativa pode ser acessada em:

- **URL:** [http://localhost:8080/api/swagger-ui.html](http://localhost:8080/api/swagger-ui.html)

## 🚀 Como Executar o Projeto

### Pré-requisitos

- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/)
- [Java 21](https://www.oracle.com/java/technologies/downloads/#java21) (Para execução local sem Docker)
- [Maven](https://maven.apache.org/) (Para execução local sem Docker)

### Opção 1: Usando Docker (Recomendado)

Esta é a forma mais simples e rápida de executar o projeto, pois gerencia o ambiente e as dependências de forma isolada.

1.  **Clone o repositório:**
    ```sh
    git clone [URL_DO_SEU_REPOSITORIO]
    cd production-optimizer
    ```

2.  **Construa a imagem Docker:**
    ```sh
    docker build -t production-optimizer .
    ```

3.  **Execute o contêiner:**
    Substitua os placeholders `[..._SUPABASE]` pelas suas credenciais do banco de dados Supabase.

    ```sh
    docker run -p 8080:8080 \
      -e DB_URL="jdbc:postgresql://aws-0-us-west-2.pooler.supabase.com:6543/postgres?sslmode=require&prepareThreshold=0" \
      -e DB_USERNAME="[SEU_USUARIO_SUPABASE]" \
      -e DB_PASSWORD="[SUA_SENHA_SUPABASE]" \
      production-optimizer
    ```

4.  **Acesse a aplicação:**
    - **API Base:** `http://localhost:8080/api`
    - **Swagger UI:** `http://localhost:8080/api/swagger-ui.html`

### Opção 2: Localmente com Maven

1.  **Clone o repositório:**
    ```sh
    git clone [URL_DO_SEU_REPOSITORIO]
    cd production-optimizer
    ```

2.  **Configure as Variáveis de Ambiente:**
    Exporte as variáveis de ambiente com suas credenciais do banco de dados.

    **No Linux/macOS:**
    ```sh
    export DB_URL="jdbc:postgresql://aws-0-us-west-2.pooler.supabase.com:6543/postgres?sslmode=require&prepareThreshold=0"
    export DB_USERNAME="[SEU_USUARIO_SUPABASE]"
    export DB_PASSWORD="[SUA_SENHA_SUPABASE]"
    ```

    **No Windows (PowerShell):**
    ```powershell
    $env:DB_URL="jdbc:postgresql://aws-0-us-west-2.pooler.supabase.com:6543/postgres?sslmode=require&prepareThreshold=0"
    $env:DB_USERNAME="[SEU_USUARIO_SUPABASE]"
    $env:DB_PASSWORD="[SUA_SENHA_SUPABASE]"
    ```

3.  **Execute a aplicação:**
    ```sh
    mvn spring-boot:run
    ```

## 🧪 Como Executar os Testes

Para rodar os testes unitários e de integração, utilize o seguinte comando Maven. Ele usará um banco de dados H2 em memória para garantir um ambiente de teste isolado.

```sh
mvn test
```

## 👨‍💻 Autor

**John Mota**

- [LinkedIn](https://www.linkedin.com/in/john-mota-026044203/)
- [GitHub](https://github.com/John-Mota)
```