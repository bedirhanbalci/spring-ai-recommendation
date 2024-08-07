import React from 'react';
import Navigation from '../layouts/Navigation';
import {Form, Formik} from "formik";
import CustomTextInput from '../utilities/customFormControls/CustomTextInput';
import * as Yup from "yup";
import {useDispatch, useSelector} from 'react-redux';
import LoadingButton from '../layouts/LoadingButton';
import {toast, ToastContainer} from 'react-toastify';
import '../style/css/add-blog.css';
import {createBlog} from "../redux/slice/blogSlice"; // CSS dosyasını içe aktarın

export default function AddBlog() {
    const initialValues = {
        title: "",
        text: "",
    };

    const schema = Yup.object({
        title: Yup.string()
            .required("Title field is required")
            .min(3, "Min 3 characters")
            .max(200, "Max 200 characters"),
        text: Yup.string()
            .required("Text field is required")
            .min(20, "Min 20 characters")
            .max(1000, "Max 1000 characters"),
    });

    const dispatch = useDispatch();

    const handleOnSubmit = async (values) => {
        await dispatch(createBlog(values));
    }

    const isAdding = useSelector((state) => state.book.isAdding);

    const addBookResponse = useSelector((state) => state.book.addBookResponse);

    const addBookError = useSelector((state) => state.book.addBookError);

    const createBlogMessage = useSelector((state) => state.blog.createBlogMessage);

    const createBlogError = useSelector((state) => state.blog.createBlogError);

    return (
        <div>
            <Navigation/>
            <div className="centered-form-container">
                <div className="centered-form">
                    <ToastContainer/>
                    {
                        addBookResponse !== null ?
                            (
                                toast.success(`${addBookResponse.message}`, {
                                    position: toast.POSITION.TOP_LEFT,
                                    toastId: ""
                                })
                            ) : addBookError === "ERR_BAD_REQUEST" ?
                                (
                                    toast.error(`Kitap Eklenemedi`, {
                                        position: toast.POSITION.TOP_LEFT,
                                        toastId: ""
                                    })
                                ) : createBlogMessage !== null ?
                                    (
                                        toast.success(`${createBlogMessage}`, {
                                            position: toast.POSITION.TOP_LEFT,
                                            toastId: ""
                                        })
                                    ) : createBlogError && createBlogError.message !== null ?
                                        (
                                            toast.error(`${createBlogError.message}`, {
                                                position: toast.POSITION.TOP_LEFT,
                                                toastId: ""
                                            })
                                        ) : null
                    }
                    <Formik
                        initialValues={initialValues}
                        validationSchema={schema}
                        onSubmit={async (values, {resetForm}) => {
                            const result = await handleOnSubmit(values);
                            if (result?.payload?.status === "Success") {
                                resetForm();
                            }
                        }}>
                        <Form>
                            <div className="form-group mb-2">
                                <label htmlFor="title">Title</label>
                                <CustomTextInput
                                    name="title"
                                    type="text"
                                    className="form-control"
                                    id="title"
                                    placeholder="Enter Title"
                                />
                            </div>

                            <div className="form-group mb-2">
                                <label htmlFor="text">Text</label>
                                <CustomTextInput
                                    name="text"
                                    as="textarea"
                                    className="form-control"
                                    id="text"
                                    placeholder="Enter Text"
                                />
                            </div>


                            {
                                isAdding ?
                                    (
                                        <LoadingButton/>
                                    ) : (
                                        <button type='submit' className="btn btn-primary" style={{width: '100%'}}>Add
                                            Blog</button>
                                    )
                            }
                        </Form>
                    </Formik>
                </div>
            </div>
        </div>
    );
}
