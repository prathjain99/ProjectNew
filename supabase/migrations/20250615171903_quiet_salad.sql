-- Create databases for different services
CREATE DATABASE IF NOT EXISTS quantcrux_auth;
CREATE DATABASE IF NOT EXISTS quantcrux_strategy;
CREATE DATABASE IF NOT EXISTS quantcrux_product;
CREATE DATABASE IF NOT EXISTS quantcrux_portfolio;
CREATE DATABASE IF NOT EXISTS quantcrux_analytics;
CREATE DATABASE IF NOT EXISTS quantcrux_booking;
CREATE DATABASE IF NOT EXISTS quantcrux_reporting;

-- Create user if not exists
CREATE USER IF NOT EXISTS 'quantcrux'@'%' IDENTIFIED BY 'quantcrux123';

-- Grant permissions
GRANT ALL PRIVILEGES ON quantcrux_auth.* TO 'quantcrux'@'%';
GRANT ALL PRIVILEGES ON quantcrux_strategy.* TO 'quantcrux'@'%';
GRANT ALL PRIVILEGES ON quantcrux_product.* TO 'quantcrux'@'%';
GRANT ALL PRIVILEGES ON quantcrux_portfolio.* TO 'quantcrux'@'%';
GRANT ALL PRIVILEGES ON quantcrux_analytics.* TO 'quantcrux'@'%';
GRANT ALL PRIVILEGES ON quantcrux_booking.* TO 'quantcrux'@'%';
GRANT ALL PRIVILEGES ON quantcrux_reporting.* TO 'quantcrux'@'%';

FLUSH PRIVILEGES;