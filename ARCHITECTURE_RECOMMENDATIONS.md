# Architecture Recommendations - OPRAG Backend

## Vue d'ensemble du projet

OPRAG Backend est une application Spring Boot 3.4.5 pour la gestion de contrôle d'accès véhiculaire, développée en Java 17 avec PostgreSQL, Spring Security JWT, et JasperReports.

## Points positifs de l'architecture actuelle

✅ **Architecture en couches bien organisée** (controller/service/repository)  
✅ **Séparation claire des responsabilités** avec des interfaces API  
✅ **Utilisation appropriée** de Lombok et JPA  
✅ **Validation métier** avec des classes Validator dédiées  
✅ **Gestion d'erreurs centralisée** avec RestExceptionHandler  
✅ **Documentation API** avec SpringDoc OpenAPI  

## Recommandations d'amélioration

### 1. Structure des DTOs

**Problème actuel :** Nommage incorrect des classes DAO
```java
// ❌ Actuel
BadgeDAO, ChauffeurDAO, VehiculeDAO
```

**Solution recommandée :**
```java
// ✅ Recommandé
BadgeDTO, ChauffeurDTO, VehiculeDTO

// Séparer les DTOs par usage
BadgeCreateDTO, BadgeUpdateDTO, BadgeResponseDTO
```

**Actions à réaliser :**
- [ ] Renommer toutes les classes `*DAO` en `*DTO`
- [ ] Créer des DTOs spécialisés pour création, mise à jour, et lecture
- [ ] Implémenter la validation Bean Validation sur les DTOs

### 2. Couche Service

**Problème actuel :** Services sans interfaces dans certains cas

**Solution recommandée :**
```java
// ✅ Structure recommandée
services/
├── BadgeService.java (interface)
├── ChauffeurService.java (interface)
└── impl/
    ├── BadgeServiceImpl.java
    └── ChauffeurServiceImpl.java
```

**Actions à réaliser :**
- [ ] Créer des interfaces pour tous les services
- [ ] Déplacer toutes les implémentations dans le package `impl/`
- [ ] Ajouter l'annotation `@Transactional` sur les méthodes appropriées

### 3. Configuration et Sécurité

**Améliorations recommandées :**

```yaml
# application.yml
spring:
  profiles:
    active: dev
  
jwt:
  secret: ${JWT_SECRET:default-secret}
  expiration: ${JWT_EXPIRATION:86400000}
  
app:
  cors:
    allowed-origins: ${ALLOWED_ORIGINS:http://localhost:3000}
```

**Actions à réaliser :**
- [ ] Externaliser la configuration JWT dans `application.yml`
- [ ] Implémenter des profils Spring (dev, test, prod)
- [ ] Ajouter une gestion des rôles plus granulaire avec `@PreAuthorize`
- [ ] Configurer CORS de manière plus restrictive par environnement

### 4. Tests

**Structure de tests recommandée :**
```
src/test/java/
├── unit/
│   ├── service/
│   └── validator/
├── integration/
│   ├── controller/
│   └── repository/
└── e2e/
```

**Actions à réaliser :**
- [ ] Tests unitaires pour les services avec `@ExtendWith(MockitoExtension.class)`
- [ ] Tests d'intégration pour les controllers avec `@WebMvcTest`
- [ ] Tests de repository avec `@DataJpaTest`
- [ ] Tests de sécurité avec `@WithMockUser`
- [ ] Atteindre 80%+ de couverture de code

### 5. Gestion des dépendances

**Problèmes identifiés :**
```xml
<!-- ❌ Dépendance système problématique -->
<dependency>
    <groupId>net.sf.jasperreports</groupId>
    <artifactId>montserrat-fonts</artifactId>
    <version>1.0</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/libs/montserrat-jar.jar</systemPath>
</dependency>
```

**Actions à réaliser :**
- [ ] Remplacer la dépendance système par un artifact Maven public
- [ ] Mettre à jour ZXing vers 3.5.3
- [ ] Considérer la migration vers Spring Boot 3.3.x
- [ ] Ajouter Spring Boot Actuator pour le monitoring

### 6. Amélioration de l'API

**Implémentations recommandées :**

```java
// Pagination standardisée
@GetMapping
public ResponseEntity<Page<BadgeDTO>> getAllBadges(Pageable pageable) {
    return ResponseEntity.ok(badgeService.findAll(pageable));
}

// Wrapper de réponse standardisé
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private List<String> errors;
}
```

**Actions à réaliser :**
- [ ] Implémenter la pagination avec `Pageable` sur toutes les listes
- [ ] Ajouter MapStruct pour le mapping DTO/Entity
- [ ] Standardiser les réponses API avec `ApiResponse<T>`
- [ ] Implémenter la recherche et le filtrage
- [ ] Ajouter la validation des paramètres avec `@Valid`

### 7. Observabilité et Monitoring

**Configuration recommandée :**
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: when-authorized
```

**Actions à réaliser :**
- [ ] Ajouter Spring Boot Actuator
- [ ] Implémenter des logs structurés avec Logback
- [ ] Ajouter des métriques métier avec Micrometer
- [ ] Configurer des health checks personnalisés
- [ ] Implémenter distributed tracing si nécessaire

### 8. Gestion des erreurs améliorée

**Exemple d'amélioration :**
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleValidation(ValidationException ex) {
        return ApiResponse.error("Erreur de validation", ex.getErrors());
    }
}
```

**Actions à réaliser :**
- [ ] Étendre `RestExceptionHandler` avec plus de types d'erreurs
- [ ] Ajouter des codes d'erreur standardisés
- [ ] Implémenter des messages d'erreur internationalisés
- [ ] Logger les erreurs avec correlation ID

### 9. CI/CD et Déploiement sur Google Cloud

**Architecture de déploiement recommandée sur GCP :**

#### 9.1 Structure des branches Git
```
main/master    ←  Production (Cloud Run)
├── develop    ←  Staging (Cloud Run)
├── feature/*  ←  Développement de fonctionnalités
├── hotfix/*   ←  Corrections urgentes
└── release/*  ←  Préparation des releases
```

#### 9.2 GitHub Actions avec Google Cloud

**Fichier `.github/workflows/ci-cd.yml` :**
```yaml
name: CI/CD Pipeline - Google Cloud

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main, develop ]

env:
  PROJECT_ID: ${{ secrets.GCP_PROJECT_ID }}
  GAR_LOCATION: europe-west1
  REPOSITORY: oprag-backend
  SERVICE: oprag-backend
  REGION: europe-west1

jobs:
  test:
    runs-on: ubuntu-latest
    services:
      postgres:
        image: postgres:15
        env:
          POSTGRES_PASSWORD: testpass
          POSTGRES_DB: oprag_test
        options: >-
          --health-cmd pg_isready
          --health-interval 10s
          --health-timeout 5s
          --health-retries 5
    
    steps:
    - uses: actions/checkout@v4
    
    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'
        
    - name: Cache Maven dependencies
      uses: actions/cache@v3
      with:
        path: ~/.m2
        key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
        
    - name: Run tests
      working-directory: ./gestionControleDAcces
      run: ./mvnw test
      
    - name: Generate test coverage
      working-directory: ./gestionControleDAcces
      run: ./mvnw jacoco:report
      
    - name: Upload coverage to Codecov
      uses: codecov/codecov-action@v3

  build-and-deploy:
    needs: test
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main' || github.ref == 'refs/heads/develop'
    
    permissions:
      contents: read
      id-token: write

    steps:
    - name: Checkout
      uses: actions/checkout@v4
    
    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'

    - name: Google Auth
      id: auth
      uses: google-github-actions/auth@v2
      with:
        credentials_json: '${{ secrets.GCP_SA_KEY }}'

    - name: Docker Auth
      id: docker-auth
      uses: docker/login-action@v3
      with:
        registry: ${{ env.GAR_LOCATION }}-docker.pkg.dev
        username: _json_key
        password: ${{ secrets.GCP_SA_KEY }}

    - name: Build application
      working-directory: ./gestionControleDAcces
      run: ./mvnw clean package -DskipTests

    - name: Build and Push Container
      run: |-
        docker build -t "${{ env.GAR_LOCATION }}-docker.pkg.dev/${{ env.PROJECT_ID }}/${{ env.REPOSITORY }}/${{ env.SERVICE }}:${{ github.sha }}" .
        docker push "${{ env.GAR_LOCATION }}-docker.pkg.dev/${{ env.PROJECT_ID }}/${{ env.REPOSITORY }}/${{ env.SERVICE }}:${{ github.sha }}"

    - name: Deploy to Cloud Run (Staging)
      if: github.ref == 'refs/heads/develop'
      id: deploy-staging
      uses: google-github-actions/deploy-cloudrun@v2
      with:
        service: ${{ env.SERVICE }}-staging
        region: ${{ env.REGION }}
        image: ${{ env.GAR_LOCATION }}-docker.pkg.dev/${{ env.PROJECT_ID }}/${{ env.REPOSITORY }}/${{ env.SERVICE }}:${{ github.sha }}
        env_vars: |
          SPRING_PROFILES_ACTIVE=staging
          SPRING_DATASOURCE_URL=${{ secrets.DB_URL_STAGING }}
          JWT_SECRET=${{ secrets.JWT_SECRET }}

    - name: Deploy to Cloud Run (Production)
      if: github.ref == 'refs/heads/main'
      id: deploy-prod
      uses: google-github-actions/deploy-cloudrun@v2
      with:
        service: ${{ env.SERVICE }}
        region: ${{ env.REGION }}
        image: ${{ env.GAR_LOCATION }}-docker.pkg.dev/${{ env.PROJECT_ID }}/${{ env.REPOSITORY }}/${{ env.SERVICE }}:${{ github.sha }}
        env_vars: |
          SPRING_PROFILES_ACTIVE=prod
          SPRING_DATASOURCE_URL=${{ secrets.DB_URL_PROD }}
          JWT_SECRET=${{ secrets.JWT_SECRET }}

    - name: Show Output
      run: echo ${{ steps.deploy.outputs.url }}
```

#### 9.3 Containerisation pour Cloud Run

**Dockerfile optimisé pour Cloud Run :**
```dockerfile
FROM openjdk:17-jre-slim

LABEL maintainer="OPRAG Team"
LABEL version="1.0"

# Créer utilisateur non-root
RUN groupadd -r oprag && useradd -r -g oprag oprag

# Variables d'environnement Cloud Run
ENV SPRING_PROFILES_ACTIVE=prod
ENV PORT=8080
ENV SERVER_PORT=8080

# Copier l'application
COPY gestionControleDAcces/target/*.jar app.jar

# Permissions
RUN chown oprag:oprag app.jar
USER oprag

# Cloud Run utilise la variable PORT
EXPOSE $PORT

# Health check pour Cloud Run
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:$PORT/actuator/health || exit 1

# JVM optimisée pour containers
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "/app.jar"]
```

#### 9.4 Infrastructure Google Cloud

**Services GCP recommandés :**

1. **Cloud Run** - Déploiement serverless de l'application
2. **Cloud SQL (PostgreSQL)** - Base de données managée
3. **Artifact Registry** - Stockage des images Docker
4. **Cloud Storage** - Stockage des fichiers/rapports
5. **Cloud Logging** - Centralisation des logs
6. **Cloud Monitoring** - Métriques et alertes

**Configuration Terraform pour l'infrastructure :**
```hcl
# terraform/main.tf
provider "google" {
  project = var.project_id
  region  = var.region
}

# Artifact Registry
resource "google_artifact_registry_repository" "oprag_backend" {
  location      = var.region
  repository_id = "oprag-backend"
  description   = "OPRAG Backend Docker images"
  format        = "DOCKER"
}

# Cloud SQL Instance
resource "google_sql_database_instance" "main" {
  name             = "oprag-postgres"
  database_version = "POSTGRES_15"
  region           = var.region
  
  settings {
    tier = "db-g1-small"
    
    backup_configuration {
      enabled = true
      start_time = "03:00"
    }
    
    database_flags {
      name  = "max_connections"
      value = "100"
    }
  }
}

resource "google_sql_database" "oprag_db" {
  name     = "oprag_management"
  instance = google_sql_database_instance.main.name
}

resource "google_sql_user" "oprag_user" {
  name     = "oprag_user"
  instance = google_sql_database_instance.main.name
  password = var.db_password
}

# Cloud Run Services
resource "google_cloud_run_service" "oprag_backend_prod" {
  name     = "oprag-backend"
  location = var.region

  template {
    spec {
      containers {
        image = "${var.region}-docker.pkg.dev/${var.project_id}/oprag-backend/oprag-backend:latest"
        
        env {
          name  = "SPRING_PROFILES_ACTIVE"
          value = "prod"
        }
        
        env {
          name  = "SPRING_DATASOURCE_URL"
          value = "jdbc:postgresql:///${google_sql_database.oprag_db.name}?cloudSqlInstance=${google_sql_database_instance.main.connection_name}&socketFactory=com.google.cloud.sql.postgres.SocketFactory"
        }
        
        resources {
          limits = {
            cpu    = "2000m"
            memory = "2Gi"
          }
        }
        
        ports {
          container_port = 8080
        }
      }
    }
    
    metadata {
      annotations = {
        "autoscaling.knative.dev/minScale" = "1"
        "autoscaling.knative.dev/maxScale" = "10"
        "run.googleapis.com/cloudsql-instances" = google_sql_database_instance.main.connection_name
      }
    }
  }
  
  traffic {
    percent         = 100
    latest_revision = true
  }
}

# IAM pour Cloud Run
resource "google_cloud_run_service_iam_member" "public_access" {
  service  = google_cloud_run_service.oprag_backend_prod.name
  location = google_cloud_run_service.oprag_backend_prod.location
  role     = "roles/run.invoker"
  member   = "allUsers"
}
```

#### 9.5 Configuration pour Google Cloud

**application-prod.yml optimisé pour GCP :**
```yaml
spring:
  datasource:
    # Cloud SQL avec connexion socket factory
    url: jdbc:postgresql:///${DB_NAME:oprag_management}?cloudSqlInstance=${CLOUD_SQL_INSTANCE}&socketFactory=com.google.cloud.sql.postgres.SocketFactory&useSSL=false
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    hikari:
      maximum-pool-size: 10
      minimum-idle: 1
      connection-timeout: 20000
      
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
    
  cloud:
    gcp:
      sql:
        enabled: true
        database-name: ${DB_NAME:oprag_management}
        instance-connection-name: ${CLOUD_SQL_INSTANCE}

logging:
  level:
    root: INFO
    oprag.project.gestionControleDAcces: INFO
  pattern:
    # Format compatible avec Cloud Logging
    console: "%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr([%t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}"

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
      probes:
        enabled: true

server:
  port: ${PORT:8080}
  compression:
    enabled: true
```

#### 9.6 Scripts de déploiement Cloud

**deploy-gcp.sh :**
```bash
#!/bin/bash

set -e

ENVIRONMENT=${1:-staging}
PROJECT_ID=${2:-your-project-id}
REGION=${3:-europe-west1}

echo "🚀 Déploiement de OPRAG Backend sur Google Cloud - ${ENVIRONMENT}"

# Authentification GCP
echo "🔐 Authentification Google Cloud..."
gcloud auth application-default login
gcloud config set project $PROJECT_ID

# Construction et push de l'image
echo "🏗️ Construction de l'image Docker..."
docker build -t $REGION-docker.pkg.dev/$PROJECT_ID/oprag-backend/oprag-backend:latest .
docker push $REGION-docker.pkg.dev/$PROJECT_ID/oprag-backend/oprag-backend:latest

# Déploiement sur Cloud Run
echo "🚀 Déploiement sur Cloud Run..."
if [ "$ENVIRONMENT" == "staging" ]; then
  SERVICE_NAME="oprag-backend-staging"
  DB_INSTANCE="oprag-postgres-staging"
else
  SERVICE_NAME="oprag-backend"
  DB_INSTANCE="oprag-postgres"
fi

gcloud run deploy $SERVICE_NAME \
  --image $REGION-docker.pkg.dev/$PROJECT_ID/oprag-backend/oprag-backend:latest \
  --region $REGION \
  --platform managed \
  --allow-unauthenticated \
  --set-env-vars "SPRING_PROFILES_ACTIVE=$ENVIRONMENT" \
  --set-env-vars "CLOUD_SQL_INSTANCE=$PROJECT_ID:$REGION:$DB_INSTANCE" \
  --set-cloudsql-instances $PROJECT_ID:$REGION:$DB_INSTANCE \
  --memory 2Gi \
  --cpu 2 \
  --min-instances 1 \
  --max-instances 10 \
  --timeout 300

# Obtenir l'URL du service
SERVICE_URL=$(gcloud run services describe $SERVICE_NAME --region $REGION --format 'value(status.url)')
echo "✅ Déploiement terminé! URL: $SERVICE_URL"

# Tests de santé
echo "🧪 Tests de santé..."
sleep 30
curl -f "$SERVICE_URL/actuator/health" || exit 1
echo "✅ Application déployée avec succès!"
```

#### 9.7 Monitoring et Observabilité GCP

**Configuration Cloud Monitoring :**
```yaml
# monitoring-config.yml
resources:
  - name: oprag-backend-cpu-alert
    type: gcp-types/monitoring-v1:projects.alertPolicies
    properties:
      displayName: "OPRAG Backend - High CPU Usage"
      conditions:
        - displayName: "CPU usage above 80%"
          conditionThreshold:
            filter: 'resource.type="cloud_run_revision" resource.label.service_name="oprag-backend"'
            comparison: COMPARISON_GT
            thresholdValue: 0.8
            duration: "300s"
      alertStrategy:
        autoClose: "1800s"
      notificationChannels:
        - $(ref.email-notification.name)

  - name: oprag-backend-error-rate-alert  
    type: gcp-types/monitoring-v1:projects.alertPolicies
    properties:
      displayName: "OPRAG Backend - High Error Rate"
      conditions:
        - displayName: "Error rate above 5%"
          conditionThreshold:
            filter: 'resource.type="cloud_run_revision" resource.label.service_name="oprag-backend"'
            comparison: COMPARISON_GT
            thresholdValue: 0.05
            duration: "300s"
```

**Stratégie de rollback Cloud Run :**
```bash
#!/bin/bash
# rollback-gcp.sh

ENVIRONMENT=${1:-staging}
PREVIOUS_REVISION=${2}
PROJECT_ID=${3:-your-project-id}
REGION=${4:-europe-west1}

if [ "$ENVIRONMENT" == "staging" ]; then
  SERVICE_NAME="oprag-backend-staging"
else
  SERVICE_NAME="oprag-backend"
fi

echo "⏪ Rollback vers la révision ${PREVIOUS_REVISION}"

# Rollback vers la révision précédente
gcloud run services update-traffic $SERVICE_NAME \
  --to-revisions $PREVIOUS_REVISION=100 \
  --region $REGION

# Vérifier le health check
SERVICE_URL=$(gcloud run services describe $SERVICE_NAME --region $REGION --format 'value(status.url)')
echo "🧪 Vérification de la santé de l'application..."
sleep 30
curl -f "$SERVICE_URL/actuator/health" || exit 1

echo "✅ Rollback terminé avec succès!"
```

**Actions à réaliser pour Google Cloud :**
- [ ] Créer un projet Google Cloud Platform
- [ ] Configurer Artifact Registry pour les images Docker
- [ ] Provisionner Cloud SQL (PostgreSQL) pour staging et production
- [ ] Configurer les services Cloud Run (staging et production)
- [ ] Implémenter le pipeline GitHub Actions avec authentification GCP
- [ ] Ajouter les dépendances Spring Cloud GCP au pom.xml
- [ ] Configurer les variables d'environnement et secrets GitHub
- [ ] Mettre en place Cloud Monitoring et alertes
- [ ] Tester les procédures de rollback Cloud Run
- [ ] Configurer les sauvegardes automatiques Cloud SQL
- [ ] Implémenter Infrastructure as Code avec Terraform
- [ ] Documenter les procédures de déploiement GCP

**Variables GitHub Secrets requises :**
- `GCP_PROJECT_ID` - ID du projet Google Cloud
- `GCP_SA_KEY` - Clé JSON du service account
- `DB_URL_STAGING` - URL Cloud SQL staging
- `DB_URL_PROD` - URL Cloud SQL production  
- `JWT_SECRET` - Clé secrète JWT

## Priorités d'implémentation

### Phase 1 - Fondations (Critique)
1. ✅ Correction des nommages DTO
2. ✅ Création des interfaces de service
3. ✅ Configuration par profils
4. ✅ Tests unitaires de base

### Phase 2 - Amélioration API (Important)
1. ✅ Pagination et recherche
2. ✅ Mapping avec MapStruct
3. ✅ Standardisation des réponses
4. ✅ Validation améliorée

### Phase 3 - CI/CD et Déploiement (Critique pour Production)
1. ✅ Configuration Docker et docker-compose
2. ✅ Pipeline GitHub Actions/GitLab CI
3. ✅ Scripts de déploiement automatisés
4. ✅ Monitoring et rollback
5. ✅ Configuration multi-environnements

### Phase 4 - Production Ready (Nice to have)
1. ✅ Observabilité complète
2. ✅ Sécurité renforcée
3. ✅ Performance monitoring
4. ✅ Documentation technique

## Métriques de succès

- **Couverture de tests :** > 80%
- **Temps de build :** < 2 minutes
- **Temps de démarrage :** < 30 secondes
- **Zero dépendances system scope**
- **API response time :** < 200ms P95

## Ressources utiles

- [Spring Boot Best Practices](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Security Architecture](https://spring.io/guides/topicals/spring-security-architecture/)
- [JPA Best Practices](https://thorben-janssen.com/jpa-best-practices/)
- [MapStruct Documentation](https://mapstruct.org/documentation/stable/reference/html/)

---

**Date de création :** 2025-09-13  
**Version :** 1.0  
**Auteur :** Architecture Review Team