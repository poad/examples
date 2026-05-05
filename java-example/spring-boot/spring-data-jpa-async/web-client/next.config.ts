import withBundleAnalyzer from '@next/bundle-analyzer';

export default withBundleAnalyzer({
  enabled: process.env.ANALYZE === 'true',
})(
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
  },
);
