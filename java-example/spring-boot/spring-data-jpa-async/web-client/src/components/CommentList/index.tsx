'use client';
import { ChangeEvent, JSX, useState } from 'react';
import { Button, TextField } from '@mui/material';
import ListNode from './ListNode';
import { State, Comment } from '../../store/comment/types';
import RestClient from './RestClient';

interface Props {
  comments: Comment[];
  comment: string;
}

function CommentList(props: Props): JSX.Element {
  const [state, setState] = useState<State>({
    comments: props.comments,
    comment: props.comment,
  });
  const client = new RestClient();

  function addComment(comment: string): Promise<Comment> {
    return client.add(comment);
  };

  function removeComment(id: string): void {
    const { comments } = state;
    const target = comments.findIndex((comment) => comment.id === id);
    if (target === 0) {
      comments.shift();
      setState({
        comments,
        comment: '',
      });
    } else if (target > 0) {
      comments.splice(target, 1);
      setState({
        comments,
        comment: '',
      });
    }
  };

  function list(): JSX.Element[] {
    return state.comments
      .filter((comment) => comment.id)
      .map((comment) => (
        <ListNode
          key={comment.id}
          id={comment.id ? comment.id : ''}
          comment={comment.comment}
          text=""
          client={client}
          onDelete={removeComment}
        />
      ));
  };

  function add(): void {
    const { comments } = state;
    addComment(state.comment).then((comment) => {
      comments.push(comment);
      setState({
        comments,
        comment: '',
      });
      return;
    }).catch(() => {
      // pass
    });
  };

  function update(event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>): void {
    setState({
      comments: state.comments,
      comment: event.currentTarget.value,
    });
  };

  return (
    <div>
      <form>
        <table>
          <tbody>
            <tr>
              <td>メッセージ</td>
              <td>
                <TextField
                  type="text"
                  name="comment"
                  value={state.comment}
                  onChange={update}
                />
              </td>
              <td>
                <Button onClick={(): void => add()}>追加</Button>
              </td>
            </tr>
          </tbody>
        </table>
      </form>
      <table className="comment-list">
        <tbody>
          <tr>
            <th colSpan={2}>メッセージ</th>
          </tr>
          {list()}
        </tbody>
      </table>
    </div>
  );
}

export default CommentList;
