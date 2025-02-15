# ---------- Stage 1: 用 Node 镜像构建 -----------
FROM node:18-alpine AS build

# 设置工作目录
WORKDIR /app

# 将 package.json 和 lock 文件复制到容器
COPY package*.json ./
# 如果你使用的是 yarn，可改为 COPY yarn.lock ./

# 安装依赖
RUN npm ci
# 如果你用的是 yarn，可以写 RUN yarn install

# 复制源码和配置文件到容器中
COPY . .

# 运行 Vite 构建
RUN npm run build
# 或者 RUN yarn build

# ---------- Stage 2: 用 Nginx 镜像部署 ----------
FROM nginx:1.21-alpine

# 将构建生成的 dist 内容复制到 Nginx 默认静态目录
COPY --from=build /app/dist /usr/share/nginx/html

# 如果有自定义 Nginx 配置，可以在这里替换默认配置
# COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY custom-nginx.conf /etc/nginx/conf.d/default.conf

# 暴露 Nginx 默认端口
EXPOSE 80

# 指定启动命令
CMD ["nginx", "-g", "daemon off;"]
    