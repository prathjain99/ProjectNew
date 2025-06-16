# QuantCrux - Enterprise Quantitative Finance Platform

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
- **Apache Kafka** for event streaming
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
| User Service | 8084 | User Profile Management |
| Backtest Service | 8085 | Strategy Backtesting |
| Product Service | 8086 | Structured Products |
| Portfolio Service | 8087 | Portfolio Management |
| Analytics Service | 8088 | Risk & Performance Analytics |
| Reporting Service | 8089 | Report Generation |
| Booking Service | 8090 | Trade Booking & Lifecycle |
| Lifecycle Service | 8091 | Product Lifecycle Events |
| Pricing Engine | 8092 | Monte Carlo Pricing |

## 👥 User Roles

### Client
- Browse and simulate structured products
- Book trades through trading desk
- Monitor portfolio PnL and performance
- Download trade reports and statements

### Portfolio Manager
- Create structured financial products
- Access advanced trading desk with pricing
- Allocate products to clients
- Monitor execution and lifecycle events
- Access comprehensive analytics dashboard

### Researcher
- Design quantitative trading strategies
- Run backtests on historical data
- Access advanced analytics and risk metrics
- Monte Carlo simulations and walk-forward analysis

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
- `quantcrux_booking` - Trade booking
- `quantcrux_lifecycle` - Lifecycle events
- `quantcrux_pricing` - Pricing calculations

## 📊 Key Features

### Advanced Strategy Builder
- Visual strategy designer with technical indicators
- MACD, EMA, RSI, and custom signals
- Backtesting with performance metrics
- Monte Carlo simulation for robustness testing
- Walk-forward analysis for model validation
- Risk management rules and position sizing

### Enterprise Product Creator
- Digital Options and Dual Currency Investments
- Barrier options with knock-in/knock-out features
- Autocallable notes and reverse convertibles
- Real-time Monte Carlo pricing with Greeks
- Payoff diagram visualization
- Lifecycle event management

### Professional Trading Desk
- Real-time pricing with confidence intervals
- Trade booking with full lifecycle tracking
- Position management and P&L monitoring
- Risk analytics and exposure limits
- Trade blotter with status tracking

### Portfolio Management
- Real-time P&L tracking across all positions
- Risk attribution analysis by product type
- Performance benchmarking against indices
- Automated reporting and compliance
- Greeks aggregation and hedging analysis

### Advanced Analytics Engine
- Value at Risk (VaR) calculations (95% and 99%)
- Sharpe ratio and risk-adjusted returns
- Maximum drawdown analysis with recovery time
- Correlation and beta metrics
- Monte Carlo stress testing
- Scenario analysis and sensitivity testing

### Lifecycle Management
- Automated fixing processing
- Barrier breach detection and notifications
- Coupon payment calculations
- Autocall event processing
- Maturity and early redemption handling
- Event-driven architecture with Kafka

## 🔐 Security

- JWT-based authentication with role-based access control
- Secure password hashing with BCrypt
- CORS configuration for cross-origin requests
- API Gateway with request filtering
- Service-to-service authentication

## 📈 API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration

### Trading & Booking
-  `POST /api/trades/book` - Book a new trade
- `GET /api/trades` - Get user trades
- `PUT /api/trades/{id}/status` - Update trade status

### Pricing
- `POST /api/pricing/calculate` - Calculate product price
- `POST /api/pricing/monte-carlo` - Monte Carlo pricing

### Lifecycle
- `GET /api/lifecycle/events/{tradeId}` - Get trade events
- `POST /api/lifecycle/process-fixings` - Process fixings
- `POST /api/lifecycle/check-barriers` - Check barriers

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

### Demo Accounts
- **Client**: `client1` / `password`
- **Portfolio Manager**: `pm1` / `password`
- **Researcher**: `researcher1` / `password`

### Testing Workflow
1. Login with demo account
2. Create/select a strategy (Researcher/PM)
3. Run backtest with Monte Carlo simulation
4. Create structured product (PM)
5. Book trade through trading desk
6. Monitor lifecycle events
7. View portfolio analytics and reports

## 📝 Environment Variables

### Backend Services
- `SPRING_DATASOURCE_URL` - Database connection
- `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` - Eureka server URL
- `JWT_SECRET` - JWT signing secret
- `SPRING_KAFKA_BOOTSTRAP_SERVERS` - Kafka brokers

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

## 📁 Directory Structure

```
📁 quantcrux-platform
├── frontend/
│   ├── src/components/
│   │   ├── Auth/
│   │   ├── Dashboard/
│   │   ├── Strategy/
│   │   ├── Products/
│   │   ├── Trading/
│   │   ├── Portfolio/
│   │   ├── Analytics/
│   │   └── Reports/
│   ├── src/services/
│   └── src/contexts/
├── backend/
│   ├── eureka-server/
│   ├── gateway-service/
│   ├── auth-service/
│   ├── strategy-service/
│   ├── product-service/
│   ├── booking-service/
│   ├── lifecycle-service/
│   ├── pricing-engine/
│   ├── portfolio-service/
│   ├── analytics-service/
│   └── reporting-service/
└── docker/
    └── docker-compose.yml
```

## 🔧 Advanced Features

### Monte Carlo Simulation
- Configurable number of simulations (up to 1M+)
- Multiple stochastic processes (GBM, Jump Diffusion)
- Confidence intervals and convergence analysis
- Parallel processing for performance

### Walk-Forward Analysis
- Rolling window backtesting
- Out-of-sample validation
- Parameter stability testing
- Overfitting detection

### Risk Management
- Real-time position limits monitoring
- VaR-based risk budgeting
- Stress testing scenarios
- Correlation-based portfolio optimization

### Event-Driven Architecture
- Kafka-based messaging for real-time events
- Asynchronous processing of lifecycle events
- Event sourcing for audit trails
- CQRS pattern for read/write separation

## 📘 API Documentation

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI Spec**: http://localhost:8080/v3/api-docs

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🎯 Production Readiness Checklist

- ✅ Microservices architecture with service discovery
- ✅ JWT-based authentication and authorization
- ✅ Database per service pattern
- ✅ Event-driven communication with Kafka
- ✅ API Gateway with request routing
- ✅ Docker containerization
- ✅ Health checks and monitoring endpoints
- ✅ CORS configuration
- ✅ Error handling and logging
- ✅ Role-based access control
- ✅ Real-time pricing with Monte Carlo
- ✅ Trade lifecycle management
- ✅ Portfolio analytics and reporting

## 🔮 Future Enhancements

- Redis caching for improved performance
- Elasticsearch for advanced search and analytics
- WebSocket connections for real-time updates
- Machine learning models for strategy optimization
- Integration with external market data providers
- Mobile application for portfolio monitoring
- Advanced charting and technical analysis tools
- Regulatory reporting automation