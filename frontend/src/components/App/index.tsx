import React, { Fragment, Suspense, lazy } from 'react';
// import './style.scss';
import { withRouter, Switch, Route, Redirect } from 'react-router-dom';

const Main = lazy(() => import('components/Main'));

const App = () => {
    return (
        <Fragment>
            <Suspense fallback={
                <div>
                    loading...
                </div>
            }>
                <div className="App">
                    <header className="App-header">

                    </header>
                    <Switch>
                        <Route exact path="/template" component={Main} />
                        <Redirect path="*" to="/template" />
                    </Switch>
                </div>
            </Suspense>
        </Fragment>
    );
}

export default withRouter(App);
