export interface Comment {
  id?: string | undefined;
  comment: string;
}

export type CommentState = Array<Comment>;

export interface State {
  comments: CommentState;
  comment: string;
}
