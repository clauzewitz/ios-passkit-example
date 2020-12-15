FROM openjdk:8-jdk-alpine

# 배포 디렉토리 생성
RUN mkdir /usr/share/web/

# 작업 디렉토리 설정
WORKDIR /usr/share/web/

# local 의 jar 파일을 docker 의 /usr/share/web/ 디렉토리로 복사
COPY build/libs/ios-passkit-example-0.0.1-SNAPSHOT.jar ./app.jar

## port 설정
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./app.jar"]