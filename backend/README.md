# Installation
아래의 명령어를 실행하여 Docker Image 를 설치한다.
```
$ cd docker
$ docker-compose up -d
```

# docker
아래의 명령어를 실행하여 Docker Image 를 생성 및 실행한다.
## 생성
```
$ docker build --tag ios-passkit-example:0.0.1 .
```
## 실행
```
$ docker run -d -p 8080:8080 --name ios-passkit-example ios-passkit-example/:0.0.1
```

# Document
[Wallet 개발자 가이드](https://developer.apple.com/library/archive/documentation/UserExperience/Conceptual/PassKit_PG/index.html)  
[PassKit Package Format 참고문서](https://developer.apple.com/library/archive/documentation/UserExperience/Reference/PassKit_Bundle/Chapters/Introduction.html)  
[jpasskit 참고문서](https://github.com/drallgood/jpasskit)

# Issue
## jpasskit 의 최신버전
jpasskit 의 최신버전은 `0.2.0` 버전으로 gadle 및 maven repository 에 표기되어 있으나 실제로 받아온 버전은 `0.1.1` 으로 차이가 있다.  
이에 [공식 문서](https://github.com/drallgood/jpasskit) 에 게재된 `0.2.0` 버전의 가이드는 참조만 하되 실제 개발은 라이브러리를 디컴파일하여 개발하여야한다.

## 패스의 자동 그룹화
Event Ticket 및 Boarding Pass 가 아닌 다른 타입의 패스인 경우 기본적으로 공통 그룹으로 묶이게 된다.
> Event Ticket 및 Boarding Pass 는 `groupingIdentifier` 의 값으로 그룹으로 묶을 수 있다.

이를 방지하기 위해서는 패스 생성 시에 필요한 Pass Type Identifier 를 달리 생성하여 설정하면 해결된다.  
[관련링크](https://stackoverflow.com/questions/13057307/passbook-passurl-not-opening-the-right-pass-when-passes-are-grouped)
