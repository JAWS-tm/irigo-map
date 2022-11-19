import { useDispatch, useSelector } from 'react-redux';
import { selectCurrentUser } from '../store/slices/authSlice';

export const useAuth = () => {
  const currentUser = useSelector(selectCurrentUser);
  if (currentUser) {
    return true;
  } else {
    return false;
  }
};
