# 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 

# 클래스 설계
* HTTPRequest
  * StartLine
    * Method
    * URL
    * QueryString
    * Version
  * Header -> Map
  * Data -> Map

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
* 요청을 한 번만 보내도 여러 개의 요청이 오는 이유는?
  * 서버가 웹 페이지를 구성하는 자원(HTML, CSS, JS, 이미지 등)을 한 번에 보내주지 않기 때문.
  * 첫 번째로 html만 보내고, 브라우저가 html을 분석하여 추가 자원 요청

### 요구사항 2 - get 방식으로 회원가입
* 

### 요구사항 3 - post 방식으로 회원가입
* HTML은 GET, POST 메소드만 지원한다.
* 최근에는 REST API와 AXIOS 기반으로 웹 App을 개발하기 떄문에 PUT, DELETE까지 활용하는 것을 추천

### 요구사항 4 - redirect 방식으로 이동
* 

### 요구사항 5 - cookie
* 

### 요구사항 6 - stylesheet 적용
* 브라우저는 서버로부터 응답을 받은 후 Content-Type 헤더 값을 통해 본문 컨텐츠의 타입을 판별한다.

### heroku 서버에 배포 후
* 