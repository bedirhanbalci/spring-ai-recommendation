import React from 'react'
import {useNavigate} from 'react-router';
import "../style/css/navigation.css";
import {Link} from 'react-router-dom';
import {useDispatch} from "react-redux";
import {getRecommendation} from "../redux/slice/blogSlice";


export default function Navigation() {

    const navigate = useNavigate();

    const dispatch = useDispatch();

    const logout = () => {
        localStorage.clear();
        navigate('/signin');
    }

    const hadleGetRecommendation = async () => {
        await dispatch(getRecommendation());
        navigate('/');
    }

    const handleHomeClick = () => {
        window.location.href = "/";
    }

    return (
        <div>
            <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <button className="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01" aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <div className="collapse navbar-collapse" id="navbarTogglerDemo01">
                    <button className="navbar-brand" onClick={handleHomeClick}
                            style={{border: 'none', background: 'none', padding: 0, cursor: 'pointer'}}>
                        <b>BlogHub</b>
                    </button>
                    <ul className="navbar-nav mr-auto mt-2 mt-lg-0">
                        <li className="nav-item active">
                            <Link to="/addbook" className="nav-link" style={{textDecoration: 'none'}}>Add Blog</Link>
                        </li>
                    </ul>

                    <div className="ml-auto my-2 my-lg-0 right-aligned-buttons">
                        <button className="btn btn-outline-success my-2 my-sm-0 logout" onClick={logout}
                                type="submit">Logout
                        </button>
                        <button className="btn btn-outline-primary my-2 my-sm-0 ml-2" type="submit"
                                onClick={hadleGetRecommendation}>Get Recommendation
                        </button>
                    </div>
                </div>
            </nav>
        </div>

    );
}