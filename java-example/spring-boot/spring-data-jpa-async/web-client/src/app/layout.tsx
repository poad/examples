'use client';
import { ReactNode, useState } from 'react';
import Head from 'next/head';
import {
  AppBar,
  Box,
  CssBaseline,
  Divider,
  Drawer,
  IconButton,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  ThemeProvider,
  Toolbar,
  Typography,
} from '@mui/material';
import themes from './styles/theme';
import DashboardIcon from '@mui/icons-material/Dashboard';
import MenuIcon from '@mui/icons-material/Menu';
import theme from './styles/theme';
import './styles/Layout.module.css';

const drawerWidth = 240;

function Base({ children }: { children: ReactNode }) {
  const [drawerOpen, setDrawerOpen] = useState(false);

  function handleDrawerToggle() {
    setDrawerOpen(!drawerOpen);
  }

  return (
    <Box sx={{ display: 'flex', color: '#fff' }}>
      <CssBaseline />
      <AppBar
        position="fixed"
        sx={{ marginLeft: drawerWidth, width: `calc(100% - ${drawerWidth}px)` }}
      >
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            edge="start"
            onClick={handleDrawerToggle}
            sx={{ marginRight: '2em', display: 'none' }}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap></Typography>
        </Toolbar>
      </AppBar>
      <Drawer variant="permanent" open>
        <Divider />
        <List sx={{ width: drawerWidth }}>
          {['Info'].map((text) => (
            <ListItem button key={text} sx={{ color: '#2d2d2d' }}>
              <ListItemIcon sx={{ color: '#2d2d2d' }}>
                <DashboardIcon />
              </ListItemIcon>
              <ListItemText primary={text} />
            </ListItem>
          ))}
        </List>
      </Drawer>
      <Box style={{ flexGrow: 1 }}>
        <Box />
        {children}
      </Box>
    </Box>
  );
}

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
        <ThemeProvider theme={themes}>
          <Base>{children}</Base>
        </ThemeProvider>
      </body>
    </html>
  );
}
