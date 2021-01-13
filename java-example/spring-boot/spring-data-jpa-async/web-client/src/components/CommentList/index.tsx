import * as React from 'react'
import ListNode from './ListNode'
import { State, Comment } from '../../store/comment/types'
import RestClient from './RestClient'
import Button from '@material-ui/core/Button'
import TextField from '@material-ui/core/TextField';

interface Props {
  comments: Array<Comment>
  comment: string
}

export default class CommentList extends React.Component<Props, State> {
  props: Props
  state: State
  client: RestClient
  constructor(props: Props) {
    super(props)
    this.props = props
    this.state = {
      comments: [],
      comment: ''
    }
    this.client = new RestClient()
  }

  componentDidMount(): void {
    this.fetchComments()
  }

  fetchComments = (): void => {
    this.client.fetchComments().then((comments) =>
      this.setState({
        comments: comments,
        comment: ''
      })
    )
  }

  addComment = async (comment: string): Promise<Comment> => {
    return await this.client.add(comment)
  }

  removeComment = (id: string): void => {
    const comments = this.state.comments
    const target = comments.findIndex((comment) => comment.id === id)
    if (target == 0) {
      comments.shift()
      this.setState({
        comments: comments,
        comment: ''
      })
    } else if (target > 0) {
      comments.splice(target, 1)
      this.setState({
        comments: comments,
        comment: ''
      })
    }
  }

  list = (): Array<JSX.Element> => {
    return this.state.comments.filter(
      (comment) => comment.id
    ).map((comment) => (
      <ListNode
        key={comment.id}
        id={comment.id ? comment.id : ''}
        comment={comment.comment}
        text={''}
        client={this.client}
        onDelete={this.removeComment}
      />
    ))
  }

  add = (): void => {
    const comments = this.state.comments
    this.addComment(this.state.comment).then((comment) => {
      comments.push(comment)
      this.setState({
        comments: comments,
        comment: ''
      })
    })
  }

  private update = (event: React.ChangeEvent<HTMLInputElement>): void => {
    this.setState({
      comment: event.currentTarget.value,
      comments: this.state.comments
    })
  }

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
                  ></TextField>
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
    )
  }
}
