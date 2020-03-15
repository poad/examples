import {
  Comment,
  LIST_COMMENT,
  SEND_COMMENT,
  UPDATE_COMMENT,
  DELETE_COMMENT,
  CommentActionTypes
} from './types'

export function listComment(): CommentActionTypes {
  return {
    type: LIST_COMMENT
  }
}

export function sendComment(newComment: Comment): CommentActionTypes {
  return {
    type: SEND_COMMENT,
    payload: newComment
  }
}

export function updateComment(newComment: Comment): CommentActionTypes {
  return {
    type: UPDATE_COMMENT,
    meta: {
      payload: newComment
    }
  }
}

export function deleteComment(id: string): CommentActionTypes {
  return {
    type: DELETE_COMMENT,
    meta: {
      id
    }
  }
}
