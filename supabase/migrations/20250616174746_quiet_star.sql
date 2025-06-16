-- Initialize databases for QuantCrux microservices
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

-- Grant all privileges to quantcrux user
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