'use client';
import { ChangeEvent, useState } from 'react';
import { Button, TextField } from '@mui/material';
import RestClient from './RestClient';

interface State {
  id: string;
  text: string;
  comment: string;
}

interface Props {
  id: string;
  comment: string;
  text: string;
  client: RestClient;
  onDelete(id: string): void;
}

function ListNode(props: Props) {
  const [state, setState] = useState<State>({
    id: props.id,
    comment: props.comment,
    text: '',
  });
  const client = props.client;


  function update(): void {
    if (props.id && state.text.length > 0) {
      client.update(props.id, state.text).then(() =>
        setState({
          id: props.id,
          comment: state.text,
          text: '',
        }),
      ).catch(() => {
        // pass
      });
    }
  };

  function deleteItem(): void {
    if (props.id) {
      client.delete(props.id).then(() => {
        props.onDelete(props.id);
        return;
      }).catch(() => {
        // pass
      });
    }
  };

  function changeText(event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, comment: string): void {
    setState({
      id: props.id,
      text: event.currentTarget.value,
      comment,
    });
  };

  return (
    <tr>
      <td>{state.comment}</td>
      <td>
        <TextField
          type="text"
          name="comment"
          value={state.text}
          onChange={(e) => changeText(e, state.comment)}
        />
        <Button onClick={(): void => update()}>更新</Button>
        <Button onClick={(): void => deleteItem()}>削除</Button>
      </td>
    </tr>
  );
}

export default ListNode;
