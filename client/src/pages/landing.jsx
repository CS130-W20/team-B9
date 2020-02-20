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
        <h4>
          Tired of vying for attention on social media but getting nowhere?
          Limelight gives you the chance to showcase yourself no matter who you
          are.
        </h4>
        <p>How? It's easy.</p>
        <ol>
          <li>Join a line to be in the Limelight</li>
          <li>Watch other people as you wait</li>
          <li>
            When it's your turn, you have the stage Livestream yourself to
            millions of viewers and make your mark.
          </li>
        </ol>
        <p>
          Not interested in showcasing yourself? No problem! Just sit back,
          relax, and enjoy the show.
        </p>
      </section>
    </div>
  );
}

export default Landing;
