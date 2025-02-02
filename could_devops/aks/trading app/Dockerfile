# Make sure you create the Dockerfile in the root directory of your Angular Project, i.e. beside the package.json
# Stage 1: Set up Angular project in Docker

# Pull node image -> We just need a version thats compatible with our project
FROM node:14.15.4 as build 

# Set working directory inside container
WORKDIR /app

#Copy source code from current directory to container directory (/app)
COPY . .

# Stage 2: Serve app with nginx server

# Pull nginx image
FROM nginx:latest

# Copy build output from current build directory to specified nginx directory 
COPY --from=build /app/dist/angular-trading-ui /usr/share/nginx/html

# Expose port 80
EXPOSE 80