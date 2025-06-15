#!/bin/bash

echo "🚀 Setting up QuantCrux Platform..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker and try again."
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven and try again."
    exit 1
fi

# Install frontend dependencies
echo "📦 Installing frontend dependencies..."
npm install

# Build backend services
echo "🔨 Building backend services..."
cd backend
mvn clean package -DskipTests
cd ..

# Stop any existing containers
echo "🛑 Stopping existing containers..."
docker-compose down

# Start the services
echo "🚀 Starting backend services..."
docker-compose up -d

# Wait for services to be healthy
echo "⏳ Waiting for services to start..."
sleep 30

# Check service health
echo "🔍 Checking service health..."
for i in {1..30}; do
    if curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; then
        echo "✅ Gateway service is healthy"
        break
    fi
    echo "⏳ Waiting for gateway service... ($i/30)"
    sleep 2
done

for i in {1..30}; do
    if curl -f http://localhost:8081/api/auth/health > /dev/null 2>&1; then
        echo "✅ Auth service is healthy"
        break
    fi
    echo "⏳ Waiting for auth service... ($i/30)"
    sleep 2
done

echo "🎉 Setup complete! You can now:"
echo "   1. Start the frontend: npm run dev"
echo "   2. View logs: npm run backend:logs"
echo "   3. Check status: npm run backend:status"
echo ""
echo "🔐 Demo accounts:"
echo "   - client1 / password (Client)"
echo "   - pm1 / password (Portfolio Manager)"
echo "   - researcher1 / password (Researcher)"