import React from 'react';
import { Link } from 'react-router-dom';
import './landing.scss';

import logo from '../assets/logo.png';

function Landing() {
  return (
    <div className="landing-page">
      <section className="hero">
        <div className="title-container">
          <img src={logo} alt="logo" />
          <div className="text-container">
            <h1>Limelight</h1>
            <h3>the stage is all yours</h3>
          </div>
        </div>
        <div className="buttons-container">
          <Link to="/login">LOG IN</Link>
          <Link to="/signup">SIGN UP</Link>
        </div>
      </section>
      <section className="body">
        <div className="body-text">
          <h2>Limelight</h2>
          <h4>Tired of vying for attention on social media?</h4>
          <h4>
            We give you the stage to showcase yourself no matter who you are.
          </h4>

          <ol>
            <li>Upload a video and wait in line to be in the Limelight</li>
            <li>Watch other amazing people as you wait</li>
            <li>
              When it's your turn, the stage is all yours <br />
            </li>
          </ol>
          <p>
            Not interested in showcasing yourself? No problem! Just sit back,
            relax, and enjoy the show.
          </p>
        </div>
      </section>
    </div>
  );
}

export default Landing;
