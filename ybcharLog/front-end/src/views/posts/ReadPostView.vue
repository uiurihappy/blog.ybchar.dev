<script setup lang="ts">
import { defineProps, onMounted, ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import type { Comments } from '../../common/comments/comments.interface';
import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';
import timezone from 'dayjs/plugin/timezone';
import type { Posts } from '@/common/posts/posts.interface';
dayjs.extend(timezone);

const username = ref('');
const password = ref('');
const secretStatus = ref(0);
const commentContent = ref('');
const props = defineProps({
  postId: {
    type: [Number, String],
    required: true,
  },
});

const post = ref({
  id: 0,
  title: '',
  content: '',
  viewCount: 0,
  likeCount: 0,
  isDeleted: 0,
  display: 0,
  lastModifiedDate: '',
  createdAt: '',
  comments: [] as Comments[],
});
const router = useRouter();

const moveToEdit = (postId: number) => {
  router.push({ name: 'edit', params: { postId } });
};

const writeComment = (post: Posts) => {
  axios
    .post(`/api/posts/${props.postId}/comments`, {
      username: username.value,
      password: password.value,
      secretStatus: secretStatus.value,
      commentContent: commentContent.value,
      post,
    })
    .then(() => {
      router.go(0);
      alert('댓글이 성공적으로 작성되었습니다.');
    })
    .catch(() => {
      alert('댓글 작성이 실패되었습니다.');
    });
};

onMounted(() => {
  axios
    .get(`/api/posts/${props.postId}`)
    .then(result => {
      post.value = result.data;
    })
    .catch(() => {
      alert('글 조회에 실패하였습니다.');
    });
});
</script>

<template>
  <el-row>
    <el-col>
      <div class="mt-2">
        <h2 class="title">제목: {{ post.title }}</h2>
        <div class="sub d-flex">
          <div class="category">개발</div>

          <div class="regDate">
            작성일:
            {{
              post.createdAt === null
                ? dayjs().format('YYYY-MM-DD HH:mm:ss')
                : dayjs(post.createdAt).format('YYYY-MM-DD HH:mm:ss')
            }}
          </div>
        </div>
      </div>
    </el-col>
  </el-row>

  <el-row>
    <el-col>
      <div class="content">글 내용: {{ post.content }}</div>
    </el-col>
  </el-row>

  <br />

  <el-row>
    <el-col>
      <div class="d-flex justify-content-end">
        <el-button type="warning" @click="moveToEdit(post.id)">
          수정하기
        </el-button>
      </div>
    </el-col>
  </el-row>

  <h2>댓글</h2>
  <div class="mt-2">
    <ul>
      <li v-for="comment in post.comments" :key="comment.id">
        <div>
          {{ comment.username }}
        </div>
        <div>
          {{ comment.commentContent }}
        </div>
      </li>
    </ul>
  </div>
  <h3>댓글 작성</h3>
  <div>
    <el-input v-model="username" placeholder="이름을 적어주세요" />
  </div>
  <div>
    <el-input v-model="password" placeholder="비밀번호를 적어주세요" />
  </div>
  <div class="mt-2">
    비밀글<el-checkbox v-model="secretStatus" true-label="1" false-label="0" />
  </div>
  <div class="mt-2">
    <el-input v-model="commentContent" type="textarea" rows="15" />
  </div>

  <div class="mt-2">
    <div class="d-flex justify-content-end">
      <el-button type="primary" @click="writeComment(post)">작성완료</el-button>
    </div>
  </div>
</template>

<style scope lang="scss">
.title {
  font-size: 1.6rem;
  font-weight: 600;
  color: #383838;
  margin: 0;
}

.sub {
  margin-top: 6px;
  margin-bottom: 5px;
  font-size: 0.78rem;
  .regDate {
    margin-left: 15px;
    color: #6b6b6b;
  }
}

.content read {
  font-size: 0.95rem;
  margin-top: 8px;
  color: #616161;
  white-space: break-spaces;
  line-height: 1.5;
}
</style>
