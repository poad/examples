export interface Config {
  endpoint: string;
}

const config: Config = {
  endpoint: process.env.API_ENDPOINT
    ? process.env.API_ENDPOINT
    : 'http://localhost/rest/',
};

export default config;
