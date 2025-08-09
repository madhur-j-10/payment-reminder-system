FROM openjdk:17-jdk
LABEL authors="little"

ADD target/payment-reminder-system.jar payment-reminder-system.jar

ENTRYPOINT ["java", "-jar", "/payment-reminder-system.jar"]
