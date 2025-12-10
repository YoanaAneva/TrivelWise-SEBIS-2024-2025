# Build the Angular application
FROM node:20-slim AS build
WORKDIR /app

# Copy package files first for better caching
COPY package.json package-lock.json ./
RUN npm install

# Copy the full Angular source code
COPY . .

# Build Angular production bundle
RUN npm run build -- --output-path=./dist/travel-wise-frontend --configuration=production

# Serve using Nginx ---
FROM nginx:alpine

# Remove default config
RUN rm -f /etc/nginx/conf.d/default.conf

# Copy the build output to Nginx
COPY --from=build /app/dist/travel-wise-frontend/browser /usr/share/nginx/html

# Add custom nginx config for Angular routing
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
