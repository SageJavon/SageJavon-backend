# 使用 OpenJDK 8 的官方镜像作为基础镜像
FROM openjdk:8

ARG WORKDIR=/opt/run

WORKDIR ${WORKDIR}
COPY ./target/sage-javon-0.0.1-SNAPSHOT.jar sage-javon.jar

# 暴露容器的8080端口
EXPOSE 8080

ENV LANG=zh_CN.UTF-8
ENV LANGUAGE=zh_CN.UTF-8
ENV LC_ALL=zh_CN.UTF-8
ENV TZ=Asia/Shanghai
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT java -jar sage-javon.jar