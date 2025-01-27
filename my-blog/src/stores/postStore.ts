import { defineStore } from 'pinia';

interface Post {
  id: number;
  title: string;
  content: string;
  author?: string;
}

export const usePostStore = defineStore('post', {
  state: () => ({
    posts: JSON.parse(localStorage.getItem('blog-posts') || '[]') as Post[]
  }),
  actions: {
    addPost(post: Post) {
      this.posts.push({
        ...post,
        author: post.author || '匿名'
      });
      this.savePosts();
    },
    getPostById(id: number) {
      return this.posts.find((post) => post.id === id);
    },
    updatePost(post: Post) {
      const index = this.posts.findIndex(p => p.id === post.id)
      if (index !== -1) {
        this.posts[index] = { ...this.posts[index], ...post }
        this.savePosts();
      }
    },
    deletePost(id: number) {
      const index = this.posts.findIndex(post => post.id === id)
      if (index !== -1) {
        this.posts.splice(index, 1)
        this.savePosts();
      }
    },
    savePosts() {
      localStorage.setItem('blog-posts', JSON.stringify(this.posts))
    }
  },
});