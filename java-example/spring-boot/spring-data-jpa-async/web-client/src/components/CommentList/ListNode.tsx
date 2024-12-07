'use client';
import { ChangeEvent, Component, JSX } from 'react';
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

class ListNode extends Component<Props, State> {
  props: Props;

  state: State;

  client: RestClient;

  constructor(props: Props) {
    super(props);
    this.props = props;
    this.state = {
      id: props.id,
      comment: props.comment,
      text: '',
    };
    this.client = props.client;
  }

  changeText = (event: ChangeEvent<HTMLInputElement>): void => {
    this.setState({
      id: this.props.id,
      text: event.currentTarget.value,
    });
  };

  update = (): void => {
    if (this.props.id && this.state.text.length > 0) {
      this.client.update(this.props.id, this.state.text).then(() =>
        this.setState({
          id: this.props.id,
          comment: this.state.text,
          text: '',
        })
      );
    }
  };

  delete = (): void => {
    if (this.props.id) {
      this.client.delete(this.props.id).then(() => {
        this.props.onDelete(this.props.id);
      });
    }
  };

  public render(): JSX.Element {
    return (
      <tr>
        <td>{this.state.comment}</td>
        <td>
          <TextField
            type="text"
            name="comment"
            value={this.state.text}
            onChange={this.changeText}
          />
          <Button onClick={(): void => this.update()}>更新</Button>
          <Button onClick={(): void => this.delete()}>削除</Button>
        </td>
      </tr>
    );
  }
}

export default ListNode;
