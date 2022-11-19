import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import authService from '../../services/auth.service';
import { IDLE_STATE, LOADING_STATE } from '../constants';

export const login = createAsyncThunk(
  'auth/login',
  async ({ email, password }, { rejectWithValue }) => {
    try {
      const value = await authService.login(email, password);
      return value;
    } catch (err) {
      return rejectWithValue(err.response.data.errors.message);
    }
  }
);

export const register = createAsyncThunk(
  'auth/register',
  async (registerForm, { rejectWithValue }) => {
    try {
      const value = await authService.register(
        registerForm.email,
        registerForm.password,
        registerForm.lastName,
        registerForm.firstName,
        registerForm.sex,
        registerForm.birthday,
        registerForm.travelHabits,
        registerForm.travelFrequency
      );
      return value;
    } catch (err) {
      return rejectWithValue(err.response.data.errors.message);
    }
  }
);

export const getMe = createAsyncThunk('auth/me', async (_, { rejectWithValue }) => {
  try {
    const res = await authService.getMe();
    if (res.status === 'OK') return res.payload;
  } catch (err) {
    return rejectWithValue(err.response.data?.errors.message || err.response.data);
  }
});

export const logout = createAsyncThunk('auth/logout', () => {
  authService.logout();
});

// const user = JSON.parse(localStorage.getItem('user') ?? '{}');

const authInitialState = {
  currentUser: null,
  error: null,
  status: IDLE_STATE,
};

// Create actions & reducer
export const authSlice = createSlice({
  name: 'auth',
  initialState: authInitialState,
  reducers: {
    // register: (state, action) => {
    //   state.currentUser = action.payload.user;
    // },
    // login: (state, action) => {
    //   state.currentUser = action.payload.user;
    // },
    clearAuthError: (state, action) => {
      state.error = null;
    },
  },
  extraReducers(builder) {
    builder
      .addCase(login.pending, (state, action) => {
        state.status = LOADING_STATE;
        state.error = '';
      })
      .addCase(login.fulfilled, (state, action) => {
        state.status = IDLE_STATE;
        state.currentUser = action.payload;
        state.error = '';
      })
      .addCase(login.rejected, (state, action) => {
        state.status = IDLE_STATE;
        state.currentUser = null;
        state.error = action.payload;
      });

    builder
      // .addCase(logout.pending, (state, action) => {
      //   state.status = LOADING_STATE;
      //   state.error = '';
      // })
      .addCase(logout.fulfilled, (state, action) => {
        // state.status = IDLE_STATE;
        state.currentUser = null;
        // state.error = '';
      });
    // .addCase(logout.rejected, (state, action) => {
    //   state.status = IDLE_STATE;
    //   state.currentUser = {};
    //   state.error = action.payload;
    // });

    builder
      .addCase(getMe.pending, (state, action) => {
        state.status = LOADING_STATE;
        state.error = '';
      })
      .addCase(getMe.fulfilled, (state, action) => {
        state.status = IDLE_STATE;
        state.currentUser = action.payload;
        state.error = '';
      });

    builder
      .addCase(register.pending, (state, action) => {
        state.status = LOADING_STATE;
        state.error = '';
      })
      .addCase(register.fulfilled, (state, action) => {
        state.status = IDLE_STATE;
        // state.currentUser = action.payload;
        state.error = '';
      })
      .addCase(register.rejected, (state, action) => {
        state.status = IDLE_STATE;
        state.currentUser = null;
        state.error = action.payload;
      });
  },
});

// Action creators are generated for each case reducer function
export const { clearAuthError } = authSlice.actions;

export default authSlice.reducer;

// Selects

export const selectAuthError = (state) => state.auth.error;
export const selectCurrentUser = (state) => state.auth.currentUser;
