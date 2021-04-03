import React, {Fragment, useEffect, useState} from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';

/**
 * This is just an Example for setting login/logout
 */

axios.interceptors.request.use((req) => {
    if (
        req.method === "post" ||
        req.method === "delete" ||
        req.method === "put" ||
        req.method === "patch"
    ) {
        if (!(/^http:.*/.test(req.url) || /^https:.*/.test(req.url))) {
            req.headers.common = {
                ...req.headers.common,
                "X-XSRF-TOKEN": Cookies.get("XSRF-TOKEN"),
            };
        }
    }

    return req;
});

const Home = () => {

    const [user, setUser] = useState("");
    const [isLoggedIn, setIsLoggedIn] = useState("");
    const [errorLogging, setErrorLogging] = useState("");

    const logout = async (e) => {
        await axios.post("/logout");
        window.location.href = "http://localhost:8081";
    };

    useEffect(() => {
        const loadData = async () => {
            return await axios.get("/user");
        }

        // "authentication check" -> check if we can access /user endpoint
        loadData().then(async (userData) => {
            setUser(userData.data.email);
            setIsLoggedIn('succeeded');
        }).catch(async e => {
            setIsLoggedIn('failed');
            const req = await axios.get("/error?message=true");
            if(req.data !== "") {
                setErrorLogging(req.data);
            }
        });
    }, []);

    if(isLoggedIn === 'succeeded') {
        return (
            <Fragment>
                <div>Hello {user}</div>
                <button onClick={logout}>Logout</button>
            </Fragment>
        );
    } else {
        return(
            <Fragment>
                <h1>Login</h1>
                <strong style={{color: 'red'}}>
                    { errorLogging !== "" ? errorLogging : "" }
                </strong>
                <div className="container">
                    <div>
                        With Google: <a href="/oauth2/authorization/google">click here</a>
                    </div>
                </div>
            </Fragment>
        );
    }
}

export default Home;