import React, { Fragment } from 'react';
import { useUser } from '../../context/AuthProvider';
import { Redirect } from 'react-router-dom';

const Home = () => {
    const user = useUser();

    if (user.isLoggedIn) {
        return <Redirect to="/profile" />;
    }

    return (
        <div class="container py-5">
            <h1>Home</h1>
            <br />
            <br />

            {user.errorMessage && (
                <div class="alert alert-danger">
                    Please login before you try accessing the site.
                </div>
            )}

            <div>
                Login With Google:{' '}
                <a
                    class="btn btn-primary"
                    href="/oauth2/authorization/google"
                >
                    click here
                </a>
            </div>
        </div>
    );
};

export default Home;
