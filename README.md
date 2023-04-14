# 블로그 프로젝트
Spring boot, Data JPA, Querydsl을 수강했으니 직접 블로그 프로젝트를 개발하면서
<br>
티스토리에 게시한 글과 학습한 내용을 기록하고자 한다.

<h2> Tech Stack </h2>
<h3> 백엔드 </h3>
<ul>
  <li> Java 17 </li>
  <li> Spring boot </li>
  <li> Spring Data JPA </li>
  <li> Querydsl </li>
</ul>

<h3> 프론트엔드(현재 미개발) </h3>
<ul>
  <li> Vue.js </li>
</ul>
<br/>

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
