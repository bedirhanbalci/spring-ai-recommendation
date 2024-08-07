import {Route, Routes} from "react-router-dom";
import Dashboard from "./pages/Dashboard";
import AddBlog from "./pages/AddBlog";
import SignUp from "./pages/SignUp";
import SignIn from "./pages/SignIn";
import {ToastContainer} from "react-toastify";


function App() {
    return (
        <div className="container-fluid">
            <ToastContainer/>

            <Routes>
                <Route exact path="/" Component={Dashboard}/>
                <Route exact path="/signin" Component={SignIn}/>
                <Route exact path="/signup" Component={SignUp}/>
                <Route exact path="/addbook" Component={AddBlog}/>
            </Routes>
        </div>
    );
}

export default App;
