FROM node:14.15.4 as build

WORKDIR /app

COPY . .

RUN npm install

RUN npm run build --prod

FROM nginx:latest

COPY --from=build /app/dist/angular-trading-ui /usr/share/nginx/html

EXPOSE 80