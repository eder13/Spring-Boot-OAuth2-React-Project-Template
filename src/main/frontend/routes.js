import React from 'react';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Home from "./components/sites/Home";

const Routes = () => {

    /**
     * TODO: For Redux do Auth check here
     * As soon as the site loads we check if the user is still logged in (session)
     * This loads user Context inside the store whenever page loads
     * */

    return (
        <BrowserRouter>
            <Switch>
                <Route exact path="/" component={Home}/>
            </Switch>
        </BrowserRouter>
    );
}

export default Routes;
