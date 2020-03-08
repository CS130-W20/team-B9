import React from 'react';
import { Link } from 'react-router-dom';
import './login.scss';

class Login extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      userName: '',
      password: ''
    };
  }

  onUsernameChange = e => {
    this.setState({ userName: e.target.value });
  };

  onPasswordChange = e => {
    this.setState({ password: e.target.value });
  };

    handleSubmit = e => {
      e.preventDefault();
      const form  = new FormData();
  
      for(const name in this.state) {
        form.append(name, this.state[name]);
      }

      try {
      fetch('http://localhost:8080/app/login', {
        method: 'POST',
        mode: 'no-cors',
        body: form
      })
      .then(function(response) {
        return response.json();
      })
      .then(function(response) {
        this.setState({ key: response });
      });
      
      //this.props.history.push('/stream');
    } catch (e) {
      alert(e.message);
    }
  };

  render() {
    const { userName, password } = this.state;

    return (
      <div className="login-page">
        <section className="hero">
          <p>Log In</p>
        </section>
        <section className="body">
          {/* todo: submit form to correct endpoint */}
          <form onSubmit={this.handleSubmit} id="form">
            <div className="form-input">
              <label htmlFor="userName">Username</label>
              <input
                autoFocus
                type="text"
                name="userName"
                value={userName}
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
