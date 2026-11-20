# Notification Microservice

[![CI](https://github.com/MateuSkywalker90/notification-microservice/actions/workflows/ci.yml/badge.svg)](https://github.com/MateuSkywalker90/notification-microservice/actions/workflows/ci.yml)

Listens to credit proposal events on RabbitMQ and sends the customer an SMS through **AWS SNS** — once when the proposal is received, and again when the analysis is concluded (approved, or denied with the reason).

Part of an event-driven credit proposal system — see [proposal-app](https://github.com/MateuSkywalker90/proposal-app) for the full architecture. The credit decision is made by [credit-analysis-microservice](https://github.com/MateuSkywalker90/credit-analysis-microservice).

## Messages sent

| Event | SMS |
|---|---|
| Proposal received | "Dear {name}, your proposal has been received by our team…" |
| Approved | "Dear {name}, your proposal was APPROVED…" |
| Denied by a rule | "Dear {name}, your proposal was DENIED. Our system detected the following cause: {reason}" |
| Denied (no reason) | "Dear {name}, after internal analysis, it was not possible to proceed…" |

## Messaging

| Queue | Bound to exchange |
|---|---|
| `pending-proposal.ms-notification` | `pending-proposal.ex` |
| `concluded-proposal.ms-notification` | `concluded-proposal.ex` |

## Tech stack

Java 17 · Spring Boot 3.4 · Spring AMQP (RabbitMQ) · AWS SDK (SNS) · JUnit 5 · Mockito

## Running

Requires RabbitMQ on `localhost:5672` (the `docker-compose.yaml` in proposal-app starts one), Java 17, and AWS credentials with SNS publish permission, passed as environment variables:

```bash
export ACCESS_KEY=...
export SECRET_KEY=...
./mvnw spring-boot:run
```

Tests (no broker or AWS account needed):

```bash
./mvnw test
```
