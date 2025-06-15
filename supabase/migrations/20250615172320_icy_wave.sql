-- Create databases for different services
CREATE DATABASE quantcrux_auth;
CREATE DATABASE quantcrux_strategy;
CREATE DATABASE quantcrux_product;
CREATE DATABASE quantcrux_portfolio;
CREATE DATABASE quantcrux_analytics;
CREATE DATABASE quantcrux_booking;
CREATE DATABASE quantcrux_reporting;

-- Grant permissions to quantcrux user (user is created by POSTGRES_USER env var)
GRANT ALL PRIVILEGES ON DATABASE quantcrux_auth TO quantcrux;
GRANT ALL PRIVILEGES ON DATABASE quantcrux_strategy TO quantcrux;
GRANT ALL PRIVILEGES ON DATABASE quantcrux_product TO quantcrux;
GRANT ALL PRIVILEGES ON DATABASE quantcrux_portfolio TO quantcrux;
GRANT ALL PRIVILEGES ON DATABASE quantcrux_analytics TO quantcrux;
GRANT ALL PRIVILEGES ON DATABASE quantcrux_booking TO quantcrux;
GRANT ALL PRIVILEGES ON DATABASE quantcrux_reporting TO quantcrux;