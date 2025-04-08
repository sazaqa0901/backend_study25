#백엔드 정규 스터디 1주차 과제
<sub>C335282 이예영</sub> ##웹과 HTTP##

###웹이란?###
여러 컴퓨터가 서로 연결되어 정보를 공유하는 공간

- 일반적인 형태 : 클라이언트-서버
- 클라이언트 : 데이터의 생성.조회.수정.삭제 요청을 전송
- 서버 : 요청대로 동작을 수행하고 응답을 전송
- 예시 : 브라우저(클라이언트)로 네이버(서버)에 접속하는 것

_절대적인 역할이 나눠져 있는 것이 아님. (상대적)_
ex. 만약에 세 개의 컴퓨터가 통신한다고 가정하자. 컴퓨터1(클라이언트)이 컴퓨터2(서버) 데이터를 요청하고 컴퓨터2(클라이언트)가 컴퓨터3(서버)에 데이터를 요청한다.
컴퓨터3(서버)에서 응답을 컴퓨터2(클라이언트)에 보내고 컴퓨터2(서버)가 컴퓨터1(클라이언트)에 응답을 보낸다.

###프로토콜과 HTTP###
컴퓨터를 정해진 동작만 수행 -> 데이터 전송 방법을 명시해야함
서로 다른 방법으로 데이터를 전송X -> **프로토콜**(정해진 규칙이 필요함)
**_프로토콜이란?_** 네트워크 안에서 요청과 응답을 보내는 규칙
웹에서는 **"HTTP"** 사용함.
-HTTP Method : 데이터를 다루는 방법
-URL : 다루고자하는 데이터의 위치
ex. GET http://www.naver/com = 네이버의 메인 화면을 가져와라

GET 조회
POST 생성 (중복 데이터 생성 가능)
PUT 수정(교체)
PATCH 수정
DELETE 삭제
PUT vs PATCH
PUT : 데이터가 없으면 생성/데이터가 있으면 교체(기존 데이터를 지우고 새로운 데이터 생성)
PATCH : 기존 데이터에 수정

###URL 구조###
http(프로토콜, scheme)://(구분)www.example.com(서버주소, domain)/user/1/nickname(서버 내 데이터 위치, path)
-Path Parameter(URL의 일반화된 표현 방법)
http(프로토콜, scheme)://(구분)www.example.com(서버주소, domain)/user/{user_id}/nickname(서버 내 데이터 위치, path)
-Query String(request parameter)
.com(서버 주소, domain)/post/search(데이터 위치, path)?page=1&keyword=hello(Query String) = 어떤 서버에 게시글을 검색을 할 것이다. 그 검색의 조건은 페이지가 1이고 키워드가 hello이다. ###데이터의 구조###
HTTP 데이터 = HTTP 헤더 + HTTP 바디

- HTTP 헤더 : when, who, HTTP method 종류, 요청 경로 등
- HTTP 바디 : 주고 받으려는 데이터(JSON형식)
- HTTP 통신을 할 때 바디 부분을 생략하고 전송 가능함

응답할 때 8**상태코드**를 보낸다.(성공 or 실패)
대표적인 상태 코드

- 200 처리 성공
- 201 데이터 생성 성공
- 400 클라이언트 요청 오류
- 404 요청 데이터 없음
- 500 서버 에려

##프론트엔드와 백엔드##
자주 변하지 않는 화면 UI(프론트엔드)와 자주 변하는 컨텐츠(백엔드) 분리
프론트(클라이언트) 화면에 필요한 데이터를 백엔드에 요청
백엔드(서버) 데이터베이스에서 가져온 컨텐츠 데이터를 프론트에게 응답

###API(Application Programming Interface)란?### 어플리케이션과 소통하는 방법 ###백엔드 API란?### 어떤 http method, url을 사용해야 하는지와 각 요청에 어떤 응답을 보내는지 정의한 것

##REST API##
REST 아키텍처를 따르도록 설계한 API

- URL : 조작할 데이터
- HTTP Method : 동작

##APT 설계##

- 유저 회원가입: POST /register
- 유저 로그인: POST /login
- 나의 할 일 생성: POST /todo
- 나의 할 일 조회: GET /todo/{todo_id}
- 나의 할 일 수정: PATCH /todo/{todo_id}
- 나의 할 일 삭제: DELETE /todo/{todo_id}
- 나의 할 일 체크: POST /todo/{todo_id}/check
- 나의 할 일 체크 해제: POST /todo/{todo_id}/uncheck
- 친구 찾기: GET /user/search?username={username}
- 팔로우: POST /user/{user_id}/follow
- 언팔로우: DELETE /user/{user_id}/unfollow
- 나의 친구 리스트 조회: GET /user/list
- 특정 친구의 할 일 조회: GET/user/{user_id}/todo/list
