// eslint-disable-next-line @typescript-eslint/no-require-imports
const withBundleAnalyzer = require('@next/bundle-analyzer')({
  enabled: process.env.ANALYZE === 'true',
});

module.exports = withBundleAnalyzer(
  /** @type {import('next').NextConfig} */
  {
    output: 'export',
    reactStrictMode: true,
    compiler: {
      emotion: true,
    },
    experimental: {
      esmExternals: true,
    },
    eslint: {
      ignoreDuringBuilds: true,
    },
  }
);
