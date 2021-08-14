import { green } from '@material-ui/core/colors';
import { createTheme, ThemeOptions } from '@material-ui/core';

// A theme with custom primary and secondary color.
// It's optional.

const options: ThemeOptions = {
  palette: {
    primary: {
      main: '#2d2d2d',
    },
    secondary: {
      light: green[300],
      main: green[500],
      dark: green[700],
    },
  },
};

const theme = createTheme(options);
export type Theme = typeof theme;
export default theme;
