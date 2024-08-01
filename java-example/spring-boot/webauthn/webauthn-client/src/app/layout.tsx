'use client';
import { ReactNode } from 'react';
import { CssBaseline, ThemeProvider } from '@mui/material';
import Authenticator from '../components/Authenticator';
import theme from './styles/theme';
import './styles/Layout.module.css';
import StyledJsxRegistry from './registry';

export default function Layout({ children }: { children: ReactNode }) {
  return (
    <html lang="en">
      <head>
        {/* PWA primary color */}
        <meta name="theme-color" content={theme.palette.primary.main} />
        { }
        <link
          rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
        />
      </head>
      <body>
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
