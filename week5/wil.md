#백엔드 스터디 5주차 과제
<sub>C335282</sub>

스프링 Layered Architecture

- 어플리케이션의 비지니스 로직이 담기는 계층
- 레프지토리 계층과 소통하며 엔티티 또는 DTO로 소통
  비지니스 로직 예시 -할 일은 인당 최대 10개까지 생성할 수 있다
    1. 할 일을 생성하려는 유저를 조회
    2. 해당 유저가 생성한 모든 할 일을 조회
    3. 모든 할 일의 개수가 10개 미만인지 확인
    4. 10개 미만이라면, 할 일을 생성
       if) 에러 발생? 1. 유저X -> 234 로직 수행X 2. 유저 조회O, 할 일 10개 이상 -> 로직 실행X -복잡한 비즈니스 로직
    5. A 데이터를 생성
    6. B 데이터를 생성
    7. A, B 데이터를 이용하여 C 데이터 생성성
       if) 에러 발생? 2. B데이터 만들다가 생성 실패 -> 다음 로직 수행X
       생성된 A데이터는 어떻게 해야할까? 이미 생성해서 데이터베이스에 저장됨. C데이터를 만들기 위해 필요한 것이었음. C데이터를 만드는 데 실패하였기 때문에 필요없어짐. 따라서 123로직 모두 실행될 필요없음.
       세 로직은 셋 다 실행되거나 셋 다 실행되지 않아야 함/이 전체 로직은 더 이상 쪼갤 수 없는 "원자성"을 가진 로직
       서비스 계층
       :비지니스 로직을 수행 -> 서비스 계층의 하나의 메서드에는 원자성을 갖는 로직을 기술( 로직의 원자성을 보장하기 위해 서비스 계층에 메서드 단위로 트랜잭션을 적용 )
       <sub>JPA 동작 : 트랜잭션 중간에 에러가 발생하면 변경 사항을 롤백</sub>
       서비스 객체 중복 생성 X -> @Service 이용하여 빈 등록
       레포지토리 계층에 의존(생성자 주입방식)
       할 일 생성
       서비스 메서드에 작성한 로직 -> 트랜잭션 안에서 동작(@Transactional, 원자성 보장)
       리팩토링
       isChecked 필드는 기본값이 false 인데 매번 명시해야 할까?
- content, user는 클라이언트가 제공하는 정보이므로 컨트롤러로부터 받아서 저장하도록 수정
- user는 모든 정보를 받지 않고 userid만 받은 뒤 데이터베이스스에서 id로 user를 조회해서 저장
- isChecked 속성은 todo 객체를 생성할 때 false 기본값
- 생성자에서 isChecked 매개변수를 제거하고 기본값 지정
  로그인한 유저만 할 일 생성 -> user 값이 null 이라면 예외 발생
  할 일 조회
  조회만 하는 경우 - > 트랜잭션 내에서 데이터가 변경되지 않도록 readOnly 속성을 활성화(@Transactional(readOnly = true))

스프링 Layered Architecture
컨트롤러

- 클라이언트의 요청을 받고 응답을 보내는 계층
- DTO(Data Transfer Object)를 사용하여 서비스 계층과 데이터를 주고받음 GET/todo/list -> 나의 할 일을 조회하는 메서드를 http 요청을 보냄
  -> 내장 tomcat 서버가 받아서이 GET/todo/list 요청을 처리할 수 있는 전용 컨트롤러에게 전달
  -> 데이터베이스랑 상호작용하여 데이터 조회

프로토콜과 HTTP
HTTP 데이터 -> HTTP헤더 + HTTP바디
보통 어플리케이션과 관련된 데이터는 body
(header에 담는 경우도 있음, 이번 스터디에서는 다루지 않음)

- HTTP 요청으로 보내는 데이터는 Request Body
- HTTP 응답으로 보내는 데이터는 Response Body

컨트롤러 계층

- @ResponseBody 사용하면 메서드가 자바 객체를 반환 -> 객체를 json 데이터로 변환해서 response body에 담아 응답
- @Controller + @ResponseBody -> @RestController
- 서비스 계층에 의존(생성자 주입 방식)
- 메서드가 처리할 요청 method, url 지정(@RequestMapping)
- TodoController : 모든 todo 관련 요청을 처리하는 클래스(API 설계에서 todo 관련 요청은 모두 "/todo"로 시작)
- URL 앞 공통 URL은 클래스에서 지정, URL 뒤 상세 URL은 메서드에서 지정
  할 일 생성
  Todo Service 기능을 이용 ->content, user 데이터 받아야 함함
- Request body 데이터는 보통 json 형식, 메서드 파라미터로 받을 수 있음
- 파라미터로 들어오는 json 데이터 -> 자바 객체(DTO)로 변환(@RequestBody)
- TodoCreateRequest 클래스 생성 -> DTO로 content, user id 받기
- 컨트롤러 계층은 클라이언트의 요청을 처리 -> 처리 결과(HTTP 프로토콜에 정의된 상태코드 사용)를 클라이언트에게 응답

컨트롤러 테스트

- 어플리케이션을 실행 -> API 요청을 보냈을 때 제대로 응답이 오는지 테스트
- API를 테스트하는 도구로는 postman을 사용
  서버에서 에러가 발생하여 500 상태코드 응답 -> H2 콘솔에 유저 직접 추가

할 일 조회
제네릭 타입 -> Response Body로 반환할 객체의 타입을 지정
여러 개의 Todo List가 반환 -> List<Todo>를 지정
Response에도 DTO를 사용(일반적)
할 일 삭제
Path variable은 url에 {변수명} 형태
@PathVariable -> path parameter 값을 매개변수로 받을 수 있음
할 일 수정
@RequestBody, @PathVariable 사용 -> 수정할 content는 간단하게 String으로 받음, request body에 문자열로 전달
