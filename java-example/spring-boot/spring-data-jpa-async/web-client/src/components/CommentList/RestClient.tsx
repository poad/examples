import config from './Config';
import { Comment } from '../../store/comment/types';

class RestClient {
  endpoint = config.endpoint;

  async add(comment: string): Promise<Comment> {
    const json = await fetch(this.endpoint, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ comment }),
    }).then((res): Promise<Comment> => {
      if (res.ok) {
        return res.json() as Promise<Comment>;
      }
      // eslint-disable-next-line promise/no-return-wrap
      return Promise.resolve<Comment>({} as Comment);
    });
    return json as Comment;
  }

  async fetchComments(): Promise<Comment[]> {
    const json = await fetch(this.endpoint, {
      method: 'GET',
      headers: {
        Accept: 'application/json',
      },
    }).then((res): Promise<Comment[]> | undefined => {
      if (res.ok) {
        return res.json() as Promise<Comment[]>;
      }
      // eslint-disable-next-line promise/no-return-wrap
      return Promise.resolve<Comment[]>([{} as Comment]);
    });

    return json as Comment[];
  }

  async update(id: string, comment: string): Promise<Comment[]> {
    const json = await fetch(this.endpoint + id, {
      method: 'PUT',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ comment }),
    }).then((res): Promise<Comment[]> | undefined => {
      if (res.ok) {
        return res.json() as Promise<Comment[]>;
      }
      return undefined;
    });

    return json as Comment[];
  }

  async delete(id: string): Promise<void> {
    const response = await fetch(this.endpoint + id, {
      method: 'DELETE',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
    });
    if (!response.ok) {
      throw response;
    }
  }
}

export default RestClient;
