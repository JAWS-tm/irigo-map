import { createSlice } from "@reduxjs/toolkit";
import { IDLE_STATE } from "../constants";

// Create actions & reducer
export const usersSlice = createSlice({
  name: "users",
  initialState: {
    list: [],
    error: false,
    state: IDLE_STATE,
  },
  reducers: {
    addUser: (state) => {
      state.list.push({
        id: 1,
        name: "Jules",
        age: 20,
      });
    },
  },
});

// Action creators are generated for each case reducer function
export const { addUser } = usersSlice.actions;

export default usersSlice.reducer;
