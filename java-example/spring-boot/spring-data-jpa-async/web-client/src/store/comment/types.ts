export interface Comment {
  id?: string | undefined;
  comment: string;
}

export type CommentState = Comment[];

export interface State {
  comments: CommentState;
  comment: string;
}
