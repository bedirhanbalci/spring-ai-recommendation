import React from "react";
import {Link, useNavigate} from "react-router-dom";
import "../style/css/SignIn.css";
import CustomTextInput from "../utilities/customFormControls/CustomTextInput";
import {Form, Formik} from "formik";
import * as Yup from "yup";
import {useDispatch, useSelector} from "react-redux";
import {login} from "../redux/slice/authSlice";
import {toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import LoadingButton from "../layouts/LoadingButton";


export default function SignIn() {
    const initialValues = {email: "", password: ""};

    const schema = Yup.object({
        email: Yup.string()
            .required("Email field is required").email("Invalid email")
            .matches(/^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/i, "Invalid email"),
        password: Yup
            .string()
            .required("Password field is required")
            .max(15, "Max 15 charaters")
    });

    const dispatch = useDispatch();

    const isLoading = useSelector((state) => state.auth.isLoading);

    const loginError = useSelector((state) => state.auth.loginError);

    const response = useSelector((state) => state.auth.loginResponse);

    // const logoutSuccess = useSelector((state) => state.auth.logoutSuccess);

    const navigate = useNavigate();

    const handleOnSubmit = async (values) => {
        await dispatch(login(values)).then((response) => {
            if (login.fulfilled.match(response) && response.payload.status === "Success") {
                localStorage.clear();
                localStorage.setItem("email", response.payload.data.email)
                navigate("/")
            }
        });
    }

    return (
        <div className="signin-container">
            {
                loginError ?
                    (
                        toast.error(`${loginError}`, {
                            position: toast.POSITION.TOP_LEFT,
                            toastId: ""
                        })
                    )
                    : response && response.status === "Success" ?
                        (
                            toast.success(`${response.message}`, {
                                position: toast.POSITION.TOP_LEFT,
                                toastId: ""
                            })
                        ) : null

                // ) : logoutSuccess ?
                //     (
                //         toast.success(`Başarıyla çıkış yapıldı`, {
                //             position: toast.POSITION.TOP_LEFT,
                //             toastId: ""
                //         })
                //     ) : (null)
            }

            <h2>Sign In</h2>
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

                                <button type="submit" className="btn btn-primary">Sign in</button>
                            )}
                    </div>
                </Form>
            </Formik>
            <p>Don't you have an account? <Link to="/signup">SignUp</Link></p>
        </div>
    );
}
