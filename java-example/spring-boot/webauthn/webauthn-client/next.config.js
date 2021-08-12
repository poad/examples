module.exports = {
  async rewrites() {
    return [
      {
        source: '/api/:path*',
        destination: 'http://localhost:8080/:path*',
      },
    ]
  },
  webpack5: true,
  reactStrictMode: true,
  // webpack: {
  //     rules: [
  //         {
  //         test: /\.m?js$/,
  //         exclude: /(node_modules|bower_components)/,
  //         use: {
  //             // `.swcrc` can be used to configure swc
  //             loader: "swc-loader"
  //         }
  //         }
  //     ]
  // },
};
