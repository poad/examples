import { createMuiTheme } from '@material-ui/core/styles';
import { green } from '@material-ui/core/colors';
import { ThemeOptions } from '@material-ui/core';

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

const theme = createMuiTheme(options);
export type Theme = typeof theme;
export default theme;
