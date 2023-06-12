<h1 style="background-color:gray;"> 📝 블로그 프로젝트 </h1>
Spring boot, Data JPA, Querydsl을 학습하였고 실전으로 개발하고자 간단하게 풀스택으로 블로그를 개발
<br>
기존 티스토리에 게시한 글과 블로그를 개발하면서 트러블 슈팅했던 내용을 작성하고자 한다.
<div>
<h1> Tech Stack </h1>
<h2> Back-end </h2>
<ul>
  <li> Java 17 </li>
  <li> Spring boot </li>
  <li> Spring Data JPA </li>
  <li> Spring Security </li>
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

<h3> .env </h3>

```
# env
BASE_URL=/
VITE_ENVIRONMENT=development
VITE_SERVER_PORT={백엔드 서버 URL port번호}
VITE_API_URL={백엔드 API 서버 URL}
VITE_SECRET_KEY={jwt decode용 비밀키}
```

<br/>
<h2> API Docs </h2>

<h3> 인증 </h3>

<ul>
  <li> POST /auth/login ->             로그인 </li>
  <li> POST /auth/join ->              회원가입 </li>
  <li> GET /auth/logout ->             로그아웃 </li>
</ul>

<li> 인증은 Spring Security를 사용하였고, JWT 토큰을 사용 </li>
<li> 프론트엔드에서는 토큰을 Session storage에 저장하여 토큰 값 유효성 체크를 한다. </li>
<li> 로그아웃 시에는 토근 만료 및 Session storage 삭제 </li>

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

<br/>
<h3> TODO </h3>
<ul>
  <li> AWS EC2 생성은 했으나 빌드가 안되서 mariadb 설치 및 빌드하도록 구현 </li>
  <li> 현재 백엔드 프로젝트 내에 하위 디렉토리로 프론트엔드 프로젝트가 존재하므로 고려해서 Route 53 활용하여 도메인 연결할 것 </li>
  <li> 배포 자동화 사용할 것 </li>
</ul>

