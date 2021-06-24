import App, { AppContext, AppInitialProps, AppProps } from 'next/app';
import withRedux from 'next-redux-wrapper';
import * as React from 'react';
import { Provider } from 'react-redux';
import Head from 'next/head';
import { ThemeProvider } from '@material-ui/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import { useEffect } from 'react';
import { store, makeStore } from '../store';
import theme from '../styles/theme';

interface MyAppProps extends AppInitialProps, AppProps {}

const MyApp = (props: MyAppProps) => {
  useEffect(() => {
    // Remove the server-side injected CSS.
    const jssStyles = document.querySelector('#jss-server-side');
    if (jssStyles && jssStyles.parentNode) {
      jssStyles.parentNode.removeChild(jssStyles);
    }
  }, []);

  const { Component, pageProps } = props;
  return (
    <Provider store={store}>
      <ThemeProvider theme={theme}>
        {/* CssBaseline kickstart an elegant, consistent, and simple baseline to build upon. */}
        <Head>
          <title>Home</title>
          <link rel="icon" href="/favicon.ico" />
        </Head>
        {/* ThemeProvider makes the theme available down the React
            tree thanks to React context. */}

        <CssBaseline />
        <Component {...pageProps} />
      </ThemeProvider>
    </Provider>
  );
};

type Context = AppContext;

App.getInitialProps = async ({ Component, ctx }: Context) => {
  const pageProps = Component.getInitialProps
    ? await Component.getInitialProps(ctx)
    : {};
  return { pageProps };
};

export default withRedux(makeStore)(MyApp);
