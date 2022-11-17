import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import authService from '../../services/auth.service';
import { IDLE_STATE, LOADING_STATE } from '../constants';

const user = JSON.parse(localStorage.getItem('user') ?? '{}');

export const login = createAsyncThunk(
  'auth/login',
  async ({ email, password }, { rejectWithValue }) => {
    try {
      const value = await authService.login(email, password);
      return value;
    } catch (err) {
      return rejectWithValue(err.message);
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
      return rejectWithValue(err.message);
    }
  }
);

const authInitialState = {
  currentUser: user ?? null,
  error: {},
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
        state.currentUser = {};
        state.error = action.payload;
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
        state.currentUser = {};
        state.error = action.payload;
      });
  },
});

// Action creators are generated for each case reducer function
// export const { login } = authSlice.actions;

export default authSlice.reducer;

// Selects
