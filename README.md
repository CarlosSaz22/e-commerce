☁️ Microservices Cloud-Native E-commerce
Arquitectura de microservicios para e-commerce basada en Kubernetes (EKS), con orquestación, mensajería, observabilidad y control de acceso avanzado.

📋 Descripción
🇪🇸 Español
Esta infraestructura despliega una solución de e-commerce utilizando microservicios sobre Amazon Elastic Kubernetes Service (EKS), gestionando órdenes, inventario y notificaciones. Incluye un bróker de eventos con Apache Kafka, almacenamiento distribuido, observabilidad avanzada, malla de servicios con Istio (incluyendo Istio Ingress Gateway) y monitoreo/logging de clase empresarial. La arquitectura aplica conceptos de resiliencia, escalabilidad, seguridad y mantenibilidad modernos en la nube.

📌 Características principales
Despliegue en Amazon EKS: todos los microservicios y herramientas funcionan sobre Kubernetes gestionado.

Gateway y balanceo de carga: Amazon API Gateway, Application Load Balancer e Istio Ingress Gateway gestionan el ingreso seguro y escalable del tráfico externo.

Malla de servicios con Istio: para gestión de tráfico, seguridad, observabilidad y políticas entre servicios.

Estructura de microservicios: Spring Boot con Spring WebFlux (Order, Inventory, Notification), integración con PostgreSQL, MySQL y MongoDB.

Comunicación resiliente: uso de Circuit Breaker con Resilience4j y comunicación asíncrona vía Kafka como event broker.

Integración nativa de observabilidad: OpenTelemetry, Jaeger (tracing), Prometheus y Grafana (métricas), Elastic Stack y Loki (logs), Kiali (visualización mesh).

Escalabilidad y tolerancia a fallos: gracias a Kubernetes, Kafka y patrones resilientes.

Persistencia especializada: PostgreSQL para órdenes, MySQL para inventario y MongoDB para notificaciones.

Diseño API robusto: definición y consumo de servicios a través de OpenAPI siguiendo el enfoque Contract First, garantizando integraciones desacopladas y documentadas desde el inicio.

Arquitectura extensible: fácilmente adaptable para nuevos microservicios o integraciones de terceros.

Control y monitoreo centralizados: dashboards, logging y tracing unificados para análisis de performance y troubleshooting.

🛠️ Tecnologías utilizadas
Java 21

Spring Boot 3 + Spring WebFlux

Kubernetes (Amazon EKS)

Istio Service Mesh (+ Istio Ingress Gateway)

PostgreSQL, MySQL, MongoDB

Apache Kafka (Confluent)

OpenTelemetry, Jaeger

Prometheus, Grafana

Elastic Stack (ELK), Grafana Loki

Kiali

🚀 Flujo de trabajo arquitectónico
Entrada Cliente: Peticiones llegan mediante Amazon API Gateway y Application Load Balancer.

Enrutamiento Seguro: Istio Ingress Gateway recibe todo el tráfico externo y lo enruta internamente en el cluster.

Malla de servicios Istio: controla, asegura y monitorea la comunicación entre microservicios.

Order Service: gestiona pedidos, persiste en PostgreSQL, y publica eventos a Kafka.

Inventory Service: actualiza stock en MySQL y responde a eventos de Órdenes.

Notification Service: consume eventos desde Kafka y almacena notificaciones en MongoDB.

Observabilidad: logs centralizados, métricas y rastreo distribuido de punta a punta.

Administración: Istio Console (Kiali), dashboards de Grafana y Elastic para monitoreo avanzado.

