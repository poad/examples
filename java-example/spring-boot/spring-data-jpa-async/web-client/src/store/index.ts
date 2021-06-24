import {
  combineReducers, createStore, applyMiddleware, Store, EmptyObject, AnyAction,
} from 'redux';
import thunk from 'redux-thunk';
import { Reducer } from './reducer';
import { State } from './comment/types';

export type AppState = {
  state: State
};

export const store = createStore(
  combineReducers<AppState>({
    state: Reducer,
  }),
  applyMiddleware(thunk),
);

export const makeStore = (): Store<EmptyObject & AppState, AnyAction> & {
  dispatch: unknown;
} => createStore(
  combineReducers<AppState>({
    state: Reducer,
  }),
  applyMiddleware(thunk),
);
