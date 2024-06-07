# Quarkus3 Monitoring
<div align="left">
      <a href="https://youtu.be/fsav8gDlkyo?si=rk5ZZDazh44qDqZA">
         <img src="https://img.youtube.com/vi/fsav8gDlkyo/0.jpg" style="width:100%;">
      </a>
</div>

This project uses Quarkus, the Supersonic Subatomic Java Framework. The monitoring concept for Quarkus3 utilizes:
* `quarkus-logging-gelf` for sending logs to Kibana
* `quarkus-micrometer-registry-prometheus` for Prometheus metrics
* `quarkus-scheduler` for generating data

The application creates basic data regarding prime number calculations. It generates basic metrics and exposes REST endpoints.

## Start Local Infrastructure

Tested on ```WSL2 + Docker Engine```. For setting up the local infrastructure, `docker-elk` is used with custom adjustments. The default configuration runs Elasticsearch, Kibana, and Logstash, with Prometheus and Grafana added. Please check the configuration for Grafana in `./grafana/provisioning/datasources/provisioned-datasource.yaml` and for Prometheus in `prometheus.yml`. In `prometheus.yml`, it is important to specify the correct targets. Please refer to the README paragraph. A basic license is used.

1. Go to the `docker-elk` folder.
2. For the first time, run `docker-compose up setup`.
3. Run `docker-compose up`.
4. The ELK stack should be started together with Prometheus and Grafana.
5. Go to the root folder.
6. Go to the `app` folder.
7. Run `mvn quarkus:dev`.

This will set up the following components:
* Kibana
    * It will allow visual access to application logs
    * http://localhost:5601
* Prometheus
    * It will allow querying application metrics
    * http://localhost:9090
* Grafana
    * It will allow dashboards creation
    * http://localhost:3003
* Elasticsearch
    * It will allow indexing and saving logs for later visual access via Kibana
    * http://localhost:9200

```shell script
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-logging-gelf</artifactId>
    </dependency>
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-micrometer-registry-prometheus</artifactId>
    </dependency>
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-scheduler</artifactId>
    </dependency>      
```


### Links
1. https://github.com/deviantony/docker-elk
2. https://github.com/sherifabdlnaby/elastdocker
3. https://www.elastic.co/guide/en/elasticsearch/reference/8.3/docker.html#docker-compose-file
4. https://quarkus.io/guides/centralized-log-management
5. https://github.com/alvarolop/quarkus-observability-app
6. https://quarkus.io/guides/getting-started#the-jax-rs-resources
7. https://quarkus.io/blog/micrometer-prometheus-openshift/
8. https://exceptionly.com/2022/01/18/monitoring-quarkus-with-prometheus-and-grafana/
9. https://medium.com/@TimvanBaarsen/how-to-connect-to-the-docker-host-from-inside-a-docker-container-112b4c71bc66
10. https://codeblog.dotsandbrackets.com/scraping-application-metrics-prometheus/
11. https://stackoverflow.com/questions/54397463/getting-error-get-http-localhost9443-metrics-dial-tcp-127-0-0-19443-conne
12. https://stackoverflow.com/questions/24319662/from-inside-of-a-docker-container-how-do-i-connect-to-the-localhost-of-the-mach
13. https://github.com/lwitkowski/quarkus-grafana-dashboard/blob/master/screenshot.png
