FROM node:18-alpine AS builder
WORKDIR /app

# RUN npm install -g yarn

# Install app dependencies
COPY package.json .
COPY yarn.lock .
RUN yarn 

COPY . .
RUN yarn build

# Stage 2
#######################################
FROM nginx:1.19.0
WORKDIR /app

COPY --from=builder /app/dist .
COPY proxy.conf /etc/nginx/conf.d/default.conf

ENTRYPOINT ["nginx", "-g", "daemon off;"]