const withBundleAnalyzer = require('@next/bundle-analyzer')({
  enabled: process.env.ANALYZE === 'true',
});

module.exports = withBundleAnalyzer(
  /** @type {import('next').NextConfig} */
  {
    output: 'export',
    reactStrictMode: true,
    swcMinify: true,
    experimental: {
      esmExternals: true,
    }
  }
);
