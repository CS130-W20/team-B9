import React from 'react';
import { Link } from 'react-router-dom';
import './stream.scss';
import {
  Menu,
  MenuList,
  MenuButton,
  MenuLink,
} from "@reach/menu-button";
import "@reach/menu-button/styles.css";
import logo from '../assets/logo.png'


class Stream extends React.Component {
  constructor(props) {
    super(props);

    this.messagesRef = React.createRef();

    this.state = {
      currentStream: {
        streamerName: '',
        timeRemain:''
      },
      chatMessage: '',
      voted: false,
      inqueue: false,
      messages: [
        {
          username: 'KingJ0ffrey',
          message: 'BOW TO ME JON SNOW',
          key: Date.now(),
        }
      ]
    };

    this.demoMsgs = [
      'haha',
      'lol',
      'rofl',
      'The North sux',
      'White walkers are fake news.',
      'I AM THE KING. RESPECT ME.',
      'Our CS130 TA is the best',
      'Lannister4Life'
    ];

    //todo: Remove. For demo only.
    setInterval(() => {
      this.setState(prevState => ({
        messages: [
          ...prevState.messages,
          {
            username: 'KingJ0ffrey',
            message: this.demoMsgs[
              Math.floor(Math.random() * this.demoMsgs.length)
            ],
            key: Date.now()
          }
        ]
      }));
    }, 3000);
  }

  onChatMessageChange = e => {
    this.setState({ chatMessage: e.target.value });
  };

  handleKeyDown = e => {
    // e.preventDefault();
    if (e.keyCode === 13) {
      this.onSend();
    }
  };

  onVoteUp = e => {
    if (this.state.voted === false) {
      this.setState({voted: true});
      fetch('http://localhost:8080/stream/upvote', {
        method: 'POST',
        mode: 'no-cors',
      });
    }
  };

  onVoteDown = e => {
    if (this.state.voted === false) {
      this.setState({voted: true});
      fetch('http://localhost:8080/stream/vote', {
        method: 'POST',
        mode: 'no-cors',
      });
    }
  };

 //todo: change username to be the person who is logged in
 onSend = () => {
  this.setState(prevState => ({
    messages: [
      ...prevState.messages,
      {
        username: 'Warden0fDaNorth',
        message: prevState.chatMessage,
        key: Date.now()
      }
    ],
    chatMessage: ''
  }));
  const form = new FormData();
  form.append(this.state.userName, this.state.chatMessage);
  fetch('http://localhost:8080/addComment', {
      method: 'POST',
      mode: 'no-cors',
      body: form
    });
};

onQueue = e => {
  if (!this.state.inqueue) {
    this.setState({inqueue: true});
    fetch('http://localhost:8080/joinStreamQueue', {
      method: 'POST',
      mode: 'no-cors',
    });
  } else {
    this.setState({inqueue: false});
    fetch('http://localhost:8080/leaveStreamQueue', {
      method: 'POST',
      mode: 'no-cors',
    });
  }
}

componentDidUpdate() {
  let container = this.messagesRef.current;

  // padding top + bottom  + 4px tolerance = 20 (magic number)
  let isScrolledToBottom =
    container.scrollHeight - container.clientHeight <=
    container.scrollTop + 20;

  // scroll to bottom if isScrolledToBottom
  if (isScrolledToBottom) container.scrollTop = container.scrollHeight;
}


  render() {
    const { voted, inqueue, userName, key, chatMessage, messages } = this.state;
    const joinqueue = (
        <button>Join Queue</button>
    )
    const leavequeue = (
      <button>Leave Queue</button>
    )
    return (
      <div className="stream-page">
        <div className="container">
          <img src={logo} alt="logo" />
          <header>Limelight</header>
          <div className="profile-button-container">
            <Menu>
              <MenuButton>Option</MenuButton>
              <MenuList>
                <MenuLink as={Link} to="/myprofile">My Profile</MenuLink>
                <MenuLink as={Link} to="/">Sign Out</MenuLink>
              </MenuList>
            </Menu>
          </div>
          <div className="queue-button" 
            onClick={this.onQueue.bind(this)}>
            {this.state.inqueue ? leavequeue : joinqueue}>
          </div>
          <section className="stream">
            <iframe
              width="100%"
              height="100%"
              src="https://www.youtube.com/embed/nEhOEfSb5zg?start=57&controls=0"
              frameBorder="0"
              allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
              allowFullScreen
            ></iframe>
          </section>
          <section className="stream-info">
            <div className="streamer-username">
              <p>Username</p>
              <p>Time remaining:</p>
            </div>
            <div className="voteUpButton">
              <button
              disabled={this.state.voted}
              onClick={this.onVoteUp.bind(this)}>+5 Seconds</button>
            </div>
            <div className="voteDownButton">
              <button 
              disabled={this.state.voted}
              onClick={this.onVoteDown.bind(this)}>-2 Seconds</button>
            </div>
          </section>
          <section className="chat">
            <div ref={this.messagesRef} className="messages">
              {messages.map(({ username, message, key }) => (
                <p key={key}>
                  <span className="bold">{username}</span>: {message}
                </p>
              ))}
            </div>
            <div className="input-box">
              <input
                type="text"
                // name="username"
                value={chatMessage}
                onChange={this.onChatMessageChange}
                onKeyDown={this.handleKeyDown}
              />
              <button onClick={this.onSend}>Send</button>
            </div>
          </section>
        </div>
      </div>
    );
  }
}

export default Stream;
