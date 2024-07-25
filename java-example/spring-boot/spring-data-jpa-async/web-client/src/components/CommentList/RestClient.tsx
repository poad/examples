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
      return Promise.resolve({} as Comment);
    });
    return json as Comment;
  }

  async fetchComments(): Promise<Array<Comment>> {
    const json = await fetch(this.endpoint, {
      method: 'GET',
      headers: {
        Accept: 'application/json',
      },
    }).then((res): Promise<Array<Comment>> | undefined => {
      if (res.ok) {
        return res.json() as Promise<Array<Comment>>;
      }
      return Promise.resolve([] as Comment[]);
    });

    return json as Array<Comment>;
  }

  async update(id: string, comment: string): Promise<Array<Comment>> {
    const json = await fetch(this.endpoint + id, {
      method: 'PUT',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ comment }),
    }).then((res): Promise<Array<Comment>> | undefined => {
      if (res.ok) {
        return res.json() as Promise<Array<Comment>>;
      }
      return undefined;
    });

    return json as Array<Comment>;
  }

  async delete(id: string): Promise<void> {
    fetch(this.endpoint + id, {
      method: 'DELETE',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
    }).then((res): void => {
      if (!res.ok) {
        throw res;
      }
    });
  }
}

export default RestClient;
