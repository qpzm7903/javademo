import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

console.log('Vite config loaded'); // 确保这行代码被执行


export default defineConfig({
  plugins: [vue()],
  build: {
    outDir: 'dist', // 确保构建结果输出到 dist 目录
    sourcemap: false, // 禁用源码映射
    emptyOutDir: true, // 每次构建前清空输出目录
    rollupOptions: {
      output: {
        manualChunks: undefined, // 禁用代码分割（可选）
      },
    },
  },
});