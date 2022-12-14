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
  async getUserComments() {
    const response = await axios.get(COMMENT_API_URL + 'own', { headers: authHeader() });
    return response.data.payload;
  }
  async getAllUsersCommentsByNumberLine(lineId) {
    const response = await axios.get(COMMENT_API_URL + lineId, { headers: authHeader() });
    return response.data.payload;
  }
}

export default new CommentService();
