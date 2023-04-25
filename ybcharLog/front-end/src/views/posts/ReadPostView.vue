<script setup lang="ts">
import { defineProps, onMounted, ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import type { Comments } from '../../common/comments/comments.interface';
import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';
import timezone from 'dayjs/plugin/timezone';
import type { Posts } from '../../common/posts/posts.interface';
import { getFormattedDate } from '../../common/tools/dateFormat.tool';

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
  thumbnailImage: '',
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
  <div class="container">
    <el-row>
      <el-col>
        <div class="mt-2">
          <h2 class="title">{{ post.title }}</h2>
          <div class="sub d-flex">
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
        <div class="content" v-html="post.content.replace(/\n/g, '<br>')"></div>
      </el-col>
    </el-row>
    <!-- <el-row>
      <el-col>
        <div class="content">
          <template v-if="post.content.includes('```')">
            <pre
              v-html="post.content.split('```')[0].replace(/\n/g, '<br>')"
            ></pre>
            <div
              v-html="post.content.split('```')[1].replace(/\n/g, '<br>')"
            ></div>
          </template>
          <template v-else>
            <div
              class="content"
              v-html="post.content.replace(/\n/g, '<br>')"
            ></div>
          </template>
        </div>
      </el-col>
    </el-row> -->

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

    <div>
      <h2 style="font-size: 1.5rem; margin-bottom: 1rem">
        댓글 ({{ post.comments.length }})
      </h2>
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
            placeholder="내용을 입력해주세요"
            type="textarea"
            rows="5"
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
