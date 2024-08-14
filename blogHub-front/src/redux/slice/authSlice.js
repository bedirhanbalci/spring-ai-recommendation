import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import axios from "axios";

const initalState = {
    isLoading: false,

    registerResponse: '',
    registerError: null,

    loginResponse: '',
    loginError: null,
}

const BASE_URL = "http://localhost:8080";

export const addUser = createAsyncThunk(
    "auth/addUser",
    async (values, { rejectWithValue }) => {
        try {
            const response = await axios.post(`${BASE_URL}/api/v1/users`, values);
            return response.data;
        } catch (error) {
            if (error.response && error.response.data) {
                return rejectWithValue(error.response.data.message);

            } else {
                return rejectWithValue(error.message);
            }
        }
    }
);


export const login = createAsyncThunk(
    "auth/login",
    async (values, { rejectWithValue }) => {
        try {
            const response = await axios.post(`${BASE_URL}/api/v1/users/login`, values);
            return response.data;
        } catch (error) {
            if (error.response && error.response.data) {
                return rejectWithValue(error.response.data.message);
            } else {
                return rejectWithValue(error.message);
            }
        }
    }
);


export const authSlice = createSlice({
    name: 'auth',
    initialState: initalState,
    reducers: {},

    extraReducers:{
        // addUser
        [addUser.pending]: (state) => {
            state.isLoading = true;
        },
        [addUser.fulfilled]: (state, action) => {
            state.isLoading = false;
            state.registerResponse = action.payload;
        },
        [addUser.rejected]: (state, action) => {
            state.isLoading = false;
            state.registerError = action.payload;
        },


        // login
        [login.pending]: (state) => {
            state.isLoading = true;
        },
        [login.fulfilled]: (state, action) => {
            state.isLoading = false;
            state.loginResponse = action.payload;
        },
        [login.rejected]: (state, action) => {
            state.isLoading = false;
            state.loginError = action.payload;
        }
    }
})


export default authSlice.reducer