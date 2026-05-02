# 🚀 IoT Temperature Monitoring Platform

Plataforma distribuída para ingestão, processamento e observabilidade de dados de sensores IoT em tempo real, utilizando arquitetura de microserviços orientada a eventos.

---

## 📌 Visão Geral

O sistema simula sensores de temperatura enviando dados continuamente para uma arquitetura baseada em microserviços.

Os dados são:

1. Recebidos por um API Gateway
2. Processados por serviços de ingestão
3. Publicados em um broker de mensageria (RabbitMQ)
4. Consumidos por serviços independentes:
   - Persistência (PostgreSQL)
   - Alertas
5. Monitorados via stack de observabilidade completa (Grafana + Prometheus + Loki)

---

## 🏗️ Arquitetura

### 🔹 Componentes

- **API Gateway**
- **Ingestion Service**
- **Historical Temperature Service**
- **Alert Service**
- **Sensor Simulator**
- **RabbitMQ**
- **PostgreSQL**
- **Prometheus**
- **Grafana**
- **Loki + Promtail**

---

### 🔹 Padrão Arquitetural

- Microserviços
- Event-Driven Architecture
- Comunicação assíncrona via mensageria

📄 ADRs:
- `ADR-001 - Microserviços`
- `ADR-002 - RabbitMQ`
- `ADR-003 - Java + Spring`

---

## 🔄 Fluxo de Dados

```text
Sensor Simulator
      ↓
API Gateway
      ↓
Ingestion Service
      ↓
RabbitMQ (Exchange)
      ↓                           
    Historical                
    Service        
      ↓
   PostgreSQL