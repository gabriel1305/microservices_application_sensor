# ADR 002 - Adoção do RabbitMQ como Broker de Mensageria

## Status
Aceito

## Contexto

O sistema requer comunicação assíncrona entre serviços, principalmente para:

- Processamento de eventos de sensores
- Desacoplamento entre ingestão e persistência
- Escalabilidade do processamento

Alternativas avaliadas:

- Apache Kafka
- ActiveMQ
- Amazon SQS
- RabbitMQ

---

## Decisão

Foi adotado o **RabbitMQ** como sistema de mensageria.

---

## Justificativa

### Vantagens

- Simples de configurar e operar
- Baixa latência
- Suporte a múltiplos padrões (pub/sub, routing, fanout)
- Excelente integração com Spring Boot
- Ideal para workloads moderados e filas transacionais

---

### Comparativo

#### Kafka

**Vantagens:**
- Altíssima escalabilidade
- Persistência forte
- Ideal para streaming

**Desvantagens:**
- Complexidade alta
- Overkill para este cenário
- Latência maior

---

#### ActiveMQ

**Vantagens:**
- Tradicional e robusto

**Desvantagens:**
- Menor adoção atual
- Performance inferior ao RabbitMQ

---

#### Amazon SQS

**Vantagens:**
- Totalmente gerenciado

**Desvantagens:**
- Dependência de cloud
- Menor controle
- Latência maior

---

## Limitações

- Não é ideal para altíssimo throughput (como Kafka)
- Persistência limitada dependendo da configuração
- Necessidade de gerenciamento de filas

---

## Mitigações

- Uso de exchange + routing keys para flexibilidade
- Configuração de filas duráveis
- Monitoramento via Prometheus + Grafana
- Possibilidade futura de migração para Kafka se necessário

---

## Indicadores

- Taxa de publicação: 18.3 mensagens/min
- Sistema operando sem perda de mensagens
- Processamento assíncrono eficiente

---

## Consequências

- Sistema desacoplado
- Melhor escalabilidade
- Complexidade adicional de mensageria
