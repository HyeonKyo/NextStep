# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 구현하며 알게된 것
* 요청을 한 번만 보내도 여러 개의 요청이 오는 이유는?
  * 서버가 웹 페이지를 구성하는 자원(HTML, CSS, JS, 이미지 등)을 한 번에 보내주지 않기 때문.
  * 첫 번째로 html만 보내고, 브라우저가 html을 분석하여 추가 자원 요청
* HTML은 GET, POST 메소드만 지원한다.
  * 최근에는 REST API와 AXIOS 기반으로 웹 App을 개발하기 떄문에 PUT, DELETE까지 활용하는 것을 추천
* 브라우저는 서버로부터 응답을 받은 후 Content-Type 헤더 값을 통해 본문 컨텐츠의 타입을 판별한다.
  * 때문에, CSS 파일을 보내는 경우 text/css를 타입으로 설정해주어야 한다.

# Java Web Server와 Servlet 비교
* 서블릿의 HttpServletRequest, Response는 내가 구현한 HttpRequest, HttpResponse와 정확히 같은 역할을 수행한다.
  * 요청, 응답 HTTP 메시지를 파싱하여 객체에 담아두고 활용할 수 있는 메소드를 제공한다.
* Controller 인터페이스는 Servlet 인터페이스와 같다.
  * HttpServlet은 추상 클래스로 공통적인 부분을 어느정도 구현해둔 클래스이다.
* 요청의 URL을 보고 적절한 Controller를 찾아주는 RequestHandler 클래스의 역할을 서블릿 컨테이너가 수행한다.
## 서블릿 (Servlet)
* 서블릿 : HTTP의 요청과 응답에 대한 자바 진영 표준을 정해놓은 것(인터페이스 활용)
* 서블릿 컨테이너의 구현체 : Tomcat, Jetty 등
### 서블릿 컨테이너 역할
* 서블릿의 생명주기를 관리한다.
  * 요청 URL과 서블릿 클래스의 매핑
  * 서블릿 클래스의 인스턴스 생성 (서블릿의 초기화(init) 및 소멸(destroy) 작업 담당)
  * 요청을 받으면 적절한 서블릿에 작업 위임
* 멀티쓰레딩 지원
* 설정 파일을 활용한 보안 관리
* JSP 지원
### 과정
1. 서블릿 컨테이너 시작
2. 클래스패스 내의 Servlet 인터페이스를 구현하는 클래스들을 찾는다.
3. @WebServlet의 값인 URL과 서블릿을 Map으로 매핑
4. 서블릿 클래스 인스턴스 생성
5. init() 메소드를 호출하여 초기화
6. 클라이언트 요청을 대기하다가 들어오면 서블릿을 찾아 service() 메소드를 호출
7. 서비스 후 서블릿 컨테이너 종료 시, 관리하고 있던 모든 서블릿의 destroy() 메소드 호출하여 소멸 작업



