<template>
  <div class="container py-4">
    <!-- Header -->
    <header class="pb-3 mb-4 border-bottom">
      <div class="d-flex justify-content-between align-items-center">
        <h1 class="h3 mb-0">写文章</h1>
        <div class="btn-group">
          <button @click="cancelAndReturn" class="btn btn-outline-secondary">
            <i class="bi bi-x-lg"></i> 取消编辑
          </button>
          <button @click="savePost" class="btn btn-primary">
            <i class="bi bi-check-lg"></i> 发布文章
          </button>
        </div>
      </div>
    </header>

    <!-- Main Content -->
    <main>
      <div class="card shadow-sm">
        <div class="card-body">
          <div class="mb-3">
            <input 
              v-model="title" 
              class="form-control form-control-lg" 
              placeholder="请输入文章标题" 
            />
          </div>
          <div class="mb-3">
            <textarea 
              v-model="content" 
              class="form-control" 
              placeholder="请输入文章内容（支持 Markdown 格式）"
              rows="15"
            ></textarea>
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

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { usePostStore } from '../stores/postStore'

const router = useRouter()
const postStore = usePostStore()

const title = ref('')
const content = ref('')

const savePost = () => {
  if (!title.value.trim()) {
    alert('请输入文章标题')
    return
  }

  postStore.addPost({
    id: Date.now(),
    title: title.value,
    content: content.value,
  })
  router.push('/')
}

const cancelAndReturn = () => {
  if (title.value.trim() || content.value.trim()) {
    if (confirm('确定要放弃编辑吗？')) {
      router.push('/')
    }
  } else {
    router.push('/')
  }
}
</script>

<style scoped>
/* 添加 Bootstrap Icons */
@import url("https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css");
</style>