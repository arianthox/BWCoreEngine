# BWCoreEngine

## Overview

Legacy orchestration service that also hosts Eureka service discovery. Historically coupled to a predictor/lottery processing flow.

## Scope in BrainWaves

- Out-of-scope for brainwave-only target core
- Retained temporarily for compatibility where required

## Tech Stack

- Java 11
- Spring Boot 2.2.x
- Spring Cloud (Eureka, Feign)
- Kafka
- Gradle
- Docker / docker-compose

## Build

```bash
./gradlew clean build
```

## Run

```bash
./gradlew bootRun
```

```bash
docker-compose up --build
```

## Key Configuration / Integration

- Config file: `src/main/resources/application.yml`
- Important keys:
  - `spring.kafka.bootstrap-servers`
  - `eureka.client.service-url.defaultZone`
  - `draw.url`
- Cross-repo dependency: `:commons` via `settings.gradle` (legacy path expectation: `../Commons`)

## Status / Notes

- Recommended long-term path: separate/archive from BrainWaves core domain.