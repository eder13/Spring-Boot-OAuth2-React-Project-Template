import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';
import 'normalize.css';
import GlobalStyle from "./components/GlobalStyle";

ReactDOM.render(
    <React.StrictMode>
        <GlobalStyle />
        <App />
    </React.StrictMode>,
    document.getElementById('root')
);