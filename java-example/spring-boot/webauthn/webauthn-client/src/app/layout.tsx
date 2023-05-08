'use client';
import { ReactNode } from 'react';
import { CssBaseline, ThemeProvider } from '@mui/material';
import Authenticator from '../components/Authenticator';
import theme from './styles/theme';
import './styles/Layout.module.css';
import Head from 'next/head';

export default function Layout({ children }: { children: ReactNode }) {
  return (
    <html lang="en">
      <Head>
        {/* PWA primary color */}
        <meta name="theme-color" content={theme.palette.primary.main} />
        {/* eslint-disable-next-line @next/next/no-page-custom-font */}
        <link
          rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
        />
      </Head>
      <body>
        <ThemeProvider theme={theme}>
          {/* CssBaseline kickstart an elegant, consistent, and simple baseline to build upon. */}
          <CssBaseline />
          <Authenticator>{children}</Authenticator>
        </ThemeProvider>
      </body>
    </html>
  );
}
