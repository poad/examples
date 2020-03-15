FROM node:alpine AS builder

USER root

RUN apk --update add --no-cache python3 make g++

USER node

COPY --chown=node:node ./web-client /home/node/web-client
COPY --chown=node:node docker/assets/.env /home/node/web-client/.env

WORKDIR /home/node/web-client

RUN yarn install \
 && yarn build

FROM nginx:alpine

COPY --from=builder /home/node/web-client/out /usr/share/nginx/html/
COPY --from=builder /home/node/web-client/src/public/favicon.ico /usr/share/nginx/html/

COPY ./docker/assets/etc/nginx /etc/nginx

EXPOSE 80

STOPSIGNAL SIGTERM

CMD ["nginx", "-g", "daemon off;"]

