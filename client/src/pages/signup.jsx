import React from 'react';
import { Link } from 'react-router-dom';
import './signup.scss';

class Signup extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      userName: '',
      password: '',
      errors: ''
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
    const form = new FormData();

    for (const name in this.state) {
      if (name !== 'error') {
        form.append(name, this.state[name]);
      }
    }

    fetch('https://limelight-ucla.herokuapp.com/app/signup', {
      method: 'POST',
      headers: {
        'Access-Control-Allow-Origin': '*'
      },
      body: form
    })
      .then(response => {
        return response.json();
      })
      .then(response => {
        if (response === 0) {
          if (this.state.userName === '') {
            this.setState({ errors: 'Please enter a username' });
          } else if (this.state.password === '') {
            this.setState({ errors: 'Please enter a password' });
          } else {
            this.setState({ errors: 'Username taken' });
          }
        } else {
          localStorage.setItem('userSessionKey', response);
          localStorage.setItem('userName', this.state.userName);
          this.props.history.push('/stream');
        }
      });
  };

  render() {
    const { userName, password, errors } = this.state;

    return (
      <div className="signup-page">
        <section className="hero">
          <p>Sign Up</p>
        </section>
        <section className="body">
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
            SIGN UP
          </button>

          <p>{errors}</p>
        </section>
      </div>
    );
  }
}

export default Signup;
