import React from 'react';
import './landing.scss';

import logo from '../assets/logo.png';

function Landing() {
  return (
    <div className="landing-page">
      <img src={logo} alt="logo" />
    </div>
  );
}

export default Landing;
