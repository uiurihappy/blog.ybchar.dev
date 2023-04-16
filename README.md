<h1 style="background-color:gray;"> 블로그 프로젝트 </h1>
Spring boot, Data JPA, Querydsl을 수강했으니 직접 블로그 프로젝트를 개발하면서
<br>
티스토리에 게시한 글과 학습한 내용을 기록하고자 한다.
<div>
<h2> Tech Stack </h2>
<h3> 백엔드 </h3>
<ul>
  <li> Java 17 </li>
  <li> Spring boot </li>
  <li> Spring Data JPA </li>
  <li> Querydsl </li>
</ul>

```
# application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/{DB 스키마 이름}?serverTimezone=Asia/Seoul
    username: {mysql 사용자 이름}
    password: {mysql 비밀번호}
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: validate
      naming:
        # underscore to camelcase table name
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    properties:
      hibernate:
        # show_sql: true
        format_sql: true

  data:
    web:
      pageable:
        # page를 1부터 시작하는 옵션
        one-indexed-parameters: true
        # page 사이즈
        default-page-size: 10
        # 최대 page 사이즈
        max-page-size: 2000

logging.level:
  org.hibernate.SQL: debug
# org.hibernate.type: trace

server:
  port: {사용할 port 번호}
```
<b> 중활호 {} 에 있는 본인 환경에 맞춰서 사용할 것 </b>

<br/>
<h3> 프론트엔드(개발 진행 중) </h3>
<ul>
  <li> Vue.js </li>
</ul>
</div>

## API Mapping
### 게시글
- GET /posts/{postId} ->            단건 조회
- POST /posts ->                    게시글 저장
- GET /posts/list ->                게시글 리스트
- PATCH /posts/update/{postId} ->   게시글 수정
- DELETE /posts/delete/{postId} ->  게시글 삭제

### 댓글
- GET /comments/{commentId} ->      댓글 단건 조회
- POST /comments ->                 댓글 저장
- DELETE /comments/{commentId} ->   댓글 단건 삭제
