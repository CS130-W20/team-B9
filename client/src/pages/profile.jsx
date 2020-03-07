import React from 'react';
import { Link } from 'react-router-dom';
import './profile.scss';
import logo from '../assets/logo.png'

class UserProfile extends React.Component {
  constructor(props) {
    super(props);
    this.name = props.item;
    this.state = {
        // profilePic: null,
        userName: '',
        firstName: '',
        lastname: '',
        email: '',
        instagram: '',
        youtube: '',
        twitter: '',
        snapchat: '',
        other: ''
    };
}

    handleFirstNameChage = e => {
        this.setState({firstname: e.target.value});
    };

    handleLastNameChage = e => {
        this.setState({lastname: e.target.value});
    };

    handleEmailChange = e => {
        this.setState({email: e.target.value});
    };

    handleInstagramChange = e => {
        this.setState({instagram: e.target.value});
    };

    handleYoutubeChange = e => {
        this.setState({youtube: e.target.value});
    };

    handleTwitterChange = e => {
        this.setState({twitter: e.target.value});
    };

    handleSnapchatChange = e => {
        this.setState({snapchat: e.target.value});
    };

    handleOtherChange = e => {
        this.setState({other: e.target.value});
    };

    handleImage = e => {   
        console.log(e.target.files[0]);

        this.setState({
            selectedFile: e.target.files[0],
            loaded: 0,
        })

    };

    handleSubmit = e => {
        e.preventDefault();

        const form  = new FormData();

        for(const name in this.state) {
          form.append(name, this.state[name]);
        }
      
        try {
        fetch('http://localhost:8080/app/edit', {
          method: 'POST',
          mode: 'no-cors',
          body: form
        });
      } catch (e) {
        alert(e.message);
      }
    };


  render() {
      const{ username, firstname, lastname, email, instagram, youtube, twitter, snapchat, other } = this.state;
    return (
        <div className="user-profile-page">
            <section className="hero">
                <div className="header-logo-container">
                    <img src={logo} alt="logo" />
                    <div className="header-text-container">
                        <h1>Limelight</h1>
                    </div>
                </div>
                <div className="return-button-container">
                    <Link to="/stream">Return to stream</Link>
                </div>
                {/* <div className="upload-pic-button-container">
                    <form action="/action_page.php">
                        <input type="file" id="img" name="img" accept="image/*"/>
                    </form>
                </div> */}
                <div className="username">
                    <p>Username</p>
                </div>
            </section>
            <section className="body">
                <form onSubmit={this.handleSubmit} id="form">
                    <div className="form-input">
                    <tr>
                        <label for="firstname">First Name</label>
                        <input 
                        type="text" 
                        name="firstname" 
                        value={firstname} 
                        onChange={this.handleFirstNameChage}/>
                    </tr>
                    </div>
                    
                    <div className="form-input">
                    <tr>
                        <label for="lastname">Last Name</label>
                        <input
                        type="text"
                        name="lastname"
                        value={lastname} 
                        onChange={this.handleLastNameChage}/>
                    </tr>
                    </div>
                    
                    <div className="form-input">
                    <tr>
                        <label for="mail">E-Mail</label>
                        <input
                        type="text"
                        name="email"
                        value={email}
                        onChange={this.handleEmailChange} />
                    </tr>
                    </div>

                    <div className="form-input">
                    <tr>
                        <label for="instagram">Instagram</label>
                        <input type="text"
                        name="instagram"
                        value={instagram}
                        onChange={this.handleInstagramChange} />
                    </tr>
                    </div>

                    <div className="form-input">
                    <tr>
                        <label for="youtube">Youtube</label>
                        <input type="text"
                        name="youtube"
                        value={youtube}
                        onChange={this.handleYoutubeChange} />
                    </tr>
                    </div>
                    
                    <div className="form-input">
                    <tr>
                        <label for="twitter">Twitter</label>
                        <input type="text"
                        name="twitter"
                        value={twitter}
                        onChange={this.handleTwitterChange} />
                    </tr>
                    </div>

                    <div className="form-input">
                    <tr>
                        <label for="snapchat">Snapchat</label>
                        <input type="text"
                        name="snapchat"
                        value={snapchat}
                        onChange={this.handleSnapchatChange} />
                    </tr>
                    </div>

                    <div className="form-input">
                    <tr>
                        <label for="other">Other</label>
                        <input type="text"
                        name="other" 
                        value={other} 
                        onChange={this.handleOtherChange} />
                    </tr>
                    </div>

                </form>
                <div className="save-button-container">
                    <button onClick={this.handleSubmit.bind(this)}>Submit</button>                    
                </div>
            </section>
        </div>
    );
  }
}

export default UserProfile;
