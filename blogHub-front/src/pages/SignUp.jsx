import React from "react";
import {Link} from "react-router-dom";
import "../style/css/SignIn.css";
import CustomTextInput from "../utilities/customFormControls/CustomTextInput";
import {Form, Formik} from "formik";
import * as Yup from "yup";
import {useDispatch, useSelector} from "react-redux";
import {addUser} from "../redux/slice/authSlice";
import LoadingButton from "../layouts/LoadingButton";
import {toast} from "react-toastify";

export default function SignUp() {
    const initialValues = {
        name: "",
        email: "",
        password: "",
    };

    const schema = Yup.object({
        name: Yup.string()
            // .required("Ad alanı zorunludur")
            .max(50, "Ad maksimum 50 karakter olabilir"),
        email: Yup.string()
            .required("Email alanı zorunludur"),
        // .email("Email geçerli değil"),
        // .matches(/^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/i, "Email geçerli değil"),
        password: Yup.string()
        // .required("Parola alanı zorunludur")
        // .min(8, "Parola minimum 8 karakter olabilir")
        // .max(150, "Parola maksimum 150 karakter olabilir")
        // .matches(
        //     /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,})/,
        //     "Parola 8 karakterden oluşmalı, aynı zamanda en az bir büyük harf bir küçük harf, bir numara ve bir özel karakter içermelidir(#,!)"
        // ),
    });

    const dispatch = useDispatch();

    const isLoading = useSelector((state) => state.auth.isLoading);

    const registerError = useSelector((state) => state.auth.registerError);

    const response = useSelector((state) => state.auth.registerResponse);

    const handleOnSubmit = async (values) => {
        const result = await dispatch(addUser(values));
        if (addUser.fulfilled.match(result) && result.payload.status === "Success") {
            localStorage.clear();
        }
        return result;
    };


    return (
        <div className="signin-container">
            <h2>Sign Up</h2>
            {
                registerError ?
                    (
                        toast.error(`${registerError}`, {
                            position: toast.POSITION.TOP_LEFT,
                            toastId: ""
                        })
                    ) :
                    response.status && response.status === "Success" ? (
                        toast.success(`${response.message}`, {
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
                    <div>
                        <div className="form-group">
                            <label htmlFor="name">Name</label>
                            <CustomTextInput
                                name="name"
                                type="text"
                                className="form-control"
                                id="name"
                                placeholder="Enter Name"
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="email">Email</label>
                            <CustomTextInput
                                name="email"
                                type="email"
                                className="form-control"
                                id="email"
                                placeholder="Enter email"
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="password">Password</label>
                            <CustomTextInput
                                name="password"
                                placeholder="Enter password"
                                id="password"
                                type="password"
                                className="form-control"
                            />
                        </div>
                        {
                            isLoading ? (
                                <LoadingButton/>
                            ) : (
                                <button type="submit" className="btn btn-primary">Sign Up</button>
                            )}
                    </div>
                </Form>
            </Formik>
            <p>Do you have an account? <Link to="/signin">SignIn </Link></p>
        </div>
    );
}