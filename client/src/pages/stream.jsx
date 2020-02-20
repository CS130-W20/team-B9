import React from 'react';
import { Link } from 'react-router-dom';
import './stream.scss';

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

    // padding top + bottom  + 2px tolerance = 18 (magic number)
    let isScrolledToBottom =
      container.scrollHeight - container.clientHeight <=
      container.scrollTop + 18;

    // scroll to bottom if isScrolledToBottom
    if (isScrolledToBottom) container.scrollTop = container.scrollHeight;
  }

  render() {
    const { chatMessage, messages } = this.state;

    return (
      <div className="stream-page">
        <div className="container">
          <header>header placeholder</header>
          <section className="stream">stream placeholder</section>
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
