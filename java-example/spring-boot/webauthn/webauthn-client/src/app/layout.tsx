'use client';

import { ReactNode } from 'react';
import { CssBaseline, ThemeProvider } from '@mui/material';
import Authenticator from '../components/Authenticator';
import theme from './styles/theme';
import { Roboto } from 'next/font/google';
import StyledJsxRegistry from './registry';
import './styles/Layout.module.css';

const roboto300 = Roboto({
  weight: '400',
  subsets: ['latin'],
  display: 'swap',
});

const roboto400 = Roboto({
  weight: '400',
  subsets: ['latin'],
  display: 'swap',
});

const roboto500 = Roboto({
  weight: '400',
  subsets: ['latin'],
  display: 'swap',
});

const roboto700 = Roboto({
  weight: '400',
  subsets: ['latin'],
  display: 'swap',
});

export default function Layout({ children }: { children: ReactNode }) {
  return (
    <html lang="en">
      <head>
        {/* PWA primary color */}
        <meta name="theme-color" content={theme.palette.primary.main} />
      </head>
      <body className={`${roboto300.className} ${roboto400.className} ${roboto500.className} ${roboto700.className}`}>
        <StyledJsxRegistry>
          <ThemeProvider theme={theme}>
            {/* CssBaseline kickstart an elegant, consistent, and simple baseline to build upon. */}
            <CssBaseline />
            <Authenticator>{children}</Authenticator>
          </ThemeProvider>
        </StyledJsxRegistry>
      </body>
    </html>
  );
}
