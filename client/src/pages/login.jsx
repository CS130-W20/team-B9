import React from 'react';
import { Link } from 'react-router-dom';
import './login.scss';

class Login extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      username: '',
      password: ''
    }
  }
  render() {
    const { username, password } = this.state;

    return (
      <div className="login-page">
        <section className="hero">
          <p>Log In</p>
        </section>
        <section className="body">
          <form>
            <label for="username">Username</label>
            <input type="text" name="username" value={username} />
            <label for="password">Password:</label>
            <input type="password" name="password" value={password} />
          </form>
        </section>
      </div>
    );
  }
}

export default Login;
