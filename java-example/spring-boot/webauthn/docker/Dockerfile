FROM node:alpine

USER root

RUN apk --update add --no-cache python3 make g++

USER node

COPY --chown=node:node webauthn-client /home/node/webauthn
COPY --chown=node:node docker/assets/.env /home/node/webauthn/.env

WORKDIR /home/node/webauthn

RUN yarn install \
 && yarn build \
 && rm -rf node_modules \
 && yarn install --production

EXPOSE 3000

ENTRYPOINT [ "yarn", "run", "start" ]
