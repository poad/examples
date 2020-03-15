import fetch from 'node-fetch'
import config from './Config'
import { Comment } from '../../store/comment/types'

class RestClient {
  endpoint = config.endpoint

  async add(comment: string): Promise<Comment> {
    const json = await fetch(config.endpoint, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ comment: comment })
    }).then((res) => {
      if (res.ok) {
        return res.json()
      } else {
        console.log('error!')
        return '{}'
      }
    })
    return json as Comment
  }

  async fetchComments(): Promise<Array<Comment>> {
    const json = await fetch(this.endpoint, {
      method: 'GET',
      headers: {
        Accept: 'application/json'
      }
    }).then((res) => {
      if (res.ok) {
        return res.json()
      } else {
        console.log('error!')
        return []
      }
    })

    return json as Array<Comment>
  }

  async update(id: string, comment: string): Promise<Array<Comment>> {
    const json = await fetch(this.endpoint + id, {
      method: 'PUT',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ comment: comment })
    }).then((res) => {
      if (res.ok) {
        return res.json()
      } else {
        console.log('error!')
      }
    })

    return json as Array<Comment>
  }

  async delete(id: string): Promise<void> {
    fetch(this.endpoint + id, {
      method: 'DELETE',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      }
    }).then((res) => {
      if (res.ok) {
        return
      } else {
        console.error('error!')
        throw res
      }
    })
  }
}

export default RestClient
