import { reducerWithInitialState } from 'typescript-fsa-reducers';
import { State } from './comment/types';

export const initialState: State = {
  comments: [],
  comment: '',
};

export const Reducer = reducerWithInitialState(initialState);
