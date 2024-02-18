import React from 'react';
import Routes from '../routes';
import { AuthProvider } from '../context/AuthProvider';

const App = ({ isLoggedIn, username, errorMessage }) => {
    return (
        <AuthProvider
            isLoggedIn={isLoggedIn}
            username={username}
            errorMessage={errorMessage}
        >
            <Routes />
        </AuthProvider>
    );
};

export default App;
