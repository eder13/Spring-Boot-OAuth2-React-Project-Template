import React from 'react';
import { BrowserRouter, Route, Switch, Redirect } from 'react-router-dom';
import Home from './components/sites/Home';
import Profile from './components/sites/User';
import { useUser } from './context/AuthProvider';

const PrivateRoute = ({ component: Component, ...rest }) => {
    const user = useUser();

    return (
        <Route
            {...rest}
            render={(props) =>
                !user.isLoggedIn ? (
                    <Redirect to="/" />
                ) : (
                    <Component {...props} />
                )
            }
        />
    );
};

const Routes = () => {
    return (
        <BrowserRouter>
            <Switch>
                <Route
                    exact
                    path="/"
                    component={Home}
                />
                <PrivateRoute
                    exact
                    path="/profile"
                    component={Profile}
                />
            </Switch>
        </BrowserRouter>
    );
};

export default Routes;
