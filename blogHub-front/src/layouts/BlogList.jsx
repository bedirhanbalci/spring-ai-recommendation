import React from 'react'
import "../style/css/blog-card.css"
import {defaultBlogFoto} from "../utilities/constant/Constant";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faHeart} from "@fortawesome/free-solid-svg-icons";

export default function BlogList({blogs, handleOnLike}) {
    return (
        <div className="projcard-container">
            {blogs.map((blog) => (
                <div className="projcard projcard-green" key={blog.title}>
                    <div className="projcard-innerbox">
                        {blog.fotoUrl === null ? (
                            <img className="projcard-img" src={defaultBlogFoto} alt=""/>
                        ) : (
                            <img className="projcard-img" src={blog.fotoUrl} alt=""/>
                        )}
                        <div className="projcard-textbox">
                            <div className="projcard-title">{blog.title}</div>
                            <div className="projcard-subtitle">{formatDate(blog.createdDateTime)}</div>
                            <div className="projcard-bar"></div>
                            <div className="projcard-description">
                                {blog.text.length > 100 ? blog.text.substring(0, 250) + "..." : blog.text}
                            </div>
                            <div
                                 onClick={() => handleOnLike(blog.title)}>
                                <FontAwesomeIcon icon={faHeart}/> {blog.likeCount}
                            </div>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
}


function formatDate(dateString) {
    const options = {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        hour12: false
    };
    const date = new Date(dateString);
    return new Intl.DateTimeFormat('tr-TR', options).format(date);
}
