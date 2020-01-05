FROM node:alpine

COPY webauthn /home/node/webauthn
COPY docker/assets/.env /home/node/webauthn/.env

WORKDIR /home/node/webauthn

RUN yarn install \
 && yarn generate

EXPOSE 3000

ENTRYPOINT [ "yarn", "run", "start" ]
