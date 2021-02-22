FROM node:15.9.0-alpine3.10 as build

WORKDIR /app

COPY . .

RUN yarn
RUN yarn build

FROM nginx:latest as runner

COPY --from=build /app/build /var/www

COPY --from=build /app/nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

ENTRYPOINT ["nginx", "-g", "daemon off;"]




