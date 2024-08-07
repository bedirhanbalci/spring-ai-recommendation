import { useField } from "formik";
import React from "react";
import '../../style/css/SignIn.css';

export default function CustomTextInput({ as: Component = "input", ...props }) {
  const [field, meta] = useField(props);

  return (
    <div>
      <Component {...field} {...props} />
      {
        meta.touched && !!meta.error ? (
          <small className="text-danger">{meta.error}</small>
        ) : null
      }
    </div>
  );
}