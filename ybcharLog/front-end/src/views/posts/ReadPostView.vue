<script setup lang="ts">
import { defineProps, onMounted, ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import type { Comments } from '../../common/comments/comments.interface';
import dayjs from 'dayjs';
import timezone from 'dayjs/plugin/timezone';
import type { Posts } from '../../common/posts/posts.interface';
import { getFormattedDate } from '../../common/tools/dateFormat.tool';
import jwt_decode from 'jwt-decode';
import MarkdownIt from 'markdown-it';
interface AccessToken {
  userRoles: string;
  // 다른 프로퍼티가 있다면 추가 가능
}

dayjs.extend(timezone);

const username = ref('');
const password = ref('');
const secretStatus = ref(0);
const commentContent = ref('');
const renderedContent = ref('');

let userRole = ref('');
const accessToken: string | null = sessionStorage.getItem('accessToken');

const isAccessTokenValid = () => {
  if (accessToken) {
    try {
      const { userRoles }: AccessToken = jwt_decode(
        accessToken,
        import.meta.env.VITE_SECRET_KEY
      );

      userRole.value = userRoles === 'ROLE_ADMIN' ? userRoles : '';
      return true;
    } catch (error) {
      console.error('Invalid access token:', error);
      return false;
    }
  } else {
    userRole.value = '';
    return false;
  }
};

let isCodeBlock = ref(false);
let codeBlockContent = ref('');

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
  thumbnailImage: '',
  comments: [] as Comments[],
});

const router = useRouter();

const moveToEdit = (postId: number) => {
  router.push({ name: 'Edit', params: { postId } });
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
  const md = new MarkdownIt();
  axios
    .get(`/api/posts/${props.postId}`)
    .then(result => {
      post.value = result.data;
      renderedContent.value = md.render(result.data.content);
    })
    .catch(() => {
      alert('글 조회에 실패하였습니다.');
    });
});
</script>

<template>
  <div class="container">
    <el-row>
      <el-col>
        <div class="title">{{ post.title }}</div>
        <div class="mt-2">
          <div class="sub d-flex">
            <div class="regDate">
              작성일:
              {{
                post.createdAt === null
                  ? dayjs().format('YYYY년 MM월 DD일 A H:mm')
                  : dayjs(post.createdAt).format('YYYY년 MM월 DD일 A H:mm')
              }}
              <div class="regDate">
                조회 수:
                {{ post.viewCount }}
              </div>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row>
      <el-col>
        <div class="content">
          <div v-if="isCodeBlock">
            <pre>
            <code class="code-block">{{ codeBlockContent }}</code>
          </pre>
          </div>
          <div v-html="renderedContent"></div>
        </div>
      </el-col>
    </el-row>
    <br />

    <div v-if="isAccessTokenValid()">
      <el-row>
        <el-col>
          <div class="d-flex justify-content-end">
            <el-button type="warning" @click="moveToEdit(post.id)">
              수정하기
            </el-button>
          </div>
        </el-col>
      </el-row>
    </div>

    <div>
      <h3 style="font-size: 1.3rem; margin-bottom: 1.3rem">
        댓글 ({{ post.comments.length }})
      </h3>
    </div>
    <div class="comment">
      <div class="comment-list">
        <div
          v-for="comment in post.comments"
          :key="comment.id"
          class="comment-item"
        >
          <div class="comment-info">
            <span class="comment-username">{{ comment.username }}</span>
            <span class="comment-date">{{
              getFormattedDate(comment.createdAt)
            }}</span>
          </div>
          <div class="comment-content">{{ comment.commentContent }}</div>
        </div>
      </div>
    </div>

    <template v-if="post.id">
      <div class="comment-write">
        <h3 class="comment-write__title">댓글 작성</h3>
        <div class="comment-write__input">
          <el-input v-model="username" placeholder="이름" />
        </div>
        <div class="comment-write__input mt-2">
          <el-input
            v-model="commentContent"
            placeholder="무분별한 댓글은 운영자에게 상처입니다.🥲"
            type="textarea"
            rows="7"
          />
        </div>

        <div class="comment-write__button">
          <div class="d-flex justify-content-end">
            <el-button type="primary" @click="writeComment(post)"
              >등록</el-button
            >
          </div>
        </div>
      </div>
    </template>
  </div>
</template>

<style lang="scss" scoped>
@import '@/assets/styles/read-view.scss';
</style>
