import axios from 'axios';
import { config } from '../config/config';
import authHeader from './auth-header';

const COMMENT_API_URL = config.API_URL + '/comments/';

class CommentService {
  async handleSent(notation, comment, numberLine) {
    const response = await axios.post(
      COMMENT_API_URL,
      { notation, commentary: comment, numberLine },
      { headers: authHeader() }
    );
  }
}

export default new CommentService();
