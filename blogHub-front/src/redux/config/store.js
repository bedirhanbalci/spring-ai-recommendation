import {combineReducers} from "@reduxjs/toolkit"
import {blogSlice} from "../slice/blogSlice";
import {authSlice} from "../slice/authSlice";


export const store = combineReducers({
    blog: blogSlice.reducer,
    auth: authSlice.reducer,
});
