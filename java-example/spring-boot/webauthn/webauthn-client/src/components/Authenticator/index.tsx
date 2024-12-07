'use client';

import { JSX, ReactNode, useState } from 'react';

import { Typography } from '@mui/material';

import SignUp from '../SignUp';
import SignIn from '../SignIn';

import styles from '../../styles/Authenticator.module.css';

interface AuthenticatorProps {
  children?: ReactNode;
}

const Authenticator = (props: AuthenticatorProps): JSX.Element => {
  const [showSingUp, setShowSignUp] = useState<boolean>(false);
  const [message, setMessage] = useState<string>('');
  const [session, setSession] = useState<unknown | undefined>(undefined);

  const SignInSignUpForms = (): JSX.Element =>
    showSingUp ? (
      <SignUp
        onShowSignIn={() => {
          setMessage('');
          setShowSignUp(false);
        }}
        onSignUped={() => setSession({})}
        onError={setMessage}
      />
    ) : (
      <SignIn
        onShowSignUp={() => {
          setMessage('');
          setShowSignUp(true);
        }}
        onAuthenticated={() => setSession({})}
        onError={setMessage}
      />
    );

  return (
    <div className={styles.authenticator}>
      {session !== undefined ? props.children : <SignInSignUpForms />}
      <Typography variant="h6" component="h6" color="error">
        {message}
      </Typography>
    </div>
  );
};

export default Authenticator;
