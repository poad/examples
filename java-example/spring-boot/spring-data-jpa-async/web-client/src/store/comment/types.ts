export const LIST_COMMENT = 'LIST_COMMENT';
export const SEND_COMMENT = 'SEND_COMMENT';
export const UPDATE_COMMENT = 'UPDATE_COMMENT';
export const DELETE_COMMENT = 'DELETE_COMMENT';

export interface Comment {
  id?: string | undefined
  comment: string
}

export type CommentState = Array<Comment>;

export interface State {
  comments: CommentState
  comment: string
}

interface ListCommentAction {
  type: typeof LIST_COMMENT
}
interface SendCommentAction {
  type: typeof SEND_COMMENT
  payload: Comment
}
interface UpdateCommentAction {
  type: typeof UPDATE_COMMENT
  meta: {
    payload: Comment
  }
}
interface DeleteCommentAction {
  type: typeof DELETE_COMMENT
  meta: {
    id: string
  }
}
export type CommentActionTypes =
  | ListCommentAction
  | SendCommentAction
  | UpdateCommentAction
  | DeleteCommentAction;
