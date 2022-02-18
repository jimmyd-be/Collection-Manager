# Stage 1
FROM node:14-alpine as build-step
RUN mkdir -p /app
WORKDIR /app
COPY package.json /app
RUN npm install --only=production
RUN npm install -g @angular/cli
COPY . /app
RUN ng build --configuration production

FROM nginx:alpine
EXPOSE 80
COPY ./nginx.conf /etc/nginx/nginx.conf
COPY --from=build-step /app/dist/cm/ /usr/share/nginx/html

