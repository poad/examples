import * as React from 'react';
import type { AppProps } from 'next/app';

import Head from 'next/head';
import { useEffect } from 'react';

import CssBaseline from '@material-ui/core/CssBaseline';
import { ThemeProvider } from '@material-ui/styles';
import Authenticator from 'components/Authenticator';
import theme from '../styles/theme';

const App = ({ Component, pageProps }: AppProps): JSX.Element => {
  useEffect(() => {
    // Remove the server-side injected CSS.
    const jssStyles = document.querySelector('#jss-server-side');
    if (jssStyles && jssStyles.parentNode) {
      jssStyles.parentNode.removeChild(jssStyles);
    }
  }, []);

  return (
    <React.Fragment>
      <ThemeProvider theme={theme}>
        {/* CssBaseline kickstart an elegant, consistent, and simple baseline to build upon. */}
        <Head>
          <title>Home</title>
          <link rel="icon" href="/favicon.ico" />
        </Head>
        {/* ThemeProvider makes the theme available down the React
            tree thanks to React context. */}

        <CssBaseline />
        <Authenticator>
          <Component {...pageProps} />
        </Authenticator>
      </ThemeProvider>
    </React.Fragment>
  );
};

export default App;
