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

   * Persistência (PostgreSQL)
5. Monitorados via stack de observabilidade completa

---

## 🏗️ Arquitetura

### 🔹 Componentes

* API Gateway
* Ingestion Service
* Historical Temperature Service
* Sensor Simulator
* RabbitMQ
* PostgreSQL
* Prometheus
* Grafana
* Loki + Promtail

---

### 🔹 Padrão Arquitetural

* Microserviços
* Event-Driven Architecture
* Comunicação assíncrona via mensageria

### 📄 ADRs

* ADR-001 - Microserviços
* ADR-002 - RabbitMQ
* ADR-003 - Java + Spring

---

## 🔄 Fluxo de Dados

```
Sensor Simulator
      ↓
API Gateway
      ↓
Ingestion Service
      ↓
RabbitMQ (Exchange)
      ↓                                                          
Historical Service         
      ↓
PostgreSQL
```

---

## ⚙️ Como executar o projeto

### ✅ Pré-requisitos

* Docker
* Docker Compose

---

### ▶️ Subindo a aplicação

Clone o repositório:

```bash
git clone <seu-repositorio>
cd <seu-repositorio>
```

Suba todos os serviços:

```bash
docker-compose up -d
```

---

### 🔍 Verificando containers

```bash
docker ps
```

Você deverá ver containers como:

* api-gateway
* ingestion-service
* rabbitmq
* postgres
* prometheus
* grafana
* loki

---

## 📊 Monitoramento e Observabilidade

A plataforma possui uma stack completa de observabilidade:

* Prometheus → métricas
* Grafana → dashboards
* Loki → logs
* Promtail → coleta de logs

---

### 📈 Acessando o Grafana

```
http://localhost:3000
```

Credenciais padrão:

* usuário: `admin`
* senha: `admin`

Dashboards-> New dashboard
---

### 📊 Dashboards

Os dashboards são carregados automaticamente via **provisioning**.

Isso significa que:

* Não é necessário importar dashboards manualmente
* Qualquer pessoa que subir o projeto já terá acesso aos painéis
* As configurações estão versionadas no repositório

---

### 🔎 O que você pode visualizar

* Taxa de requisições (throughput)
* Disponibilidade da API
* Latência
* Logs centralizados dos serviços

---

### 📡 Acessando o Prometheus

```
http://localhost:9090
```

Permite consultar métricas diretamente.


## 🧪 Testando o fluxo

Após subir a aplicação:

1. O Sensor Simulator começa a enviar dados automaticamente
2. O Ingestion Service processa os eventos
3. Os dados são enviados para o RabbitMQ
4. O Historical Service persiste no PostgreSQL

---

### ✅ Validação rápida

* Acesse o Grafana e verifique dashboards
* Consulte métricas no Prometheus
* Visualize logs no Loki

---

## 📦 Provisionamento automático

Este projeto utiliza provisionamento automático para observabilidade:

* Dashboards são carregados via arquivos JSON
* Datasources são configurados automaticamente
* Nenhuma configuração manual é necessária

---

## 🎯 Objetivo do Projeto

Demonstrar na prática:

* Arquitetura de microserviços orientada a eventos
* Processamento assíncrono com mensageria
* Observabilidade completa (métricas + logs)
* Boas práticas de versionamento de infraestrutura

---

## 🚀 Possíveis Evoluções

* Implementação de tracing distribuído
* Escalabilidade horizontal dos serviços
* Deploy em Kubernetes
* Alertas automatizados no Grafana
* Pipeline CI/CD

---

## 👨‍💻 Autor

Projeto desenvolvido para fins de estudo, prática de arquitetura e demonstração de habilidades em engenharia de dados e backend.

---
