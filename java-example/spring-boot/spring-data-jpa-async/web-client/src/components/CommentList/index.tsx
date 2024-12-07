'use client';
import { ChangeEvent, Component, JSX } from 'react';
import { Button, TextField } from '@mui/material';
import ListNode from './ListNode';
import { State, Comment } from '../../store/comment/types';
import RestClient from './RestClient';

interface Props {
  comments: Comment[];
  comment: string;
}

export default class CommentList extends Component<Props, State> {
  props: Props;

  state: State;

  client: RestClient;

  constructor(props: Props) {
    super(props);
    this.props = props;
    this.state = {
      comments: [],
      comment: '',
    };
    this.client = new RestClient();
  }

  componentDidMount(): void {
    this.fetchComments();
  }

  fetchComments = (): void => {
    this.client.fetchComments().then((comments) =>
      this.setState({
        comments,
        comment: '',
      })
    );
  };

  addComment = async (comment: string): Promise<Comment> =>
    this.client.add(comment);

  removeComment = (id: string): void => {
    const { comments } = this.state;
    const target = comments.findIndex((comment) => comment.id === id);
    if (target === 0) {
      comments.shift();
      this.setState({
        comments,
        comment: '',
      });
    } else if (target > 0) {
      comments.splice(target, 1);
      this.setState({
        comments,
        comment: '',
      });
    }
  };

  list = (): JSX.Element[] =>
    this.state.comments
      .filter((comment) => comment.id)
      .map((comment) => (
        <ListNode
          key={comment.id}
          id={comment.id ? comment.id : ''}
          comment={comment.comment}
          text=""
          client={this.client}
          onDelete={this.removeComment}
        />
      ));

  add = (): void => {
    const { comments } = this.state;
    this.addComment(this.state.comment).then((comment) => {
      comments.push(comment);
      this.setState({
        comments,
        comment: '',
      });
    });
  };

  private update = (event: ChangeEvent<HTMLInputElement>): void => {
    this.setState({
      comment: event.currentTarget.value,
      comments: this.state.comments,
    });
  };

  public render(): JSX.Element {
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
                    value={this.state.comment}
                    onChange={this.update}
                  />
                </td>
                <td>
                  <Button onClick={(): void => this.add()}>追加</Button>
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
            {this.list()}
          </tbody>
        </table>
      </div>
    );
  }
}
