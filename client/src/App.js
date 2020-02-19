import React from 'react';
import './App.css';
import { Switch, Route, BrowserRouter } from 'react-router-dom';

import LandingPage from './pages/landing';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Switch>
          <Route exact path="/" component={LandingPage} />
          {/* <Route path="/stream" component={StreamingPage} /> */}
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
