# ForumHub API

## 📋 Descrição
O **ForumHub API** é um sistema de backend desenvolvido para gerenciar tópicos e autenticação em um fórum. Ele oferece funcionalidades como:

- Autenticação baseada em JWT.
- Gerenciamento de usuários e tópicos.
- Tratamento robusto de erros.
- Segurança configurada com Spring Security.

---

## 🚀 Funcionalidades

### **Autenticação e Autorização**
- Login de usuários com autenticação baseada em JWT.
- Controle de acesso configurado por endpoint e método HTTP.

### **Gerenciamento de Tópicos**
- Criar, listar, atualizar e excluir tópicos.
- Suporte a ordenação e validação para evitar duplicidade.

### **Tratamento de Erros**
- Manipulação personalizada de exceções:
    - Erros de autenticação.
    - Validações.
    - Recursos não encontrados.

### **Configuração de Segurança**
- Criptografia de senhas usando BCrypt.
- Integração com Spring Security para segurança nos endpoints.

---

## 🛠️ Tecnologias Utilizadas
- **Java** (versão 17+)
- **Spring Boot** (versão 3.0+)
- **Spring Security**
- **Spring Data JPA**
- **JWT** (JSON Web Token)
- **MySQL** para persistência.
- **Lombok** para simplificação do código.
- **Flyway** para migração de banco de dados.

---

## ⚙️ Configuração do Projeto

### 1. Pré-requisitos
- Java 17 ou superior.
- Maven (versão 3.6+).
- MySQL (ou outro banco de dados relacional).

### 2. Configuração do Banco de Dados
Altere as propriedades no arquivo `application.properties` para conectar ao banco de dados:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forum_api
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
spring.flyway.enabled=true
```

### 3. Configuração do JWT
Defina os valores de segredo e tempo de expiração do token no arquivo `application.properties`:

```properties
jwt.secret=SUA_CHAVE_SECRETA
jwt.expiration=86400000 # 1 dia em milissegundos
```

### 4. Instalação
Clone o repositório e compile o projeto:

```bash
git clone https://github.com/seuprojeto/forumhub-api.git
cd forumhub-api
mvn clean install
````
Execute o projeto:

```bash
mvn spring-boot:run
```

---

## 📚 Endpoints da API

### **Autenticação**
- **POST /login**  
  Requer corpo JSON:
  ```json
  {
    "email": "usuario@exemplo.com",
    "senha": "senha123"
  }
  ```
  Retorna:
  ```json
  {
    "token": "jwt-gerado",
    "tipo": "Bearer"
  }
  ```

### **Tópicos**

#### **1. Listar todos os tópicos**
- **GET /api/topicos**  
  Retorna a lista de todos os tópicos cadastrados.  
  **Acesso:** Público.  
  **Resposta de exemplo:**
  ```json
  [
    {
      "id": 1,
      "titulo": "Primeiro Tópico",
      "mensagem": "Mensagem inicial do tópico",
      "autor": "João",
      "curso": "Java Básico"
    },
    {
      "id": 2,
      "titulo": "Segundo Tópico",
      "mensagem": "Discussão sobre Spring Boot",
      "autor": "Maria",
      "curso": "Spring Framework"
    }
  ]
  ```

#### **2. Criar um novo tópico**
- **POST /api/topicos**  
  Cria um novo tópico no fórum.  
  **Requer:** Token JWT válido no cabeçalho e corpo JSON no seguinte formato:
  ```json
  {
    "titulo": "Título do tópico",
    "mensagem": "Mensagem inicial",
    "autor": "Autor do tópico",
    "curso": "Curso relacionado"
  }
  ```
  **Resposta de exemplo (201 Created):**
  ```json
  {
    "id": 3,
    "titulo": "Título do tópico",
    "mensagem": "Mensagem inicial",
    "autor": "Autor do tópico",
    "curso": "Curso relacionado",
    "dataCriacao": "2025-01-21T10:30:00Z",
    "status" : "PENDENTE"
  }
  ```

#### **3. Buscar um tópico por ID**
- **GET /api/topicos/{id}**  
  Retorna os detalhes de um tópico específico.  
  **Requer:** Token JWT válido no cabeçalho.  
  **Resposta de exemplo:**
  ```json
  {
    "id": 1,
    "titulo": "Primeiro Tópico",
    "mensagem": "Mensagem inicial do tópico",
    "autor": "João",
    "curso": "Java Básico",
    "dataCriacao": "2025-01-20T14:00:00Z",
    "status" : "PENDENTE"
  }
  ```

#### **4. Atualizar um tópico existente**
- **PUT /api/topicos/{id}**  
  Atualiza os dados de um tópico específico.  
  **Requer:** Token JWT válido no cabeçalho e corpo JSON com os campos a serem atualizados:
  ```json
  {
    "titulo": "Título atualizado",
    "mensagem": "Mensagem atualizada"
  }
  ```
  **Resposta de exemplo (200 OK):**
  ```json
  {
    "id": 1,
    "titulo": "Título atualizado",
    "mensagem": "Mensagem atualizada",
    "autor": "João",
    "curso": "Java Básico",
    "dataCriacao": "2025-01-20T14:00:00Z"
  }
  ```

#### **5. Excluir um tópico**
- **DELETE /api/topicos/{id}**  
  Remove um tópico pelo ID.  
  **Requer:** Token JWT válido no cabeçalho.  
  **Resposta de exemplo (204 No Content):**  
  Nenhum conteúdo retornado.


## 🔐 Segurança

### **Autenticação JWT**
- Os tokens são gerados durante o login e devem ser incluídos no cabeçalho `Authorization` em cada requisição autenticada.  
  Formato do cabeçalho:
  ```makefile
  Authorization: Bearer <token>
  ```

### **Roles de Usuário**
- Cada usuário possui um papel definido no banco de dados (ex.: ADMIN, USER).
- O acesso a determinados recursos é configurado com base em roles.

---

## ⚠️ Tratamento de Erros

- **401 Unauthorized:** Falha de autenticação (token inválido ou ausente).
- **403 Forbidden:** Acesso negado.
- **404 Not Found:** Recurso não encontrado.
- **400 Bad Request:** Erros de validação de dados.
- **500 Internal Server Error:** Erros inesperados no servidor.

---

## 🌟 Contribuições

Contribuições são bem-vindas!  
Siga os passos abaixo:

1. Faça um fork do repositório.
2. Crie uma branch para sua feature:
   ```bash
   git checkout -b feature/nova-feature
   ```
3. Faça o commit das alterações:
   ```bash
   git commit -m "Adiciona nova feature"
   ```
4. Envie as alterações:
   ```bash
   git push origin feature/nova-feature
   ```
5. Abra um Pull Request.

---

## 📝 Licença
Este projeto está sob a licença **MIT**. Consulte o arquivo `LICENSE` para mais detalhes.

---

## 📧 Contato
Se tiver dúvidas ou sugestões, entre em contato pelo e-mail:  
**gustavolrsc@gmail.com**


