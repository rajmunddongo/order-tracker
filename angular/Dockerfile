# Use the official Node.js image as the base image
FROM node:14

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the entire Angular project
COPY . .

# Build the Angular application
RUN npm run build

# Expose the port the app runs on
EXPOSE 80

# Run the Angular application
CMD ["npm", "start"]
