'use client';

import { JSX, useState } from 'react';

import { Typography } from '@mui/material';

import SignUp from '../SignUp';
import SignIn from '../SignIn';

import styles from '../../styles/Authenticator.module.css';

interface AuthenticatorProps {
  children?: React.ReactNode;
}

const SignInSignUpForms = ({setMessage, setSession, session, children}: { session?: unknown; children?: React.ReactNode; setSession: (session?: unknown) => void; setMessage: (message: string) => void }) => {
  const [showSingUp, setShowSignUp] = useState<boolean>(false);

  if (session) {
    return children ?? <></>;
  }
  return showSingUp ? (
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
};

const Authenticator = (props: AuthenticatorProps): JSX.Element => {
  const [message, setMessage] = useState<string>('');
  const [session, setSession] = useState<unknown | undefined>(undefined);

  return (
    <div className={styles.authenticator}>
      <SignInSignUpForms session={session} setSession={setSession} setMessage={setMessage}>{props.children}</SignInSignUpForms>
      <Typography variant="h6" component="h6" color="error">
        {message}
      </Typography>
    </div>
  );
};

export default Authenticator;
