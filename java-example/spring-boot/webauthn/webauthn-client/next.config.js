/** @type {import('next').NextConfig} */
const withPlugins = require('next-compose-plugins');
const withBundleAnalyzer = require('@next/bundle-analyzer')({
  enabled: process.env.ANALYZE === 'true',
});

module.exports = withBundleAnalyzer(
  {
    reactStrictMode: true,
    swcMinify: true,
    experimental: {
      esmExternals: true,
    }
  }
);
