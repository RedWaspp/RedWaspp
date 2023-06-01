#Ep purge

This project serves CVAS EVAP Purge Service deployed within PAE Core Services.

## Build & Deploy

This project requires **JDK 11** to build and run the application. It also requires **cf** CLI if you want to deploy to PCF. You can visit [Dev Enablement's OnBoarding](http://x.ford.com/dev-onboard) page for links to download these resources or sign-up for a free-trial PCF foundation.

>If your workstation is not already setup to develop with Java 11, then you can refer to [Java 11 Workstation Setup](https://github.ford.com/DevEnablement/pcfdev-guides/blob/master/migrations/Java-11.md#workstation-setup).

## Health Actuator
Health integrations can be added and managed at HealthNut (https://healthnut.ford.com)

The general health endpoint can be found at /actuator/health

Further custom health endpoints can be implemented with the org.springframework.boot.actuate.health.HealthIndicator
interface

Custom endpoints can be enabled or disabled with the following properties
```
management.health.{health_actuator_name}.enabled=true
```

#### Run & Test Locally

To build, run `./gradlew build`
> Note: Windows users do not use the `./` in front of the `gradew` commands. The instructions assume Mac command prompt

To start and run the application, run `./gradlew bootRun`. 

Your local running app is served on port 8080. If you have actuator installed, you can test app using:
- [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health) 
 
If you opted for DB integration (SQL Database + Flyway), you can see your DB after starting the project by using the [H2 Console](http://localhost:8080/h2-console/)
 
Press `^C` (control-C) to stop your app


#### Deploy manually using CF CLI

In order to deploy, you must first build (`./gradlew build`).

To deploy your built application:

```bash
# login into your PCF space
# cf login -a https://[foundation-api-host] ...

# generate manifest based on 'dev-edc1' settings
./gradlew -PcfManifestTarget=dev-edc1 cfManifest

# push generated manifest
cf push -f manifest-generated.yml
```

#### IDE

Your IDE version must support Java 11 &mdash; i.e. Eclipse (>= 2018-09), IntelliJ (>= 2018.2).

**Your must install and enable use of Lombok for your IDE.** Lombok setup instructions can be found [here](https://github.ford.com/DevEnablement/pcfdev-guides/blob/master/base-service/README.md#lombok).

<br/>


## EcoBoost Project Features
This [**EcoBoost Project**](http://x.ford.com/spring-ecoboost) was originally generated with the following features:

- [**Core Features**](https://github.ford.com/DevEnablement/pcfdev-guides/blob/master/base-service/README.md#core-features) &mdash; 
  Spring Boot 2
, Gradle 5
, Java 11
, [Gradle Boost Plugin](https://github.ford.com/DevEnablement/gradle-boost-plugin)
, [Lombok](https://projectlombok.org/)
, Firewall Friendly

- [**Recommended Web Features**](https://github.ford.com/DevEnablement/pcfdev-guides/blob/master/base-service/README.md#recommended-web-features) &mdash; 
Swagger
, Actuator
, Sleuth
, X-Application-Info
, X-Request-Info
, Common Error Handling
, SRE Metrics

- Other Features & Reference Code &mdash; 
[Simple REST Controller](https://github.ford.com/DevEnablement/pcfdev-guides/tree/master/rest-controller)
, [SQL Database + Flyway](https://github.ford.com/DevEnablement/pcfdev-guides/tree/master/db-flyway)
, [Externalized Configuration](https://github.ford.com/DevEnablement/pcfdev-guides/tree/master/config-client)
, [Service Registry](https://github.ford.com/DevEnablement/pcfdev-guides/tree/master/service-registry)

## Dev Enablement Resources
Need to learn more about your EcoBoost project or learn more about additional integrations available for your Spring Boot app? See our Dev Guides, Community Forum, and other resources in our Dev Services portal at [http://devservices.ford.com](http://devservices.ford.com).


## Contact Us
Need to notify us of a bug, have issues, new feature request or simply want to brag? Join the /d/c/s Community Channels!

- [/Dev/Central/Station Slack](https://app.slack.com/client/T5V3ZFCD6/C9L83E6DQ)
- [/Dev/Central/Station Webex Teams](https://www.webexteams.ford.com/space?r=fz8y)
