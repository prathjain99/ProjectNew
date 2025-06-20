-- QuantCrux Database Initialization Script
-- This script creates all necessary databases and tables with sample data

-- Create databases for microservices
CREATE DATABASE IF NOT EXISTS quantcrux_auth;
CREATE DATABASE IF NOT EXISTS quantcrux_strategy;
CREATE DATABASE IF NOT EXISTS quantcrux_product;
CREATE DATABASE IF NOT EXISTS quantcrux_portfolio;
CREATE DATABASE IF NOT EXISTS quantcrux_analytics;
CREATE DATABASE IF NOT EXISTS quantcrux_booking;
CREATE DATABASE IF NOT EXISTS quantcrux_reporting;
CREATE DATABASE IF NOT EXISTS quantcrux_user;
CREATE DATABASE IF NOT EXISTS quantcrux_backtest;
CREATE DATABASE IF NOT EXISTS quantcrux_lifecycle;
CREATE DATABASE IF NOT EXISTS quantcrux_pricing;

-- Grant privileges to quantcrux user
GRANT ALL PRIVILEGES ON DATABASE quantcrux_auth TO quantcrux;
GRANT ALL PRIVILEGES ON DATABASE quantcrux_strategy TO quantcrux;
GRANT ALL PRIVILEGES ON DATABASE quantcrux_product TO quantcrux;
GRANT ALL PRIVILEGES ON DATABASE quantcrux_portfolio TO quantcrux;
GRANT ALL PRIVILEGES ON DATABASE quantcrux_analytics TO quantcrux;
GRANT ALL PRIVILEGES ON DATABASE quantcrux_booking TO quantcrux;
GRANT ALL PRIVILEGES ON DATABASE quantcrux_reporting TO quantcrux;
GRANT ALL PRIVILEGES ON DATABASE quantcrux_user TO quantcrux;
GRANT ALL PRIVILEGES ON DATABASE quantcrux_backtest TO quantcrux;
GRANT ALL PRIVILEGES ON DATABASE quantcrux_lifecycle TO quantcrux;
GRANT ALL PRIVILEGES ON DATABASE quantcrux_pricing TO quantcrux;

-- Connect to auth database and create tables
\c quantcrux_auth;

-- Users table for authentication
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'CLIENT',
    name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert demo users (passwords are BCrypt hashed 'password')
INSERT INTO users (username, email, password, role, name) VALUES
('client1', 'client1@quantcrux.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'CLIENT', 'John Client'),
('pm1', 'pm1@quantcrux.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'PORTFOLIO_MANAGER', 'Sarah Portfolio Manager'),
('researcher1', 'researcher1@quantcrux.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'RESEARCHER', 'Mike Researcher')
ON CONFLICT (username) DO NOTHING;

-- Connect to user database and create tables
\c quantcrux_user;

-- User profiles table
CREATE TABLE IF NOT EXISTS user_profiles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID UNIQUE NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone_number VARCHAR(20),
    company VARCHAR(100),
    department VARCHAR(100),
    risk_tolerance VARCHAR(20) DEFAULT 'MODERATE',
    investment_experience VARCHAR(20) DEFAULT 'INTERMEDIATE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample user profiles
INSERT INTO user_profiles (user_id, first_name, last_name, company, department, risk_tolerance, investment_experience) VALUES
('550e8400-e29b-41d4-a716-446655440001', 'John', 'Client', 'ABC Corp', 'Finance', 'MODERATE', 'INTERMEDIATE'),
('550e8400-e29b-41d4-a716-446655440002', 'Sarah', 'Manager', 'QuantCrux', 'Portfolio Management', 'AGGRESSIVE', 'PROFESSIONAL'),
('550e8400-e29b-41d4-a716-446655440003', 'Mike', 'Researcher', 'QuantCrux', 'Research', 'CONSERVATIVE', 'ADVANCED')
ON CONFLICT (user_id) DO NOTHING;

-- Connect to strategy database and create tables
\c quantcrux_strategy;

-- Strategies table
CREATE TABLE IF NOT EXISTS strategies (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_by UUID NOT NULL,
    indicators_json TEXT,
    rules_json TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Strategy assets table
CREATE TABLE IF NOT EXISTS strategy_assets (
    strategy_id UUID REFERENCES strategies(id) ON DELETE CASCADE,
    asset VARCHAR(20) NOT NULL,
    PRIMARY KEY (strategy_id, asset)
);

-- Insert sample strategies
INSERT INTO strategies (id, name, description, created_by, indicators_json, rules_json) VALUES
('650e8400-e29b-41d4-a716-446655440001', 'Momentum Strategy', 'EMA crossover with RSI confirmation', '550e8400-e29b-41d4-a716-446655440003', '{"ema_short": 10, "ema_long": 20, "rsi_period": 14}', '{"entry_condition": "ema_cross_up", "exit_condition": "ema_cross_down", "stop_loss": 5, "take_profit": 10}'),
('650e8400-e29b-41d4-a716-446655440002', 'Mean Reversion', 'RSI oversold/overbought strategy', '550e8400-e29b-41d4-a716-446655440003', '{"rsi_period": 14, "bb_period": 20}', '{"entry_condition": "rsi_oversold", "exit_condition": "rsi_overbought", "stop_loss": 3, "take_profit": 8}')
ON CONFLICT (id) DO NOTHING;

-- Insert strategy assets
INSERT INTO strategy_assets (strategy_id, asset) VALUES
('650e8400-e29b-41d4-a716-446655440001', 'SPY'),
('650e8400-e29b-41d4-a716-446655440001', 'QQQ'),
('650e8400-e29b-41d4-a716-446655440002', 'EUR/USD'),
('650e8400-e29b-41d4-a716-446655440002', 'GBP/USD')
ON CONFLICT (strategy_id, asset) DO NOTHING;

-- Connect to product database and create tables
\c quantcrux_product;

-- Products table
CREATE TABLE IF NOT EXISTS products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(200) NOT NULL,
    product_type VARCHAR(50) NOT NULL,
    underlying_asset VARCHAR(50) NOT NULL,
    strike_price DECIMAL(15,4),
    barrier_level DECIMAL(15,4),
    coupon_rate DECIMAL(8,4),
    notional_amount DECIMAL(15,2),
    maturity_months INTEGER,
    issuer VARCHAR(100),
    currency VARCHAR(10) DEFAULT 'USD',
    created_by UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Insert sample products
INSERT INTO products (id, name, product_type, underlying_asset, strike_price, barrier_level, coupon_rate, notional_amount, maturity_months, issuer, created_by) VALUES
('750e8400-e29b-41d4-a716-446655440001', 'EUR/USD Digital Option Q1 2024', 'DIGITAL_OPTION', 'EUR/USD', 1.1000, 1.0500, 0.0800, 100000.00, 12, 'Dealer 1', '550e8400-e29b-41d4-a716-446655440002'),
('750e8400-e29b-41d4-a716-446655440002', 'S&P 500 Barrier Note', 'BARRIER_OPTION', 'SPY', 4200.00, 3800.00, 0.1200, 500000.00, 18, 'Dealer 2', '550e8400-e29b-41d4-a716-446655440002'),
('750e8400-e29b-41d4-a716-446655440003', 'GBP/USD Autocallable', 'AUTOCALLABLE', 'GBP/USD', 1.2500, 1.1800, 0.0600, 250000.00, 24, 'Dealer 1', '550e8400-e29b-41d4-a716-446655440002')
ON CONFLICT (id) DO NOTHING;

-- Connect to portfolio database and create tables
\c quantcrux_portfolio;

-- Positions table
CREATE TABLE IF NOT EXISTS positions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity DECIMAL(15,4) NOT NULL,
    entry_price DECIMAL(15,4) NOT NULL,
    current_price DECIMAL(15,4),
    total_investment DECIMAL(15,2) NOT NULL,
    current_value DECIMAL(15,2),
    unrealized_pnl DECIMAL(15,2) DEFAULT 0,
    realized_pnl DECIMAL(15,2) DEFAULT 0,
    position_type VARCHAR(10) DEFAULT 'LONG',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample positions
INSERT INTO positions (id, user_id, product_id, quantity, entry_price, current_price, total_investment, current_value, unrealized_pnl) VALUES
('850e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440001', '750e8400-e29b-41d4-a716-446655440001', 1000.0000, 100.0000, 125.0000, 100000.00, 125000.00, 25000.00),
('850e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440002', '750e8400-e29b-41d4-a716-446655440002', 500.0000, 98.5000, 102.3000, 49250.00, 51150.00, 1900.00)
ON CONFLICT (id) DO NOTHING;

-- Connect to booking database and create tables
\c quantcrux_booking;

-- Trades table
CREATE TABLE IF NOT EXISTS trades (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    product_id UUID NOT NULL,
    trade_type VARCHAR(10) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    notional DECIMAL(19,2) NOT NULL,
    entry_price DECIMAL(19,4) NOT NULL,
    current_price DECIMAL(19,4),
    pnl DECIMAL(19,2) DEFAULT 0,
    trade_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    settlement_date TIMESTAMP,
    maturity_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notes TEXT
);

-- Insert sample trades
INSERT INTO trades (id, user_id, product_id, trade_type, status, notional, entry_price, current_price, pnl, settlement_date) VALUES
('950e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440001', '750e8400-e29b-41d4-a716-446655440001', 'BUY', 'BOOKED', 100000.00, 1.1050, 1.1075, 2500.00, CURRENT_TIMESTAMP + INTERVAL '2 days'),
('950e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440002', '750e8400-e29b-41d4-a716-446655440002', 'BUY', 'CONFIRMED', 500000.00, 4200.00, 4250.00, 25000.00, CURRENT_TIMESTAMP + INTERVAL '2 days')
ON CONFLICT (id) DO NOTHING;

-- Connect to backtest database and create tables
\c quantcrux_backtest;

-- Backtest results table
CREATE TABLE IF NOT EXISTS backtest_results (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    strategy_id UUID NOT NULL,
    user_id UUID NOT NULL,
    symbol VARCHAR(20) NOT NULL,
    start_date VARCHAR(20),
    end_date VARCHAR(20),
    initial_capital DECIMAL(15,2),
    final_value DECIMAL(15,2),
    total_return DECIMAL(8,4),
    sharpe_ratio DECIMAL(8,4),
    max_drawdown DECIMAL(8,4),
    win_rate DECIMAL(8,4),
    total_trades INTEGER,
    profitable_trades INTEGER,
    equity_curve_json TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample backtest results
INSERT INTO backtest_results (id, strategy_id, user_id, symbol, start_date, end_date, initial_capital, final_value, total_return, sharpe_ratio, max_drawdown, win_rate, total_trades, profitable_trades, equity_curve_json) VALUES
('a50e8400-e29b-41d4-a716-446655440001', '650e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440003', 'SPY', '2023-01-01', '2024-01-01', 10000.00, 12500.00, 0.2500, 1.85, -0.08, 0.65, 75, 49, '[{"date":"2023-01-01","value":10000},{"date":"2023-06-01","value":11200},{"date":"2024-01-01","value":12500}]')
ON CONFLICT (id) DO NOTHING;

-- Connect to lifecycle database and create tables
\c quantcrux_lifecycle;

-- Lifecycle events table
CREATE TABLE IF NOT EXISTS lifecycle_events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    trade_id UUID NOT NULL,
    product_id UUID NOT NULL,
    event_type VARCHAR(50) NOT NULL,
    event_date TIMESTAMP,
    underlying_price DECIMAL(19,4),
    barrier_level DECIMAL(19,4),
    coupon_amount DECIMAL(19,2),
    status VARCHAR(20) DEFAULT 'PENDING',
    processed_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notes TEXT
);

-- Insert sample lifecycle events
INSERT INTO lifecycle_events (id, trade_id, product_id, event_type, event_date, underlying_price, barrier_level, status, processed_at) VALUES
('b50e8400-e29b-41d4-a716-446655440001', '950e8400-e29b-41d4-a716-446655440001', '750e8400-e29b-41d4-a716-446655440001', 'FIXING', CURRENT_TIMESTAMP, 1.1075, 1.0500, 'PROCESSED', CURRENT_TIMESTAMP),
('b50e8400-e29b-41d4-a716-446655440002', '950e8400-e29b-41d4-a716-446655440002', '750e8400-e29b-41d4-a716-446655440002', 'BARRIER_BREACH', CURRENT_TIMESTAMP - INTERVAL '1 day', 3750.00, 3800.00, 'PROCESSED', CURRENT_TIMESTAMP - INTERVAL '1 day')
ON CONFLICT (id) DO NOTHING;

-- Connect to analytics database and create tables
\c quantcrux_analytics;

-- Risk metrics table
CREATE TABLE IF NOT EXISTS risk_metrics (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    portfolio_id UUID,
    var_95 DECIMAL(15,2),
    var_99 DECIMAL(15,2),
    beta DECIMAL(8,4),
    sharpe_ratio DECIMAL(8,4),
    sortino_ratio DECIMAL(8,4),
    max_drawdown DECIMAL(8,4),
    volatility DECIMAL(8,4),
    correlation_spy DECIMAL(8,4),
    calculated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample risk metrics
INSERT INTO risk_metrics (id, user_id, var_95, var_99, beta, sharpe_ratio, sortino_ratio, max_drawdown, volatility, correlation_spy) VALUES
('c50e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440001', 15420.00, 28750.00, 1.23, 1.85, 2.12, -0.08, 0.16, 0.78),
('c50e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440002', 8750.00, 16200.00, 0.95, 2.15, 2.45, -0.05, 0.12, 0.65)
ON CONFLICT (id) DO NOTHING;

-- Connect to reporting database and create tables
\c quantcrux_reporting;

-- Reports table
CREATE TABLE IF NOT EXISTS reports (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    report_type VARCHAR(50) NOT NULL,
    report_name VARCHAR(200) NOT NULL,
    start_date DATE,
    end_date DATE,
    format VARCHAR(20) DEFAULT 'PDF',
    status VARCHAR(20) DEFAULT 'GENERATED',
    file_path VARCHAR(500),
    file_size_kb INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample reports
INSERT INTO reports (id, user_id, report_type, report_name, start_date, end_date, status, file_size_kb) VALUES
('d50e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440001', 'portfolio', 'Portfolio Summary - December 2024', '2024-12-01', '2024-12-31', 'READY', 2400),
('d50e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440002', 'risk', 'Risk Analysis - Q4 2024', '2024-10-01', '2024-12-31', 'READY', 1800)
ON CONFLICT (id) DO NOTHING;

-- Connect to pricing database and create tables
\c quantcrux_pricing;

-- Pricing calculations table
CREATE TABLE IF NOT EXISTS pricing_calculations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id UUID NOT NULL,
    product_type VARCHAR(50) NOT NULL,
    spot_price DECIMAL(15,4),
    strike_price DECIMAL(15,4),
    barrier_level DECIMAL(15,4),
    volatility DECIMAL(8,4),
    risk_free_rate DECIMAL(8,4),
    time_to_maturity DECIMAL(8,4),
    calculated_price DECIMAL(15,4),
    delta_greek DECIMAL(8,6),
    gamma_greek DECIMAL(8,6),
    vega_greek DECIMAL(8,6),
    theta_greek DECIMAL(8,6),
    confidence_interval DECIMAL(8,4),
    num_simulations INTEGER,
    calculation_method VARCHAR(50),
    calculated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample pricing calculations
INSERT INTO pricing_calculations (id, product_id, product_type, spot_price, strike_price, barrier_level, volatility, risk_free_rate, time_to_maturity, calculated_price, delta_greek, gamma_greek, vega_greek, theta_greek, confidence_interval, num_simulations, calculation_method) VALUES
('e50e8400-e29b-41d4-a716-446655440001', '750e8400-e29b-41d4-a716-446655440001', 'DIGITAL_OPTION', 1.1075, 1.1000, 1.0500, 0.2000, 0.0500, 1.0000, 98.7500, 0.650000, 0.020000, 0.150000, -0.050000, 1.2500, 50000, 'MONTE_CARLO'),
('e50e8400-e29b-41d4-a716-446655440002', '750e8400-e29b-41d4-a716-446655440002', 'BARRIER_OPTION', 4250.00, 4200.00, 3800.00, 0.1800, 0.0500, 1.5000, 102.3000, 0.720000, 0.015000, 0.180000, -0.040000, 1.8500, 75000, 'MONTE_CARLO')
ON CONFLICT (id) DO NOTHING;

-- Create indexes for better performance
\c quantcrux_auth;
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

\c quantcrux_user;
CREATE INDEX IF NOT EXISTS idx_user_profiles_user_id ON user_profiles(user_id);

\c quantcrux_strategy;
CREATE INDEX IF NOT EXISTS idx_strategies_created_by ON strategies(created_by);
CREATE INDEX IF NOT EXISTS idx_strategy_assets_strategy_id ON strategy_assets(strategy_id);

\c quantcrux_product;
CREATE INDEX IF NOT EXISTS idx_products_created_by ON products(created_by);
CREATE INDEX IF NOT EXISTS idx_products_type ON products(product_type);
CREATE INDEX IF NOT EXISTS idx_products_underlying ON products(underlying_asset);

\c quantcrux_portfolio;
CREATE INDEX IF NOT EXISTS idx_positions_user_id ON positions(user_id);
CREATE INDEX IF NOT EXISTS idx_positions_product_id ON positions(product_id);
CREATE INDEX IF NOT EXISTS idx_positions_active ON positions(is_active);

\c quantcrux_booking;
CREATE INDEX IF NOT EXISTS idx_trades_user_id ON trades(user_id);
CREATE INDEX IF NOT EXISTS idx_trades_product_id ON trades(product_id);
CREATE INDEX IF NOT EXISTS idx_trades_status ON trades(status);
CREATE INDEX IF NOT EXISTS idx_trades_date ON trades(trade_date);

\c quantcrux_backtest;
CREATE INDEX IF NOT EXISTS idx_backtest_strategy_id ON backtest_results(strategy_id);
CREATE INDEX IF NOT EXISTS idx_backtest_user_id ON backtest_results(user_id);

\c quantcrux_lifecycle;
CREATE INDEX IF NOT EXISTS idx_lifecycle_trade_id ON lifecycle_events(trade_id);
CREATE INDEX IF NOT EXISTS idx_lifecycle_product_id ON lifecycle_events(product_id);
CREATE INDEX IF NOT EXISTS idx_lifecycle_status ON lifecycle_events(status);

\c quantcrux_analytics;
CREATE INDEX IF NOT EXISTS idx_risk_metrics_user_id ON risk_metrics(user_id);
CREATE INDEX IF NOT EXISTS idx_risk_metrics_calculated_at ON risk_metrics(calculated_at);

\c quantcrux_reporting;
CREATE INDEX IF NOT EXISTS idx_reports_user_id ON reports(user_id);
CREATE INDEX IF NOT EXISTS idx_reports_type ON reports(report_type);
CREATE INDEX IF NOT EXISTS idx_reports_created_at ON reports(created_at);

\c quantcrux_pricing;
CREATE INDEX IF NOT EXISTS idx_pricing_product_id ON pricing_calculations(product_id);
CREATE INDEX IF NOT EXISTS idx_pricing_calculated_at ON pricing_calculations(calculated_at);