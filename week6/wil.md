#백엔드 스터디 6주차 과제
<sub>C335282 이예영</sub>

요청 데이터의 유효성 검사
- 지금까지 만든 API 서버는 의도한 요청에 대해서만 처리
- but 실제로는 의도하지 않은 요청이 들어올 때도 많음
- content 최대 길이 : 100
  - 만약 문자열이 100보다 길다면? 
- DB에 데이터를 넣을 때 SQL 에러가 발생 -> 길이가 100이 넘는 데이터는 저장X, 서비스의 정책을 위반X
문제점
- 클라이언트는 문제를 알 수 없음
- 서버한테 요청을 보냈는데 그냥 500 에러 -> 뭔가 서버 내부에서 문제가 발생했다 정도만 알 수 있음
- 데이터의 길이가 정책을 위반했다는 것-> 문자열 길이 측정을 통해 스프링에서 사전에 확인 가능 
  => 데이터베이스 요청을 보내서 예외를 확인 -> 비효율적(시간, 자원의 낭비가 발생)
=>이러한 문제를 해결하기 위해 유효성 검사를 하는 것
유효성 검사란?
요청으로 들어오는 데이터가 올바른 형식인지 검사하는 것
- 스프링에서는 데이터를 받아들이는 DTO에서 유효성을 검사(‘형식’ 만 검사, 존재하지 않는 멤버 아이디와 같은 경우는 유효성 검사로 체크할
  수 없음)
1. 유효성 검사를 도와줄 외부 의존성을 추가 -> build.gradle에 implementation 'org.springframework.boot:spring-boot-starter-validation' 추가
2. DTO 클래스에 제약 사항과 에러 메시지를 명시
3. 명시된 제약 조건에 맞는지 검사 -> @Valid 사용

예외처리
현재 서버에서 에러가 발생 -> response body 내용만으로는 에러가 발생한 원인을 알 수 없음
=> 원인을 알려주는 에러 메시지를 담도록 직접 응답 객체를 만들어서 전송 = Global Exception Handler
Global Exception Handler
- 스프링은 예외 종류에 따라 응답할 response를 직접 설정할 수 있는 Global Exception Handler를 제공
- 스프링 어플리케이션 전역에서 발생하는 모든 에러에 대해 어떻게 처리할 지 결정
- 스프링 어플리케이션에서 에러가 발생 -> 해당 에러 타입에 대한 핸들러가 기존 컨트롤러 대신 response body를 생성해 응답
- 에러 클래스를 매칭 -> 상속관계를 따라 매칭
- Exception 클래스 :모든 에러 클래스의 공통 부모 → Exception 클래스에 대한 핸들러를 작성하면 특정 핸들러로
  처리하지 못한 에러는 이 핸들러가 처리

AOP(Aspect-Oriented Programming) : 관점 지향 프로그래밍
- 객체지향 프로그래밍을 보완하는 개념
1. Aspect : 여러 클래스가 공통적으로 갖느 관심사
2. Joint point : 실제 프로그램의 실행 중 관심사가 발생하는 곳
3. Advice : 특정 joint point에서 실행하는 action
=> 모든 컨트롤러의 공통 관심사(에러처리)를 별도의 클래스로 분리하여 구현한 것(@ControllerAdvice)
=> 모든 서비스 메서드의 공통 관심사(트랜잭션)를 미리 구현된 별도의 클래스가 대신 처리하도록 구현한 것(@Transactional)

커스텀 예외 처리
- 커스텀 예외 클래스를 구현(RuntimeException)
- 어플리케이션이 공통으로 사용할 예외 -> common package 밑
에러메세지 클래스
- 예외 메세지 문자열이 여러 곳에 중복적으로 사용 -> 수정 불편
- 예외 메세지를 상수로 정의한 클래스(DTO에 작성한 에러 메세지도 상수로 적용 가능)

API 문서화
: 백엔드가 만든 API에 대한 사용법을 문서로 공유하는 것(프론트엔드와 협업할 때 API 문서를 공유)
1. spring doc을 이용해서 OpenAPI 규격으로 API 문서 생성(OpenAPI : 표준 API 문서 규격)
2. Swagger-ui를 사용하여 spring doc이 생성한 API 문서에 swagger 디자인 적용(swagger 이외에도 다양한 API 문서화 도구가 있음)
- Spring doc 의존성을 추가 -> build.gradle에 implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0' 추가
- spring doc : swagger-ui를 적용한 문서 만듦
- status code에 설명 추가(@ApiResponse)