import React from 'react';
import ReactDOM from 'react-dom';
import { AppContainer } from 'react-hot-loader';
import App from './components/App';

const render = (App) => {
    ReactDOM.render(
        <AppContainer>
            <App />
        </AppContainer>,
        document.getElementById('root')
    );
};

render(App);

if (module.hot) {
    module.hot.accept('./components/App.js', () => {
        const NextRootContainer = require('./components/App').default;
        render(NextRootContainer);
    });
}
