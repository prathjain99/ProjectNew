import React, { useEffect, useState } from 'react';
import { motion } from 'framer-motion';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, AreaChart, Area } from 'recharts';
import { TrendingUp, TrendingDown, DollarSign, Activity, Briefcase, Target, AlertTriangle, Clock } from 'lucide-react';
import { useAuth } from '../../contexts/AuthContext';
import axios from 'axios';

const Overview: React.FC = () => {
  const { user } = useAuth();
  const [portfolioData, setPortfolioData] = useState<any>(null);
  const [marketData, setMarketData] = useState<any[]>([]);

  useEffect(() => {
    fetchPortfolioData();
    fetchMarketData();
  }, []);

  const fetchPortfolioData = async () => {
    try {
      const response = await axios.get('http://localhost:3001/api/portfolio');
      setPortfolioData(response.data);
    } catch (error) {
      console.error('Failed to fetch portfolio data:', error);
    }
  };

  const fetchMarketData = async () => {
    try {
      const response = await axios.get('http://localhost:3001/api/market-data/SPY?days=30');
      const formattedData = response.data.map((item: any) => ({
        date: item.date,
        price: item.close,
        volume: item.volume / 1000000
      }));
      setMarketData(formattedData);
    } catch (error) {
      console.error('Failed to fetch market data:', error);
    }
  };

  const stats = [
    {
      label: 'Total Portfolio Value',
      value: portfolioData ? `$${portfolioData.summary.total_value.toLocaleString()}` : '$0',
      change: portfolioData ? `${portfolioData.summary.pnl_percentage.toFixed(2)}%` : '0%',
      positive: portfolioData ? portfolioData.summary.total_pnl >= 0 : true,
      icon: DollarSign
    },
    {
      label: 'Total P&L',
      value: portfolioData ? `$${portfolioData.summary.total_pnl.toLocaleString()}` : '$0',
      change: 'vs Entry',
      positive: portfolioData ? portfolioData.summary.total_pnl >= 0 : true,
      icon: TrendingUp
    },
    {
      label: 'Active Positions',
      value: portfolioData ? portfolioData.positions.length.toString() : '0',
      change: 'Products',
      positive: true,
      icon: Briefcase
    },
    {
      label: 'Market Exposure',
      value: portfolioData ? `$${portfolioData.summary.total_investment.toLocaleString()}` : '$0',
      change: 'Invested',
      positive: true,
      icon: Activity
    }
  ];

  const notifications = [
    {
      type: 'alert',
      message: 'EUR/USD barrier option approaching knock-out level',
      time: '2 min ago',
      icon: AlertTriangle,
      color: 'text-red-400'
    },
    {
      type: 'info',
      message: 'Backtest completed for momentum strategy',
      time: '15 min ago',
      icon: Target,
      color: 'text-blue-400'
    },
    {
      type: 'success',
      message: 'Digital option auto-called - $50K payout',
      time: '1 hour ago',
      icon: TrendingUp,
      color: 'text-green-400'
    }
  ];

  return (
    <div className="space-y-6">
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        className="flex items-center justify-between"
      >
        <div>
          <h1 className="text-3xl font-bold text-white">Portfolio Overview</h1>
          <p className="text-gray-400 mt-1">Track your quantitative strategies and structured products</p>
        </div>
        <div className="flex items-center space-x-2 text-sm">
          <Clock className="h-4 w-4 text-gray-400" />
          <span className="text-gray-400">Last updated: {new Date().toLocaleTimeString()}</span>
        </div>
      </motion.div>

      {/* Stats Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {stats.map((stat, index) => (
          <motion.div
            key={stat.label}
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: index * 0.1 }}
            className="bg-gray-800 border border-gray-700 rounded-xl p-6 hover:border-blue-500/50 transition-colors"
          >
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-400 text-sm">{stat.label}</p>
                <p className="text-2xl font-bold text-white mt-1">{stat.value}</p>
                <div className="flex items-center mt-2">
                  {stat.positive ? (
                    <TrendingUp className="h-4 w-4 text-green-400 mr-1" />
                  ) : (
                    <TrendingDown className="h-4 w-4 text-red-400 mr-1" />
                  )}
                  <span className={`text-sm ${stat.positive ? 'text-green-400' : 'text-red-400'}`}>
                    {stat.change}
                  </span>
                </div>
              </div>
              <div className="p-3 bg-blue-600/20 rounded-lg">
                <stat.icon className="h-6 w-6 text-blue-400" />
              </div>
            </div>
          </motion.div>
        ))}
      </div>

      {/* Charts Section */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <motion.div
          initial={{ opacity: 0, x: -20 }}
          animate={{ opacity: 1, x: 0 }}
          transition={{ delay: 0.3 }}
          className="bg-gray-800 border border-gray-700 rounded-xl p-6"
        >
          <h3 className="text-xl font-semibold text-white mb-4">Market Performance (SPY)</h3>
          <ResponsiveContainer width="100%" height={300}>
            <AreaChart data={marketData}>
              <CartesianGrid strokeDasharray="3 3" stroke="#374151" />
              <XAxis 
                dataKey="date" 
                stroke="#9CA3AF"
                tick={{ fontSize: 12 }}
                tickFormatter={(value) => new Date(value).toLocaleDateString().slice(0, -5)}
              />
              <YAxis stroke="#9CA3AF" tick={{ fontSize: 12 }} />
              <Tooltip 
                contentStyle={{ 
                  backgroundColor: '#1F2937', 
                  border: '1px solid #374151',
                  borderRadius: '8px',
                  color: '#fff'
                }}
              />
              <Area
                type="monotone"
                dataKey="price"
                stroke="#3B82F6"
                fill="url(#colorPrice)"
                strokeWidth={2}
              />
              <defs>
                <linearGradient id="colorPrice" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="5%" stopColor="#3B82F6" stopOpacity={0.3}/>
                  <stop offset="95%" stopColor="#3B82F6" stopOpacity={0}/>
                </linearGradient>
              </defs>
            </AreaChart>
          </ResponsiveContainer>
        </motion.div>

        <motion.div
          initial={{ opacity: 0, x: 20 }}
          animate={{ opacity: 1, x: 0 }}
          transition={{ delay: 0.4 }}
          className="bg-gray-800 border border-gray-700 rounded-xl p-6"
        >
          <h3 className="text-xl font-semibold text-white mb-4">Recent Notifications</h3>
          <div className="space-y-4">
            {notifications.map((notification, index) => (
              <div key={index} className="flex items-start space-x-3 p-3 bg-gray-700/50 rounded-lg">
                <notification.icon className={`h-5 w-5 mt-0.5 ${notification.color}`} />
                <div className="flex-1">
                  <p className="text-white text-sm">{notification.message}</p>
                  <p className="text-gray-400 text-xs mt-1">{notification.time}</p>
                </div>
              </div>
            ))}
          </div>
        </motion.div>
      </div>

      {/* Quick Actions */}
      {user?.role !== 'client' && (
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ delay: 0.5 }}
          className="bg-gray-800 border border-gray-700 rounded-xl p-6"
        >
          <h3 className="text-xl font-semibold text-white mb-4">Quick Actions</h3>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <motion.button
              whileHover={{ scale: 1.02 }}
              whileTap={{ scale: 0.98 }}
              className="p-4 bg-gradient-to-r from-blue-600 to-blue-700 rounded-lg text-white text-left hover:from-blue-700 hover:to-blue-800 transition-all"
            >
              <h4 className="font-semibold">Create Strategy</h4>
              <p className="text-sm text-blue-200 mt-1">Build new quantitative trading strategy</p>
            </motion.button>
            
            <motion.button
              whileHover={{ scale: 1.02 }}
              whileTap={{ scale: 0.98 }}
              className="p-4 bg-gradient-to-r from-yellow-600 to-yellow-700 rounded-lg text-white text-left hover:from-yellow-700 hover:to-yellow-800 transition-all"
            >
              <h4 className="font-semibold">Run Backtest</h4>
              <p className="text-sm text-yellow-200 mt-1">Test strategy performance</p>
            </motion.button>
            
            {user?.role === 'portfolio_manager' && (
              <motion.button
                whileHover={{ scale: 1.02 }}
                whileTap={{ scale: 0.98 }}
                className="p-4 bg-gradient-to-r from-green-600 to-green-700 rounded-lg text-white text-left hover:from-green-700 hover:to-green-800 transition-all"
              >
                <h4 className="font-semibold">Create Product</h4>
                <p className="text-sm text-green-200 mt-1">Design structured financial product</p>
              </motion.button>
            )}
          </div>
        </motion.div>
      )}
    </div>
  );
};

export default Overview;