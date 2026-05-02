# ADR 001 - Adoção de Arquitetura de Microserviços

## Status
Aceito

## Contexto

O sistema foi projetado para processar dados de sensores IoT em tempo real, envolvendo ingestão, processamento, armazenamento e geração de alertas.

A necessidade de escalabilidade, desacoplamento e evolução independente das funcionalidades levou à avaliação de diferentes estilos arquiteturais:

- Arquitetura Monolítica
- Arquitetura em Camadas (N-Tier)
- Arquitetura Orientada a Eventos
- Arquitetura de Microserviços

---

## Decisão

Foi adotada a arquitetura de **Microserviços**, com os seguintes serviços independentes:

- ingestion-service
- historical-temp-service
- alert-service
- api-gateway

---

## Justificativa

### Vantagens

- Escalabilidade independente por serviço
- Deploy isolado
- Melhor isolamento de falhas
- Facilidade de manutenção e evolução
- Aderência natural a arquitetura orientada a eventos (RabbitMQ)

---

### Comparativo com outras arquiteturas

#### Monolítica

**Vantagens:**
- Simples de desenvolver
- Fácil deploy inicial

**Desvantagens:**
- Escalabilidade limitada
- Alto acoplamento
- Deploys arriscados
- Difícil manutenção em larga escala

---

#### N-Tier (Camadas)

**Vantagens:**
- Organização de código
- Separação de responsabilidades

**Desvantagens:**
- Ainda fortemente acoplada
- Escala como bloco único

---

#### Event-Driven puro (sem microserviços)

**Vantagens:**
- Alta escalabilidade
- Baixo acoplamento

**Desvantagens:**
- Complexidade alta
- Difícil rastreabilidade sem observabilidade

---

## Limitações da Arquitetura

- Complexidade operacional
- Necessidade de observabilidade (logs, métricas, tracing)
- Gestão de comunicação entre serviços
- Overhead de rede

---

## Mitigações

- Uso de Grafana + Loki + Prometheus para observabilidade
- Uso de RabbitMQ para desacoplamento
- Padronização de logs estruturados
- Uso de API Gateway para centralização de entrada

---

## Indicadores (SLI, SLO, SLA)

### SLIs medidos

- Throughput: 99.3 req/min
- Disponibilidade HTTP: 100%
- Taxa de publicação: 18.3 msg/min
- Latência p95: 1.26 ms

---

### SLOs definidos

- Disponibilidade HTTP: ≥ 99.9%
- Latência p95: ≤ 300 ms
- Taxa de erro: ≤ 0.1%

---

### Observações

- SLO de disponibilidade retornou "no data", indicando ausência de erros 5xx no período analisado
- SLO de latência apresentou valor acima do esperado (0.571), indicando necessidade de otimização

---

## Consequências

- Sistema altamente escalável e resiliente
- Maior complexidade operacional
- Necessidade contínua de monitoramento e ajustes
