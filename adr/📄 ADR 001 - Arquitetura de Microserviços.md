# 📄 ADR 001 - Adoção de Arquitetura de Microserviços

## Status
Aceito

---

## 📌 Contexto

O sistema foi projetado para processar dados de sensores IoT em tempo real, contemplando:

- Ingestão de dados
- Processamento assíncrono
- Armazenamento histórico
- Geração de alertas

Diante da necessidade de:

- Escalabilidade horizontal
- Baixo acoplamento
- Evolução independente de componentes
- Resiliência a falhas

foram avaliados os seguintes estilos arquiteturais:

- Arquitetura Monolítica
- Arquitetura em Camadas (N-Tier)
- Arquitetura Orientada a Eventos
- Arquitetura de Microserviços

---

## 🏗️ Decisão

Foi adotada a arquitetura de **Microserviços orientada a eventos**, composta pelos seguintes serviços independentes:

- **ingestion-service** → responsável pela entrada de dados  
- **historical-temp-service** → persistência de dados  
- **alert-service** → processamento e geração de alertas  
- **api-gateway** → ponto único de entrada  

A comunicação entre serviços é realizada de forma assíncrona utilizando **RabbitMQ**.

---

## ✅ Justificativa

### Vantagens

- Escalabilidade independente por serviço  
- Deploy isolado  
- Melhor isolamento de falhas  
- Evolução contínua sem impacto global  
- Baixo acoplamento via mensageria  
- Aderência natural ao modelo Event-Driven  

---

### ⚖️ Comparativo com outras arquiteturas

#### Monolítica

**Vantagens:**
- Simplicidade inicial  
- Facilidade de deploy  

**Desvantagens:**
- Alto acoplamento  
- Escalabilidade limitada  
- Risco elevado em deploys  
- Manutenção complexa em larga escala  

---

#### N-Tier (Camadas)

**Vantagens:**
- Separação de responsabilidades  
- Organização do código  

**Desvantagens:**
- Escalabilidade como bloco único  
- Acoplamento estrutural  

---

#### Event-Driven puro (sem microserviços)

**Vantagens:**
- Alta escalabilidade  
- Baixo acoplamento  

**Desvantagens:**
- Complexidade elevada  
- Dificuldade de rastreabilidade sem observabilidade madura  

---

## ⚠️ Limitações da Arquitetura

- Maior complexidade operacional  
- Necessidade de observabilidade avançada  
- Gestão de comunicação distribuída  
- Overhead de rede  

---

## 🛠️ Mitigações

- Observabilidade com **Prometheus + Grafana + Loki**  
- Uso de **RabbitMQ** para desacoplamento  
- Padronização de logs estruturados  
- API Gateway como ponto central de entrada  
- Monitoramento contínuo via SLIs, SLOs e SLAs  

---

## 📊 Indicadores de Confiabilidade

### 🔵 SLIs (Service Level Indicators)

Indicadores coletados via Prometheus:

- **Throughput:** 99.3 req/min  
- **Disponibilidade HTTP:** 100%  
- **Taxa de publicação:** 18.3 msg/min  
- **Latência p95:** 1.26 ms  

---

### 🎯 SLOs (Service Level Objectives)

Objetivos internos de confiabilidade:

- **Disponibilidade HTTP:** ≥ 99.9%  
- **Latência p95:** ≤ 300 ms  
- **Taxa de erro:** ≤ 0.1%  

---

### 📜 SLAs (Service Level Agreements)

Compromissos de serviço definidos com base nos SLOs e capacidade observada do sistema:

#### 🔹 Disponibilidade

- **SLA:** ≥ 99.5% mensal  
- **Downtime máximo:** ~3h 39min/mês  

---

#### 🔹 Latência

- **SLA:** 95% das requisições ≤ 200 ms  

---

#### 🔹 Taxa de Erro

- **SLA:** ≤ 0.5% das requisições  

---

#### 🔹 Processamento de Mensagens

- **SLA:**
  - 99% das mensagens processadas com sucesso  
  - Tempo máximo de processamento ≤ 1 segundo  

---

#### 🔹 Throughput

- **SLA:** Suporte mínimo de 90 req/min sustentados  

---

## 🔎 Observações

- A ausência de erros HTTP 5xx no período analisado resultou em SLI de disponibilidade de 100%  
- O sistema apresenta desempenho significativamente superior ao SLO definido para latência  
- Os SLAs foram definidos de forma conservadora para garantir margem operacional e resiliência a falhas  

---

## 📌 Consequências

### Positivas

- Sistema altamente escalável e resiliente  
- Baixo acoplamento entre componentes  
- Alta observabilidade e capacidade de diagnóstico  
- Base sólida para evolução (ex: Kubernetes, autoscaling)  

---

### Negativas

- Aumento da complexidade operacional  
- Necessidade contínua de monitoramento e tuning  
- Maior custo de infraestrutura e governança  

---

## 🚀 Considerações Finais

A adoção de microserviços, combinada com práticas de observabilidade e definição clara de SLIs, SLOs e SLAs, estabelece uma base sólida para construção de sistemas distribuídos confiáveis.

Essa decisão permite equilibrar:

- Performance  
- Escalabilidade  
- Confiabilidade  
