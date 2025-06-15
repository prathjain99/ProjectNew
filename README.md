# QuantCrux - Quantitative Finance Platform

A comprehensive multi-asset quantitative strategy design, simulation, and execution support platform built with Spring Boot microservices and React.js.

## 🏗️ Architecture

### Frontend
- **React.js** with TypeScript
- **TailwindCSS** for styling
- **Framer Motion** for animations
- **Recharts** for data visualization
- **Axios** for API communication

### Backend (Microservices)
- **Spring Boot 3.2.0** with Java 17
- **Spring Cloud 2023.0.0** for microservices
- **PostgreSQL** for data persistence
- **Eureka** for service discovery
- **Spring Cloud Gateway** for API routing
- **JWT** for authentication

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- Node.js 18+
- npm or yarn

### 1. Clone and Setup
```bash
git clone <repository-url>
cd quantcrux-platform
npm install
```

### 2. Start Backend Services
```bash
# Build all microservices
npm run backend:build

# Start all services with Docker Compose
npm run backend:up

# Check logs
npm run backend:logs
```

### 3. Start Frontend
```bash
npm run dev
```

### 4. Full Stack Development
```bash
# Start both backend and frontend concurrently
npm run full-stack
```

## 🏢 Microservices

| Service | Port | Description |
|---------|------|-------------|
| Eureka Server | 8761 | Service Discovery |
| Config Server | 8888 | Configuration Management |
| API Gateway | 8080 | Request Routing & Load Balancing |
| Auth Service | 8081 | Authentication & Authorization |
| Strategy Service | 8082 | Strategy Management |
| Market Data Service | 8083 | Market Data Provider |
| Product Service | 8084 | Structured Products |
| Portfolio Service | 8085 | Portfolio Management |
| Analytics Service | 8086 | Risk & Performance Analytics |

## 👥 User Roles

### Client
- Browse and simulate structured products
- Monitor portfolio PnL and performance
- Download trade reports and statements

### Portfolio Manager
- Create structured financial products
- Allocate products to clients
- Monitor execution and lifecycle events
- Access comprehensive analytics dashboard

### Researcher
- Design quantitative trading strategies
- Run backtests on historical data
- Access advanced analytics and risk metrics

## 🔧 Development

### Backend Development
```bash
# Build specific service
cd backend/auth-service
mvn clean package

# Run service locally
java -jar target/auth-service-1.0.0.jar
```

### Frontend Development
```bash
# Start development server
npm run dev

# Build for production
npm run build
```

### Database Setup
PostgreSQL databases are automatically created via Docker Compose:
- `quantcrux_auth` - User authentication
- `quantcrux_strategy` - Strategy definitions
- `quantcrux_product` - Product configurations
- `quantcrux_portfolio` - Portfolio positions
- `quantcrux_analytics` - Risk metrics

## 📊 Key Features

### Strategy Builder
- Visual strategy designer with technical indicators
- MACD, EMA, RSI, and custom signals
- Backtesting with performance metrics
- Risk management rules

### Product Creator
- Digital Options and Dual Currency Investments
- Barrier options with knock-in/knock-out
- Real-time pricing and Greeks calculation
- Payoff diagram visualization

### Portfolio Management
- Real-time P&L tracking
- Risk attribution analysis
- Performance benchmarking
- Automated reporting

### Analytics Engine
- Value at Risk (VaR) calculations
- Sharpe ratio and risk-adjusted returns
- Maximum drawdown analysis
- Correlation and beta metrics

## 🔐 Security

- JWT-based authentication
- Role-based access control (RBAC)
- CORS configuration for cross-origin requests
- Secure password hashing with BCrypt

## 📈 API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration

### Strategies
- `GET /api/strategies` - List user strategies
- `POST /api/strategies` - Create new strategy
- `GET /api/strategies/{id}` - Get strategy details

### Market Data
- `GET /api/market-data/{symbol}` - Get OHLCV data
- Query parameters: `days` (default: 252)

### Products
- `GET /api/products` - List products
- `POST /api/products` - Create structured product

## 🐳 Docker Services

```bash
# Start all services
docker-compose up -d

# Stop all services
docker-compose down

# View logs
docker-compose logs -f [service-name]

# Rebuild and restart
docker-compose up -d --build
```

## 🧪 Testing

### Frontend Testing
```bash
npm run test
```

### Backend Testing
```bash
cd backend
mvn test
```

## 📝 Environment Variables

### Backend Services
- `SPRING_DATASOURCE_URL` - Database connection
- `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` - Eureka server URL
- `JWT_SECRET` - JWT signing secret

### Frontend
- `VITE_API_BASE_URL` - Backend API URL (default: http://localhost:8080)

## 🚀 Deployment

### Production Build
```bash
# Build frontend
npm run build

# Build backend services
npm run backend:build

# Deploy with Docker
docker-compose -f docker-compose.prod.yml up -d
```

## 📚 Documentation

- [API Documentation](./docs/api.md)
- [Architecture Guide](./docs/architecture.md)
- [Deployment Guide](./docs/deployment.md)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.