import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';
import { getUser } from './utils/data-fetching';

(async () => {
    const response = await getUser();
    ReactDOM.render(
        <App
            isLoggedIn={response.isLoggedIn}
            username={response.username}
            errorMessage={response.errorMessage}
        />,
        document.getElementById('root')
    );
})();
