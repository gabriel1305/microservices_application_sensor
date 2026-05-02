# ADR 003 - Adoção de Java com Spring Boot

## Status
Aceito

## Contexto

O sistema exige:

- Alta confiabilidade
- Integração com mensageria
- APIs REST robustas
- Facilidade de monitoramento

Alternativas avaliadas:

- Node.js
- Python (FastAPI)
- Go
- Java (Spring Boot)

---

## Decisão

Foi adotado **Java com Spring Boot** como stack principal.

---

## Justificativa

### Vantagens

- Ecossistema maduro
- Forte suporte a microserviços
- Integração nativa com RabbitMQ
- Suporte a observabilidade (Micrometer, Actuator)
- Alto desempenho e estabilidade
- Grande comunidade

---

### Comparativo

#### Node.js

**Vantagens:**
- Rápido desenvolvimento
- Bom para I/O

**Desvantagens:**
- Menor robustez para sistemas críticos
- Gerenciamento de concorrência mais complexo

---

#### Python

**Vantagens:**
- Simples e produtivo

**Desvantagens:**
- Performance inferior
- Não ideal para alta concorrência

---

#### Go

**Vantagens:**
- Alta performance
- Baixo consumo de recursos

**Desvantagens:**
- Ecossistema menos completo
- Menor produtividade para APIs complexas

---

## Limitações

- Consumo maior de memória
- Tempo de startup mais alto
- Verbosidade do código

---

## Mitigações

- Uso de containers (Docker)
- Configuração de JVM otimizada
- Uso de Spring Boot lightweight
- Possibilidade de uso de GraalVM (futuro)

---

## Consequências

- Alta robustez e confiabilidade
- Maior consumo de recursos
- Excelente integração com ferramentas de observabilidade
