// Configuration variables

const dev = {
  API_URL: 'http://localhost:8081/api',
  FRONT_URL: 'http://localhost:5173',
};

const prod = {
  API_URL: 'https://api.irigo-map.jules-dempt.com/api',
  FRONT_URL: 'https://irigo-map.jules-dempt.com/',
};

export const config = process.env.NODE_ENV === 'development' ? dev : prod;
