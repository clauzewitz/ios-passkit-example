import React from 'react';
import ReactDOM from 'react-dom';
// import './index.scss';
import { BrowserRouter } from 'react-router-dom';
import { Provider } from 'react-redux';
import { storeConfig } from 'configs';
import { App } from 'components';
import reportWebVitals from './reportWebVitals';
import CssBaseline from '@material-ui/core/CssBaseline';

ReactDOM.render(
    <React.StrictMode>
        <Provider store={storeConfig}>
            <CssBaseline>
                <BrowserRouter>
                    <App />
                </BrowserRouter>
            </CssBaseline>
        </Provider>
    </React.StrictMode>,
    document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
