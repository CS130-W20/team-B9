import React from 'react';
import { Link } from 'react-router-dom';
import './signup.scss';

class Signup extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      userName: '',
      password: '',
      firstName: 'aaa',
      lastName:'bbb',
      email:'placeholder@gmail.com',
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
      if (name != 'key') {
        form.append(name, this.state[name]);
      }
    }
  
    try {
    fetch('http://localhost:8080/app/signup', {
      method: 'POST',
      mode: 'no-cors',
      body: form
    })
    .then(function(response) {
      return response.json();
    })
    .then(function(response) {
      localStorage.setItem('userSessionKey', response);
      console.log(localStorage.getItem('userSessionKey'));
    });
  } catch (e) {
    alert(e.message);
  }
};

  render() {
    const { userName, password } = this.state;

    return (
      <div className="signup-page">
        <section className="hero">
          <p>Sign Up</p>
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
