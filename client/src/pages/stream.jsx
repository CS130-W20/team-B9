import React from 'react';
import { Link } from 'react-router-dom';
import './stream.scss';
import { Menu, MenuList, MenuButton, MenuLink } from '@reach/menu-button';
import '@reach/menu-button/styles.css';
import logo from '../assets/logo.png';
import Popup from 'reactjs-popup';
import clock from '../assets/clock.png';

class Stream extends React.Component {
  constructor(props) {
    super(props);

    this.messagesRef = React.createRef();

    this.state = {
      chatMessage: '',
      voted: false,
      inQueue: false,
      userName: localStorage.getItem('userName'),
      key: localStorage.getItem('userSessionKey'),
      streamerName: '',
      streamerFirstName: '',
      streamerLastName: '',
      streamerEmail: '',
      streamerInstagram: '',
      streamerYoutube: '',
      streamerFacebook: '',
      streamerOther: '',
      // streamertwitter: '',
      timeRemain: '',
      messages: [],
      baseVideoURL: '',
      videoURLWithRandomParam: ''
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

  onVoteUp = e => {
    if (this.state.voted === false) {
      this.setState({ voted: true });
      fetch('http://limelight-ucla.herokuapp.com/stream/upvote', {
        method: 'POST',
        mode: 'no-cors'
      });
    }
  };

  onVoteDown = e => {
    if (this.state.voted === false) {
      this.setState({ voted: true });
      fetch('https://limelight-ucla.herokuapp.com/stream/downvote', {
        method: 'POST',
        mode: 'no-cors'
      });
    }
  };

  //todo: change username to be the person who is logged in
  onSend = () => {
    this.setState(prevState => ({
      chatMessage: ''
    }));
    const form = new FormData();
    form.append('userName', this.state.userName);
    form.append('comment', this.state.chatMessage);
    fetch('https://limelight-ucla.herokuapp.com/stream/addComment', {
      method: 'POST',
      mode: 'no-cors',
      body: form
    });
  };

  joinQueue = e => {
    document.getElementById('getFile').click();
  };

  onUploadVideo = e => {
    this.setState({ inQueue: true });

    var file = e.target.files[0];

    var newFileName = this.state.userName + '.mp4';
    var form = new FormData();
    form.append('file', file, newFileName);

    const userNameQuery = encodeURIComponent(this.state.userName);
    const keyQuery = encodeURIComponent(this.state.key);
    fetch(
      `https://limelight-ucla.herokuapp.com/stream/upload?userName=${userNameQuery}&key=${keyQuery}`,
      {
        method: 'POST',
        mode: 'no-cors',
        body: form
      }
    );
  };

  leaveQueue = e => {
    this.setState({ inQueue: false });

    const form = new FormData();
    form.append('userName', this.state.userName);
    form.append('key', this.state.key);

    fetch(`https://limelight-ucla.herokuapp.com/stream/leaveStreamQueue`, {
      method: 'POST',
      mode: 'no-cors',
      body: form
    });
  };

  setDummy() {
    this.setState({ streamerName: 'No One is Streaming' });
    this.setState({ streamerFirstName: '-' });
    this.setState({ streamerLastName: '-' });
    this.setState({ streamerEmail: '-' });
    this.setState({ streamerOther: '-' });
    this.setState({ streamerInstagram: '-' });
    this.setState({ streamerYoutube: '-' });
    this.setState({ streamerFacebook: '-' });
    // this.setState({twitter: response.socialMediaHandles['TWITTER']});
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
  // fetch the current streamer information and update upon change every second
  componentDidMount() {
    setInterval(() => {
      fetch(`https://limelight-ucla.herokuapp.com/stream/get`, {
        method: 'GET'
      })
        .then(response => response.text())
        .then(url => {
          if (this.state.baseVideoURL !== url) {
            this.setState({
              baseVideoURL: url,
              videoURLWithRandomParam: url + `?${Math.random()}`,
              voted: false
            });
            const video = document.getElementById('myVideo');
            if (video !== null) video.load();
          }
          if (this.state.userName === url.split('/')[3].split('.')[0]) {
            this.setState({ inQueue: false });
          }
        });

      fetch(`https://limelight-ucla.herokuapp.com/stream/getCurrentStreamer`, {
        method: 'GET',
        headers: {
          'Access-Control-Allow-Origin': '*',
          'Content-Type': 'application/json',
          Accept: 'application/json'
        }
      })
        .then(response => {
          return response.text();
        })
        .catch(err => {
          // response does not return properly formatted username for the dummy video
          // set all properties based on dummy
          this.setDummy();
        })
        .then(response => {
          if (response !== null) {
            // if the streamer is not dummy streamer, and the streamer is different from previous streamer
            // fetch the new streamer information and update the states
            if (
              this.state.streamerName !== response &&
              response !== '' &&
              response !== 'Dummy Streamer'
            ) {
              this.setState({ streamerName: response });
              const userNameQuery = encodeURIComponent(this.state.streamerName);
              fetch(
                `https://limelight-ucla.herokuapp.com/app/getUser?userName=${userNameQuery}`,
                {
                  method: 'GET',
                  headers: {
                    'Access-Control-Allow-Origin': '*'
                  }
                }
              )
                .then(response => {
                  return response.json();
                })
                .then(response => {
                  if (response !== null) {
                    this.setState({ streamerFirstName: response.firstName });
                    this.setState({ streamerLastName: response.lastName });
                    this.setState({ streamerEmail: response.email });
                    this.setState({ streamerOther: response.otherInfo });
                    if (Object.keys(response.socialMediaHandles).length !== 0) {
                      if (response.socialMediaHandles['INSTAGRAM'] !== null) {
                        this.setState({
                          streamerInstagram:
                            response.socialMediaHandles['INSTAGRAM']
                        });
                      } else {
                        this.setState({ streamerInstagram: '' });
                      }
                      if (response.socialMediaHandles['YOUTUBE'] !== null) {
                        this.setState({
                          streamerYoutube:
                            response.socialMediaHandles['YOUTUBE']
                        });
                      } else {
                        this.setState({ streamerYoutube: '' });
                      }
                      if (response.socialMediaHandles['FACEBOOK'] !== null) {
                        this.setState({
                          streamerFacebook:
                            response.socialMediaHandles['FACEBOOK']
                        });
                      } else {
                        this.setState({ streamerFacebook: '' });
                      }
                      // if (response.socialMediaHandles['TWITTER'] !== null) {
                      //     this.setState({streamerTwitter: response.socialMediaHandles['TWITTER']});
                      // }
                    } else {
                      this.setState({ streamerInstagram: '' });
                      this.setState({ streamerYoutube: '' });
                      this.setState({ streamerFacebook: '' });
                    }
                  }
                });
            }
            // else two cases: on startup, response is Dummy Streamer
            // or Dummy streamer kept playing after queue becomes empty
            // either way, set the dummy profile variables
            else if (
              this.state.streamerName === 'Dummy Streamer' ||
              response === 'Dummy Streamer'
            ) {
              this.setDummy();
            }
          }
        });

      fetch(`https://limelight-ucla.herokuapp.com/stream/getRemainingTime`, {
        method: 'GET',
        headers: {
          'Access-Control-Allow-Origin': '*'
        }
      })
        .then(response => {
          if (response.ok) {
            return response.json();
          }
        })
        .then(response => {
          this.setState({ timeRemain: response === -1 ? '-' : response });
        });
    }, 1000);

    //get comments
    setInterval(() => {
      fetch(`https://limelight-ucla.herokuapp.com/stream/getComments`, {
        method: 'GET'
      })
        .then(res => res.json())
        .then(comments => this.setState({ messages: comments }));
    }, 500);
  }

  render() {
    const {
      voted,
      inQueue,
      userName,
      key,
      chatMessage,
      messages,
      streamerName,
      timeRemain,
      streamerFirstName,
      streamerLastName,
      streamerEmail,
      streamerOther,
      streamerFacebook,
      streamerInstagram,
      streamerYoutube
    } = this.state;
    const joinQueue = (
      <>
        <button className="queue" onClick={this.joinQueue}>
          Join Queue
        </button>
        <input type="file" id="getFile" onChange={this.onUploadVideo} />
      </>
    );
    const leaveQueue = (
      <button className="queue leave" onClick={this.leaveQueue}>
        Leave Queue
      </button>
    );
    return (
      <div className="stream-page">
        <div className="container">
          <header>
            <img src={logo} alt="logo" />
            <p>Limelight</p>
            <div className="profile-button-container">
              {this.state.inQueue ? leaveQueue : joinQueue}
              <Menu>
                <MenuButton className="menu">☰</MenuButton>
                <MenuList>
                  <MenuLink as={Link} to="/myprofile">
                    My Profile
                  </MenuLink>
                  <MenuLink as={Link} to="/">
                    Sign Out
                  </MenuLink>
                </MenuList>
              </Menu>
            </div>
          </header>
          <section className="stream">
            <video id="myVideo" autoPlay loop name="media">
              <source
                src={this.state.videoURLWithRandomParam}
                type="video/mp4"
              />
            </video>
          </section>
          <section className="stream-info">
            <div className="stream-info-container">
              <Popup
                trigger={<p className="username">{streamerName} ⓘ</p>}
                modal
              >
                {close => (
                  <div className="modal">
                    <a className="close" onClick={close}>
                      &times;
                    </a>
                    <div className="header">{streamerName}</div>
                    <div className="content">
                      First Name: {streamerFirstName}
                      <br />
                      Last Name: {streamerLastName}
                      <br />
                      Email: {streamerEmail}
                      <br />
                      Instagram: {streamerInstagram}
                      <br />
                      Youtube: {streamerYoutube}
                      <br />
                      Facebook: {streamerFacebook}
                      <br />
                      Other: {streamerOther}
                      <br />
                    </div>
                  </div>
                )}
              </Popup>

              <p className="time-remaining">
                Time Remaining: {timeRemain} seconds
              </p>
            </div>
            <button
              disabled={this.state.voted}
              onClick={this.onVoteDown.bind(this)}
            >
              -2 seconds
            </button>

            <button
              disabled={this.state.voted}
              onClick={this.onVoteUp.bind(this)}
            >
              +5 seconds
            </button>
          </section>
          <section className="chat">
            <div ref={this.messagesRef} className="messages">
              {messages.map(({ username, comment, key }) => (
                <p key={key}>
                  <span className="bold">{username}</span>: {comment}
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
