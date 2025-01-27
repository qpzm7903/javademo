<template>
  <div class="container py-4">
    <!-- Header -->
    <header class="pb-3 mb-4 border-bottom">
      <div class="d-flex justify-content-between align-items-center">
        <h1 class="h3 mb-0">文章详情</h1>
        <div class="btn-group">
          <router-link to="/" class="btn btn-outline-secondary">
            <i class="bi bi-arrow-left"></i> 返回主页
          </router-link>
          <button 
            v-if="post" 
            @click="toggleEdit" 
            class="btn"
            :class="isEditing ? 'btn-outline-secondary' : 'btn-primary'"
          >
            <i class="bi" :class="isEditing ? 'bi-x-lg' : 'bi-pencil'"></i>
            {{ isEditing ? '取消编辑' : '编辑文章' }}
          </button>
          <button 
            v-if="isEditing" 
            @click="savePost" 
            class="btn btn-success"
          >
            <i class="bi bi-check-lg"></i> 保存更新
          </button>
          <button 
            v-if="isEditing" 
            @click="cancelAndReturn" 
            class="btn btn-danger"
          >
            <i class="bi bi-x-circle"></i> 放弃编辑
          </button>
        </div>
      </div>
    </header>

    <!-- Main Content -->
    <main>
      <div class="card shadow-sm">
        <div class="card-body">
          <div v-if="post">
            <div v-if="isEditing">
              <div class="mb-3">
                <input 
                  v-model="editingPost.title" 
                  class="form-control form-control-lg"
                  placeholder="文章标题"
                />
              </div>
              <div class="mb-3">
                <textarea 
                  v-model="editingPost.content" 
                  class="form-control"
                  placeholder="文章内容（支持 Markdown 格式）"
                  rows="15"
                ></textarea>
              </div>
            </div>
            <div v-else>
              <h2 class="card-title mb-4">{{ post.title }}</h2>
              <div class="markdown-body" v-html="post.content"></div>
            </div>
          </div>
          <div v-else class="text-center py-5">
            <h2 class="text-muted">文章不存在</h2>
          </div>
        </div>
      </div>
    </main>

    <!-- Footer -->
    <footer class="pt-5 my-5 text-muted border-top">
      Created with Bootstrap · {{ new Date().getFullYear() }}
    </footer>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import { usePostStore } from '../stores/postStore';
import { marked } from 'marked';
import hljs from 'highlight.js';
import 'highlight.js/styles/github.css';

const props = defineProps<{
  id: number
}>();

const postStore = usePostStore();
const isEditing = ref(false);
const editingPost = ref({ title: '', content: '' });

const post = computed(() => {
  const rawPost = postStore.getPostById(props.id);
  if (!rawPost) return null;

  return {
    ...rawPost,
    content: isEditing.value 
      ? rawPost.content 
      : marked(rawPost.content || '', {
          highlight: function (code: string, lang: string) {
            if (lang && hljs.getLanguage(lang)) {
              return hljs.highlight(lang, code).value;
            }
            return hljs.highlightAuto(code).value;
          },
        })
  };
});

const toggleEdit = () => {
  if (!post.value) return;
  
  if (!isEditing.value) {
    // 开始编辑时，复制当前文章内容到编辑状态
    editingPost.value = {
      title: post.value.title,
      content: postStore.getPostById(props.id)?.content || ''
    };
  }
  isEditing.value = !isEditing.value;
};

const savePost = async () => {
  if (!post.value) return;
  
  await postStore.updatePost({
    id: props.id,
    title: editingPost.value.title,
    content: editingPost.value.content
  });
  
  isEditing.value = false;
};

const cancelAndReturn = () => {
  isEditing.value = false;
};
</script>

<style scoped>
/* 添加 Bootstrap Icons */
@import url("https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css");

.markdown-body {
  line-height: 1.6;
}

.markdown-body pre {
  background-color: #f6f8fa;
  border-radius: 6px;
  padding: 16px;
}

.markdown-body code {
  background-color: rgba(175, 184, 193, 0.2);
  border-radius: 6px;
  padding: 0.2em 0.4em;
}
</style>