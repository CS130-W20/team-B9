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


class Stream extends React.Component {
  constructor(props) {
    super(props);

    this.messagesRef = React.createRef();

    this.state = {
      chatMessage: '',
      messages: [
        {
          username: 'KingJ0ffrey',
          message: 'BOW TO ME JON SNOW',
          key: Date.now()
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
  };

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
    const { chatMessage, messages } = this.state;

    return (
      <div className="stream-page">
        <div className="container">
          <header>header placeholder</header>
          <div className="profile-button-container">
            <Menu>
              <MenuButton>Option</MenuButton>
              <MenuList>
                <MenuLink as={Link} to="/myprofile">My Profile</MenuLink>
                <MenuLink as={Link} to="/">Sign Out</MenuLink>
              </MenuList>
            </Menu>
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
          <section className="stream-info">stream info placeholder</section>
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
