-- Create databases for different services
CREATE DATABASE quantcrux_auth;
CREATE DATABASE quantcrux_strategy;
CREATE DATABASE quantcrux_product;
CREATE DATABASE quantcrux_portfolio;
CREATE DATABASE quantcrux_analytics;
CREATE DATABASE quantcrux_booking;
CREATE DATABASE quantcrux_reporting;
CREATE DATABASE quantcrux_user;
CREATE DATABASE quantcrux_backtest;
CREATE DATABASE quantcrux_lifecycle;
CREATE DATABASE quantcrux_pricing;

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
GRANT ALL PRIVILEGES ON DATABASE quantcrux_pricing TO quantcrux;