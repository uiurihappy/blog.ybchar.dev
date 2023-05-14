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
# application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/{DB 스키마 이름}?serverTimezone=Asia/Seoul
    username: {DB 사용자 이름}
    password: {DB 사용자 비밀번호}
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
    defer-datasource-initialization: true
  data:
    web:
      pageable:
        # page를 1부터 시작하는 옵션
        one-indexed-parameters: true
        # page 사이즈
        #        default-page-size: 10
        # 최대 page 사이즈
        max-page-size: 2000
  session:
    timeout: 86400
    jdbc:
      initialize-schema: always


#  sql:
#    init:
#      mode: always
logging.level:
  org.hibernate.SQL: debug
  com:
    amazonaws:
      util:
        EC2MetadataUtils: error
# org.hibernate.type: trace

ybchar:
  host: localhost

server:
  port: 9000
  servlet:
    session:
      cookie:
        max-age: 172800

util:
  encrypt:
    secretKey: {암호화 비밀 키}
  jwt:
    secretKey: {jwt secretKey}
    refreshKey: {jwt refresh Key}
    defaultExpirationMinutes: 60 # 1시간
    defaultRefreshTokenMinutes: 43200 # 30일

frontEnd:
  port: 5173

auth:
  key: ybchar
  secretKey: 

cloud:
  aws:
    credentials:
      accessKey: {AWS IAM 사용자 accessKey}
      secretKey: {AWS IAM 사용자 secretKey}
    region:
      static: {AWS region}
    stack:
      auto: false

aws:
  s3:
    bucketName: {이미지 등록할 S3 버킷 이름}

```


<b> 중활호 {} 에 있는 본인 환경에 맞춰서 사용할 것 </b>

<br/>
<h2> Front-end </h2>
<ul>
  <li> Typescript </li>
  <li> Javascript </li>
  <li> Vue.js </li>
  <li> scss </li>
</ul>
</div>
<br/>

<h3> .env </h3>

```
# env
VITE_ENVIRONMENT=development
VITE_SERVER_PORT=9000
VITE_API_URL=http://localhost:9000
VITE_SECRET_KEY={jwt decode용 비밀키}
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
