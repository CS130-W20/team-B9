import React from 'react';
import './App.scss';
import { Switch, Route, BrowserRouter } from 'react-router-dom';

import LandingPage from './pages/landing';
import LoginPage from './pages/login';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Switch>
          <Route exact path="/" component={LandingPage} />
          <Route path="/login" component={LoginPage} />
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
