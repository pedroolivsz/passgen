# PassGen

Gerador de senhas aleatórias e criptograficamente seguras, com interface web instalável como PWA.

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-green?style=flat-square&logo=springboot)
![React](https://img.shields.io/badge/React-19-blue?style=flat-square&logo=react)
![Docker](https://img.shields.io/badge/Docker-ready-blue?style=flat-square&logo=docker)
![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)

## Funcionalidades

- Geração com `SecureRandom` — criptograficamente seguro
- Controle de tamanho (8–128 caracteres)
- Tipos de caractere configuráveis: maiúsculas, minúsculas, números, símbolos
- Exclusão de caracteres ambíguos (`0`, `O`, `l`, `1`)
- Geração de até 20 senhas por requisição
- Medidor de força de senha
- Cópia com um clique
- PWA — instalável no celular e notebook

## Stack

| Camada | Tecnologia |
|---|---|
| Backend | Java 21, Spring Boot 3, SpringDoc OpenAPI |
| Frontend | React 19, Vite, TypeScript |
| Containerização | Docker, Docker Compose |
| Deploy | Render (backend), Vercel (frontend) |

## Rodando localmente

### Pré-requisitos

- Docker e Docker Compose instalados

### Com Docker Compose

```bash
git clone https://github.com/pedroolivsz/passgen.git
cd passgen
docker compose up --build
```

- Backend: `http://localhost:8080`
- Frontend: `http://localhost:5173`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

### Sem Docker

**Backend:**
```bash
cd backend
./mvnw spring-boot:run
```

**Frontend:**
```bash
cd frontend
npm install
npm run dev
```

## API

### `POST /api/v1/passwords/generate`

**Request:**
```json
{
  "length": 16,
  "uppercase": true,
  "lowercase": true,
  "numbers": true,
  "symbols": false,
  "excludeAmbiguous": false,
  "quantity": 3
}
```

**Response:**
```json
{
  "passwords": ["xK9mPqR2nL4vBw7j", "Tz3hNc8aWe5rYu1s", "Qb6dFx0gMp2iJk9l"],
  "passwordLength": 16,
  "generatedAt": "2026-06-10T14:32:10.123Z"
}
```

Documentação interativa disponível em `/swagger-ui.html`.

## Testes

```bash
cd backend
./mvnw test
```

## Deploy

| Serviço | URL |
|---|---|
| Frontend | https://passgen-kraken.vercel.app |
| Backend | https://passgen-api.onrender.com |

## Licença

MIT — veja [LICENSE](LICENSE) para detalhes.