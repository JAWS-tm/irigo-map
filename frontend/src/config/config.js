// Configuration variables

const dev = {
  API_URL: 'http://localhost:8081/api',
  FRONT_URL: 'http://localhost:5173',
};

const prod = {
  API_URL: 'http://51.75.207.170/api',
  FRONT_URL: 'http://51.75.207.170',
};

export const config = process.env.NODE_ENV === 'development' ? dev : prod;
