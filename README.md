# pipeline-base

Base project for an AI-assisted, spec-driven development pipeline on Spring Boot.
It defines the stack, the quality gates and the rules that any coding agent must follow,
so the same work can be done by different agents and compared on equal terms.

## Stack

- Java 25, Spring Boot 4.1, Maven (wrapper)
- PostgreSQL, Spring Data JPA, Flyway
- Hexagonal architecture, organized by feature
- Spec-driven development with [OpenSpec](https://openspec.dev)

## Prerequisites

- Java 25 and Maven 3.9+ (`sdk env` installs the exact versions in `.sdkmanrc`)
- Docker (local database and Testcontainers)
- Node.js 20.19+ and the OpenSpec CLI, for the analysis phase

## Getting started

```bash
cp .env.example .env          # first time only; adjust the values
docker compose up -d          # local PostgreSQL
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

## Building and checking

```bash
./mvnw spotless:apply         # format the code
./mvnw verify                 # build and run every quality gate
```

`verify` fails if any of these gates fails:

| Gate | Tool |
|---|---|
| Build environment (Java, Maven, duplicated dependencies) | Maven Enforcer |
| Compilation without warnings | javac `-Xlint:all -Werror` |
| Unit, property-based, integration and acceptance tests | JUnit, jqwik, Testcontainers, Cucumber |
| Architecture and coding rules | ArchUnit |
| 80% line and branch coverage per class | JaCoCo |
| 80% mutation score on `domain` and `application` | PIT |
| Formatting | Spotless (palantir-java-format) |
| Static analysis | SpotBugs |

The build also generates a CycloneDX SBOM in `META-INF/sbom/application.cdx.json`.

## Workflow

1. A request is written in `requests/<id>-<slug>.md`.
2. Analysis: an agent creates an OpenSpec change (proposal, specs, design, tasks) and the
   Cucumber feature files, tagged `@pending`.
3. The analysis pull request is reviewed with the [spec gate](docs/gates/spec-gate.md).
4. Implementation: an agent, in a new session, implements the tasks until `verify` passes
   and removes the `@pending` tag.

## For coding agents

Read [`AGENTS.md`](AGENTS.md) before doing anything. OpenSpec rules are in
[`openspec/config.yaml`](openspec/config.yaml).
