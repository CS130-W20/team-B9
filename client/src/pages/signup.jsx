import React from 'react';
import { Link } from 'react-router-dom';
import './signup.scss';

class Signup extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      username: '',
      password: ''
    };
  }

  onUsernameChange = e => {
    this.setState({ username: e.target.value });
  };

  onPasswordChange = e => {
    this.setState({ password: e.target.value });
  };

  handleSubmit = e => {
    e.preventDefault();
  };

  render() {
    const { username, password } = this.state;

    return (
      <div className="signup-page">
        <section className="hero">
          <p>Sign Up</p>
        </section>
        <section className="body">
          {/* todo: submit form to correct endpoint */}
          <form onSubmit={this.handleSubmit} id="form">
            <div className="form-input">
              <label htmlFor="username">Username</label>
              <input
                autoFocus
                type="text"
                name="username"
                value={username}
                onChange={this.onUsernameChange}
              />
            </div>
            <div className="form-input">
              <label htmlFor="password">Password</label>
              <input
                type="text"
                name="password"
                value={password}
                onChange={this.onPasswordChange}
              />
            </div>
          </form>
          <button type="submit" form="form">
            SIGN UP
          </button>
        </section>
      </div>
    );
  }
}

export default Signup;
