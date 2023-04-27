<h1 style="background-color:gray;"> 📝 블로그 프로젝트 </h1>
Spring boot, Data JPA, Querydsl을 수강했으니 직접 블로그 프로젝트를 개발하면서
<br>
티스토리에 게시한 글과 학습한 내용을 기록하고자 한다.
<div>
<h1> Tech Stack </h1>
<h2> Back-end </h2>
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
        # default-page-size: 10
        # 최대 page 사이즈
        max-page-size: 2000

logging.level:
  org.hibernate.SQL: debug
# org.hibernate.type: trace

server:
  port: {사용할 port 번호}

frontEnd:
  port: {cors 해결을 위해 Front-end PORT 번호}
  
auth:
  key: {key 값}

cloud:
  aws:
    credentials:
      accessKey: {aws IAM 사용자 accessKey}
      secretKey: {aws IAM 사용자 secretKey}
    region:
      static: {aws region}
    stack:
      auto: false

aws:
  s3:
    bucketName: {aws 접근할 버킷 이름}

```


<b> 중활호 {} 에 있는 본인 환경에 맞춰서 사용할 것 </b>

<br/>
<h2> Front-end </h2>
<ul>
  <li> Vue.js </li>
</ul>
</div>
<br/>

<h3> .env </h3>

```
# env
VITE_ENVIRONMENT=development
VITE_SERVER_PORT=9000
VITE_API_URL=http://localhost:9000
```

<h2> API Mapping </h2>

<h3> 게시글 </h3>
<ul> 
<li> GET /posts/{postId} ->            단건 조회 </li>
<li> POST /posts ->                    게시글 저장 </li>
<li> GET /posts/list ->                게시글 리스트 </li>
<li> PATCH /posts/update/{postId} ->   게시글 수정 </li>
<li> DELETE /posts/delete/{postId} ->  게시글 삭제 </li>
</ul>

<h3> 댓글 </h3>
<ul>
<li> GET /comments/{commentId} ->      댓글 단건 조회 </li>
<li> POST /comments ->                 댓글 저장 </li>
<li> DELETE /comments/{commentId} ->   댓글 단건 삭제 </li>
</ul>
