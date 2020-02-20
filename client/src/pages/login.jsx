import React from 'react';
import { Link } from 'react-router-dom';
import './login.scss';

class Login extends React.Component {
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
    this.props.history.push('/stream');
  };

  render() {
    const { username, password } = this.state;

    return (
      <div className="login-page">
        <section className="hero">
          <p>Log In</p>
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
                type="password"
                name="password"
                value={password}
                onChange={this.onPasswordChange}
              />
            </div>
          </form>
          <button type="submit" form="form">
            LOG IN
          </button>
        </section>
      </div>
    );
  }
}

export default Login;
