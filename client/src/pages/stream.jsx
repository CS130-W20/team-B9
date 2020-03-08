import React from 'react';
import { Link } from 'react-router-dom';
import './stream.scss';
import { Menu, MenuList, MenuButton, MenuLink } from '@reach/menu-button';
import '@reach/menu-button/styles.css';
import logo from '../assets/logo.png';
import Popup from "reactjs-popup";

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
      messages: [
        {
          username: 'KingJ0ffrey',
          message: 'BOW TO ME JON SNOW',
          key: Date.now()
        }
      ],
      videoURL: ''
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
      //this.setState({ voted: true });
      fetch('http://localhost:8080/stream/upvote', {
        method: 'POST',
        mode: 'no-cors'
      });
    }
  };

  onVoteDown = e => {
    if (this.state.voted === false) {
      //this.setState({ voted: true });
      fetch('http://localhost:8080/stream/downvote', {
        method: 'POST',
        mode: 'no-cors'
      });
    }
  };

  //todo: change username to be the person who is logged in
  onSend = () => {
    // this.setState(prevState => ({
    //   messages: [
    //     ...prevState.messages,
    //     {
    //       username: 'Warden0fDaNorth',
    //       message: prevState.chatMessage,
    //       key: Date.now()
    //     }
    //   ],
    //   chatMessage: ''
    // }));
    //console.log(this.state.chatMessage);
    const form = new FormData();
    form.append('userName', this.state.userName);
    form.append('comment', this.state.chatMessage);
    fetch('http://localhost:8080/stream/addComment', {
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

    var file = e.target.files[0]

    var newFileName = this.state.userName + ".mp4";
    var form = new FormData();
    form.append('file', file, newFileName);

    const userNameQuery = encodeURIComponent(this.state.userName);
    const keyQuery = encodeURIComponent(this.state.key);
    console.log(userNameQuery);
    fetch(
      `http://localhost:8080/stream/upload?userName=${userNameQuery}&key=${keyQuery}`,
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

    fetch(
      `http://localhost:8080/stream/leaveStreamQueue`,
      {
        method: 'POST',
        mode: 'no-cors',
        body: form
      }
    );
  };

  onCheckProfile = e => {
    console.log("called");
    
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

  componentDidMount() {
    setInterval(() => {
      fetch(`http://localhost:8080/stream/get`, {
        method: 'GET'
      })
        .then(response => response.text())
        .then(url => {
          if (this.state.videoURL !== url) {
            this.setState({ videoURL: url });
            document.getElementById('myVideo').load();
          }
        });
      
      fetch(`http://localhost:8080/stream/getCurrentStreamer`, {
        method: 'GET',
        headers: {
          'Access-Control-Allow-Origin':'*',
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        },
      })
      .then((response) => {
        return response.text();
      })
      .catch(err => {        
        const DummyStreamer = 'Dummy Streamer'
        this.setState({streamerName: DummyStreamer});
          this.setState({streamerFirstName: 'DSFirstName'});
          this.setState({streamerLastName: 'DSLastName'});
          this.setState({streamerEmail: 'DSEmail@gmail.com'});
          this.setState({streamerOther:  'This is a dummy streamer'});
          this.setState({streamerInstagram: 'DSInsta'});
          this.setState({streamerYoutube: 'DSYoutube'});
          this.setState({streamerFacebook: 'DSFacebook'});
          // this.setState({twitter: response.socialMediaHandles['TWITTER']});
      })
      .then((response) => {
        if (typeof response !== 'undefined') {
          if (this.state.streamerName !== response && this.state.streamerName !== 'Dummy Streamer' && this.state.streamerName !== '') {
            this.setState({streamerName: response});
            const userNameQuery = encodeURIComponent(this.state.streamerName);
            fetch(`http://localhost:8080/app/getUser?userName=${userNameQuery}`, {
                method: 'GET',
                headers: {
                    'Access-Control-Allow-Origin':'*',
                },
            })
            .then((response) => {
              return response.json();
            })
           .then((response) => {
              this.setState({streamerFirstName: response.firstName});
              this.setState({streamerLastName: response.lastName});
              this.setState({streamerEmail: response.email});
              this.setState({streamOther: response.otherInfo});
              if (Object.keys(response.socialMediaHandles).length !== 0 ){
                  if (response.socialMediaHandles['INSTAGRAM'] !== null) {
                      this.setState({streamerInstagram: response.socialMediaHandles['INSTAGRAM']});
                  }
                  if (response.socialMediaHandles['YOUTUBE'] !== null) {
                      this.setState({streamerYoutube: response.socialMediaHandles['YOUTUBE']});
                  }
                  if (response.socialMediaHandles['FACEBOOK'] !== null) {
                      this.setState({streamerFacebook: response.socialMediaHandles['FACEBOOK']});
                  }
                  // if (response.socialMediaHandles['TWITTER'] !== null) {
                  //     this.setState({streamerTwitter: response.socialMediaHandles['TWITTER']});
                  // }
              }
          });
        }
        else {
          this.setState({streamerName: 'Dummy Streamer'});
          this.setState({streamerFirstName: 'DSFirstName'});
          this.setState({streamerLastName: 'DSLastName'});
          this.setState({streamerEmail: 'DSEmail@gmail.com'});
          this.setState({streamerOther:  'This is a dummy streamer'});
          this.setState({streamerInstagram: 'DSInsta'});
          this.setState({streamerYoutube: 'DSYoutube'});
          this.setState({streamerFacebook: 'DSFacebook'});
          // this.setState({twitter: response.socialMediaHandles['TWITTER']});
        }
      } 
      });

      fetch(`http://localhost:8080/stream/getRemainingTime`, {
        method: 'GET',
        headers: {
          'Access-Control-Allow-Origin':'*',
        },
      })
      .then((response) => {
        if (response.ok) {
          return response.json();
        } else {
          const DummyStreamerTime = 0
          this.setState({streamerName: DummyStreamerTime});
        }
      })
      .then((response) => {
        this.setState({timeRemain: response});
      });

    }, 1000);
  }

  render() {
    const { voted, inQueue, userName, key, chatMessage, messages, streamerName, timeRemain, streamerFirstName, streamerLastName,
    streamerEmail, streamerOther, streamerFacebook, streamerInstagram, streamerYoutube} = this.state;
    const joinQueue = (
      <>
        <button onClick={this.joinQueue}>Join Queue</button>
        <input type="file" id="getFile" onChange={this.onUploadVideo} />
      </>
    );
    const leaveQueue = <button onClick={this.leaveQueue}>Leave Queue</button>;
    return (
      <div className="stream-page">
        <div className="container">
          <img src={logo} alt="logo" />
          <header>Limelight</header>
          <div className="profile-button-container">
            <Menu>
              <MenuButton>Option</MenuButton>
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
          <div className="queue-button">
            {this.state.inQueue ? leaveQueue : joinQueue}>
          </div>
          <section className="stream">
            <video id="myVideo" controls autoPlay name="media">
              <source src={this.state.videoURL} type="video/mp4" />
            </video>
          </section>
          <section className="stream-info">
            <div className="streamer-username">
            <p>Username: {streamerName}</p>
            <p>Time remaining: {timeRemain} seconds</p>
            </div>
            <div className="checkProfileButton">
              {/* <Popup trigger={<button
                onClick={this.onCheckProfile.bind(this)}> Check Profile</button>} position="right center">
                <span> Modal content </span>
              </Popup> */}
              <Popup trigger={<button className="button" onClick={this.onCheckProfile.bind(this)}> Check Profile </button>} modal>
                {close => (
                  <div className="modal">
                  <a className="close" onClick={close}>
                    &times;
                  </a>
                <div className="header"> Username: {streamerName}</div>
                <div className="content">
                    {" "}
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
                <div className="closeProfile">
                  <button
                    className="button"
                      onClick={() => {
                      close();
                    }}
                  >
            Close Profile
          </button>
        </div>
      </div>
    )}
  </Popup>
            </div>
            <div className="voteUpButton">
              <button
                disabled={this.state.voted}
                onClick={this.onVoteUp.bind(this)}
              >
                +5 Seconds
              </button>
            </div>
            <div className="voteDownButton">
              <button
                disabled={this.state.voted}
                onClick={this.onVoteDown.bind(this)}
              >
                -2 Seconds
              </button>
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
