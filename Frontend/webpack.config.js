module.exports = {
    resolve: {
      alias: {
        'crypto': 'crypto-browserify',
        'stream': 'stream-browserify',
        'http': 'stream-http',
        'https': 'https-browserify',
        'os': 'os-browserify/browser',
        'buffer': 'buffer',
      },
      fallback: {
        "crypto": require.resolve("crypto-browserify"),
        "stream": require.resolve("stream-browserify"),
        "http": require.resolve("stream-http"),
        "https": require.resolve("https-browserify"),
        "os": require.resolve("os-browserify/browser"),
        "buffer": require.resolve("buffer")
      }
    }
  };
  