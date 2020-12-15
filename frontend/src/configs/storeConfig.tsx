import { createStore, combineReducers, applyMiddleware } from 'redux';
import reducers from 'reducers';
import { composeWithDevTools } from 'redux-devtools-extension/developmentOnly';

const composeEnhancers = composeWithDevTools({ 
    trace: true, 
    traceLimit: 25 
}); 
const middlewares: any[] = [];

export default createStore(
    combineReducers({
    ...reducers
    }),
    composeEnhancers(applyMiddleware(...middlewares))
);