FROM node:18.13.0 AS angular-build
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm i
COPY . .
RUN npm run build -- --configuration production

FROM nginx:alpine
COPY --from=angular-build /usr/src/app/dist/vending-client/* /usr/share/nginx/html/
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]