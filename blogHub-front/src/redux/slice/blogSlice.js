import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import axios from "axios";

const initialState = {
    allBlogs: [],
    blogCount: 0,
    blogLoadingError: null,

    recommendationMessage: null,
    recommendationError: null,
    likeError: null,

    createBlogError: null,
    createBlogMessage: null,
}

const BASE_URL = "http://localhost:8080";

export const getAllBlogs = createAsyncThunk("blog/getAllBlogs", async (pageNo) => {
    return await axios.get(`${BASE_URL}/api/v1/blogs`, {
        params: {
            page: pageNo,
            size: 5
        }
    });
});

export const getBlogCount = createAsyncThunk("blog/getBlogCount", async () => {
    return await axios.get(`${BASE_URL}/api/v1/blogs/blog-count`, {});
});

export const likeBlog = createAsyncThunk(
    "blog/likeBlog",
    async (blogTitle, {rejectWithValue}) => {
        try {
            const email = localStorage.getItem('email')
            return await axios.put(`${BASE_URL}/api/v1/blogs/like-blog`, {
                email: email,
                title: blogTitle
            }, {});
        } catch (error) {
            if (error.response && error.response.data) {
                return rejectWithValue(error.response.data.message);
            } else {
                return rejectWithValue(error.message);
            }
        }
    });

export const getBlogByTitle = createAsyncThunk("blog/getBlogByTitle", async (blogTitle) => {
    return await axios.get(`${BASE_URL}/api/v1/blogs/by-title`, {
        params: {
            title: blogTitle
        }
    });
});


export const getRecommendation = createAsyncThunk(
    "blog/getRecommendation",
    async (_, {rejectWithValue}) => {
        try {
            const email = localStorage.getItem('email')
            return await axios.get(`${BASE_URL}/api/v1/blogs/recommendation`, {
                params: {
                    email: email
                }
            });
        } catch (error) {
            console.log(error);
            if (error.response && error.response.data) {
                return rejectWithValue(error.response.data.message);
            } else {
                return rejectWithValue(error.message);
            }
        }
    });


export const createBlog = createAsyncThunk(
    "blog/createBlog",
    async (blog, {rejectWithValue}) => {
        try {
            const email = localStorage.getItem('email')
            return await axios.post(`${BASE_URL}/api/v1/blogs`, {
                email: email,
                title: blog.title,
                text: blog.text
            });
        } catch (error) {
            if (error.response && error.response.data) {
                return rejectWithValue(error.response.data.message);
            } else {
                return rejectWithValue(error.message);
            }
        }
    });

export const blogSlice = createSlice({
    name: "blog",
    initialState,
    reducers: {},
    extraReducers: {
        // getAllBlogs
        [getAllBlogs.pending]: (state) => {
            state.blogLoadingError = null;
        },
        [getAllBlogs.fulfilled]: (state, action) => {
            state.recommendationMessage = null;
            state.allBlogs = action.payload.data.data;
        },
        [getAllBlogs.rejected]: (state, action) => {
            state.recommendationMessage = null;
            state.blogLoadingError = "ERR_NETWORK";
        },


        // getBlogCount
        [getBlogCount.pending]: (state) => {
            state.recommendationMessage = null;

            state.blogLoadingError = null;
        },
        [getBlogCount.fulfilled]: (state, action) => {
            state.blogCount = action.payload.data;
        },

        [getBlogCount.rejected]: (state, action) => {
            state.blogLoadingError = "ERR_NETWORK";
        },


        // likeBlog
        [likeBlog.rejected]: (state, action) => {
            state.likeError = action;
        },

        // getBlogByTitle
        [getBlogByTitle.pending]: (state) => {
            state.blogLoadingError = null;
        },
        [getBlogByTitle.fulfilled]: (state, action) => {
            const updatedBlog = action.payload.data;

            // Eski blogu listeden çıkart
            const filteredBlogs = state.allBlogs.filter(blog => blog.title !== updatedBlog.title);

            // Güncellenmiş blogu ekle
            const updatedBlogs = [updatedBlog, ...filteredBlogs];

            // Sıralama korunduğundan emin olun
            state.allBlogs = updatedBlogs.sort((a, b) => {
                return a.title.localeCompare(b.title);
            });

            state.likeError = null
        },
        [getBlogByTitle.rejected]: (state, action) => {
            state.blogLoadingError = "ERR_NETWORK";
        },


        // getRecommendation
        [getRecommendation.pending]: (state) => {
            state.recommendationError = null;
        },
        [getRecommendation.fulfilled]: (state, action) => {
            state.recommendationMessage = action.payload.data.message;
            state.allBlogs = action.payload.data.data;
            state.blogCount = action.payload.data.data.length;
        },
        [getRecommendation.rejected]: (state, action) => {
            state.recommendationError = action.payload;
        },



        // createBlog
        [createBlog.pending]: (state) => {
            state.createBlogError = null;
        },
        [createBlog.fulfilled]: (state, action) => {
            console.log(action.payload);
            state.createBlogMessage = action.payload.data.message;
        },
        [createBlog.rejected]: (state, action) => {
            state.createBlogError = action.payload;
        }




    }
});
