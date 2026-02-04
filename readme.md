# Maven Hello World (DevOps Exercise)

This repository contains a minimal Java/Maven “Hello World” application and a CI/CD pipeline that:

- Builds and tests the app with Maven (tests run in CI)
- Packages a JAR artifact
- Builds a Docker image (multi-stage) that runs as a non-root user
- Tags the Docker image with the computed JAR version
- Pushes the image to Docker Hub (on branch pushes)
- Pulls and runs the image for a smoke test (on branch pushes)
- Provides a Helm chart to run the app in Kubernetes as a `Job`

## Application

- **Language**: Java
- **Build tool**: Maven
- **Main class**: `com.myapp.App`
- **Output**: prints `Hello World! - Dmitri Kachka`

## Build locally

From the repo root:

```bash
mvn clean test package
```

Run the app from the built JAR:

```bash
java -jar target/myapp-*.jar
```

## Docker

The image is built using a multi-stage `Dockerfile` and runs as a non-root user.

Example local build:

```bash
docker build -t dmitri-hello-world:local ./myapp
docker run --rm dmitri-hello-world:local
```

## CI/CD (GitHub Actions)

Workflow file: `.github/workflows/ci-cd.yml`

What it does:

- Reads the version from `myapp/pom.xml`
- Increments the patch version (e.g. `1.0.0` -> `1.0.1`) for the build
- Builds/tests/packages and uploads the JAR as an artifact
- Builds a Docker image tagged with the computed version
- On `push` events to `master`:
  - Logs in to Docker Hub
  - Pushes the image
  - Pulls it back and runs it as a smoke test

Required GitHub repository secrets:

- `DOCKER_HUB_USERNAME`
- `DOCKER_HUB_TOKEN`

Docker image tags produced:

- `dmitrik2026/dmitri-hello-world:<version>`
- `dmitrik2026/dmitri-hello-world:latest`

## Helm (Kubernetes)

The Helm chart runs the container as a Kubernetes `Job` (the app prints and exits).

Install:

```bash
helm install maven-hello-world ./helm-chart/maven-hello-world \
  --set image.repository=dmitrik2026/dmitri-hello-world \
  --set image.tag=1.0.0
```

View Job logs:

```bash
kubectl logs job/maven-hello-world
```
