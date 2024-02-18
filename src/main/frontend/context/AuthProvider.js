import React, { createContext, useContext } from 'react';

const AuthContext = createContext({
    isLoggedIn: false,
    username: '',
    errorMessage: '',
});

export const AuthProvider = ({
    isLoggedIn,
    username,
    errorMessage,
    children,
}) => {
    return (
        <AuthContext.Provider value={{ isLoggedIn, username, errorMessage }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useUser = () => {
    return useContext(AuthContext);
};
