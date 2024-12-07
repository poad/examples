'use client';

import { JSX } from 'react';
import { Typography } from '@mui/material';
import styles from './styles/Home.module.css';

const Home = (): JSX.Element => (
  <div className={styles.container}>
    <head>
      <title>WebAuthn example</title>
      <link rel="icon" href="/favicon.ico" />
    </head>

    <main className={styles.main}>
      <>
        <Typography variant="h1" component="h1" color="primary">
          Hello!
        </Typography>
      </>
    </main>

    <footer className={styles.footer}></footer>
  </div>
);

export default Home;
