# base image
#FROM node:12.2.0 AS builder

#COPY . ./app
#WORKDIR /app
#RUN npm i node-sass && npm i
#RUN $(npm bin)/ng build --prod

FROM nginx:alpine
#COPY --from=builder /app/dist/ /usr/share/nginx/html
COPY . /usr/share/nginx/html

