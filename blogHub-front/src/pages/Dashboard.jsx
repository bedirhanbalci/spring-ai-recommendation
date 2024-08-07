import React, {useEffect, useState} from 'react'
import {useNavigate} from 'react-router-dom'
import Navigation from '../layouts/Navigation';
import {useDispatch, useSelector} from 'react-redux';
import Pagination from '../layouts/Pagination';
import {toast, ToastContainer} from 'react-toastify';
import BlogList from '../layouts/BlogList';
import {getAllBlogs, getBlogByTitle, getBlogCount, likeBlog} from "../redux/slice/blogSlice";

export default function Dashboard() {
    const navigate = useNavigate();

    const [currentPage, setCurrentPage] = useState(0);

    const dispatch = useDispatch();

    useEffect(() => {
        if (!localStorage.getItem('email')) {
            navigate('/signin');
        }
        console.log("Dashboard useEffect");
        dispatch(getBlogCount());
        dispatch(getAllBlogs(currentPage));
    }, [currentPage, navigate, dispatch]);

    const blogs = useSelector((state) => state.blog.allBlogs);

    const pageCount = useSelector((state) => state.blog.blogCount) / 5;

    const blogLoadingError = useSelector((state) => state.blog.blogLoadingError);

    const likeError = useSelector((state) => state.blog.likeError);

    const recommendationError = useSelector((state) => state.blog.recommendationError);

    const recommendationMessage = useSelector((state) => state.blog.recommendationMessage);


    const handlePageChange = (newPage) => {
        setCurrentPage(newPage);
        dispatch(getAllBlogs(newPage));
    };

    const handleOnLike = async (blogTitle) => {
        await dispatch(likeBlog(blogTitle));
        await dispatch(getBlogByTitle(blogTitle));
    }

    return (
        <div>
            <Navigation/>

            <h2 className="text-center">All Blogs</h2><br/>

            <ToastContainer/>
            {blogLoadingError === "ERR_NETWORK" ? (
                    toast.error(`No internet connection`, {
                        position: toast.POSITION.TOP_LEFT,
                        toastId: ""
                    }))
                : likeError && likeError.payload !== null ? (
                        toast.error(`${likeError.payload}`, {
                            position: toast.POSITION.TOP_LEFT,
                            toastId: ""
                        }))
                    : recommendationError !== null ? (
                            toast.error(`${recommendationError}`, {
                                position: toast.POSITION.TOP_LEFT,
                                toastId: ""
                            }))
                        : recommendationMessage !== null && recommendationMessage !== "" ? (
                                toast.success(`${recommendationMessage}`, {
                                    position: toast.POSITION.TOP_LEFT,
                                    toastId: ""
                                }))
                            : null
            }

            <BlogList
                blogs={blogs}
                handleOnLike={handleOnLike}
            />

            <Pagination
                totalPages={Math.ceil(pageCount)}
                currentPage={currentPage}
                onPageChange={handlePageChange}/>
        </div>
    )
};
