import React from 'react';
import './App.scss';
import { Switch, Route, BrowserRouter } from 'react-router-dom';

import LandingPage from './pages/landing';
import LoginPage from './pages/login';
import SignupPage from './pages/signup';
import StreamPage from './pages/stream';
import MyProfilePage from './pages/profile';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Switch>
          <Route exact path="/" component={LandingPage} />
          <Route path="/login" component={LoginPage} />
          <Route path="/signup" component={SignupPage} />
          <Route path="/stream" component={StreamPage} />
          <Route path="/myprofile" component={MyProfilePage} />
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
