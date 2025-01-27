<template>
  <div>
    <nav class="navigation">
      <router-link to="/" class="nav-button back-home">
        返回主页
      </router-link>
      <button 
        v-if="post" 
        @click="toggleEdit" 
        class="nav-button edit-button"
      >
        {{ isEditing ? '取消编辑' : '编辑文章' }}
      </button>
      <button 
        v-if="isEditing" 
        @click="savePost" 
        class="nav-button save-button"
      >
        保存更新
      </button>
    </nav>
    
    <div v-if="post">
      <div v-if="isEditing">
        <input 
          v-model="editingPost.title" 
          class="title-input"
          placeholder="文章标题"
        />
        <textarea 
          v-model="editingPost.content" 
          class="content-input"
          placeholder="文章内容"
        ></textarea>
      </div>
      <div v-else>
        <h1>{{ post.title }}</h1>
        <div v-html="post.content"></div>
      </div>
    </div>
    <div v-else>
      <h1>文章不存在</h1>
    </div>
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
</script>

<style scoped>
.navigation {
  margin: 16px 0;
  display: flex;
  gap: 12px;
}

.nav-button {
  padding: 8px 16px;
  border-radius: 4px;
  text-decoration: none;
  display: inline-block;
  border: none;
  cursor: pointer;
  font-size: 1em;
}

.back-home {
  background-color: #f0f0f0;
  color: #333;
}

.back-home:hover {
  background-color: #e0e0e0;
}

.edit-button {
  background-color: #4CAF50;
  color: white;
}

.edit-button:hover {
  background-color: #45a049;
}

.save-button {
  background-color: #2196F3;
  color: white;
}

.save-button:hover {
  background-color: #1976D2;
}

.title-input {
  width: 100%;
  font-size: 1.5em;
  padding: 8px;
  margin-bottom: 16px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.content-input {
  width: 100%;
  min-height: 400px;
  padding: 8px;
  margin-bottom: 16px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-family: inherit;
  resize: vertical;
}
</style>