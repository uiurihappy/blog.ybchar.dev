<script setup lang="ts">
import { defineProps, onMounted, ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import type { Comments } from '../common/comments/comments.interface';
import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';
import timezone from 'dayjs/plugin/timezone';
dayjs.extend(timezone);

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
  createdAt: '',
  comments: [] as Comments[],
});
const router = useRouter();

const moveToEdit = (postId: number) => {
  router.push({ name: 'edit', params: { postId } });
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
