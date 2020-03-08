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
        userName: localStorage.getItem('userName'),
        key: localStorage.getItem('userSessionKey'),
        firstName: '',
        firstNameChanged: false,
        lastName: '',
        lastNameChanged:  false,
        email: '',
        emailChanged: false,
        instagram: '',
        instagramChanged: false,
        youtube: '',
        youtubeChanged: false,
        facebook: '',
        facebookChanged: false,
        // twitter: '',
        // twitterChanged: false,
        other: '',
        otherChanged: false,
    };
}

    handleFirstNameChage = e => {
        this.setState({firstName: e.target.value});
        this.setState({firstNameChanged: true});
    };

    handleLastNameChage = e => {
        this.setState({lastName: e.target.value});
        this.setState({lastNameChanged: true});
    };

    handleEmailChange = e => {
        this.setState({email: e.target.value});
        this.setState({emailChanged: true});
    };

    handleInstagramChange = e => {
        this.setState({instagram: e.target.value});
        this.setState({instagramChanged: true});
    };

    handleYoutubeChange = e => {
        this.setState({youtube: e.target.value});
        this.setState({youtubeChanged: true});
    };

    handleFacebookChange = e => {
        this.setState({facebook: e.target.value});
        this.setState({facebookChanged: true});
    };

    // handleTwitterChange = e => {
    //     this.setState({twitter: e.target.value});
    //     this.setState({twitterChanged: true});
    // };

    handleOtherChange = e => {
        this.setState({other: e.target.value});
        this.setState({otherChanged: true});
    };

    handleSubmit = e => {
        e.preventDefault();
        
        if (this.state.firstNameChanged) {
            const form  = new FormData();
            form.append('userName', this.state.userName);
            form.append('key', this.state.key);
            form.append('attribute', 'FIRST_NAME');
            form.append('value', this.state.firstName);
        
            fetch('https://limelight-ucla.herokuapp.com/app/edit', {
                method: 'POST',
                mode: 'no-cors',
                body: form
            });
        }

        if (this.state.lastNameChanged) {
            const form  = new FormData();
            form.append('userName', this.state.userName);
            form.append('key', this.state.key);
            form.append('attribute', 'LAST_NAME');
            form.append('value', this.state.lastName);
        
            fetch('https://limelight-ucla.herokuapp.com/app/edit', {
                method: 'POST',
                mode: 'no-cors',
                body: form
            });
        }

        if (this.state.emailChanged) {
            const form  = new FormData();
            form.append('userName', this.state.userName);
            form.append('key', this.state.key);
            form.append('attribute', 'EMAIL');
            form.append('value', this.state.email);
        
            fetch('https://limelight-ucla.herokuapp.com/app/edit', {
                method: 'POST',
                mode: 'no-cors',
                body: form
            });
        }

        if (this.state.instagramChanged) {
            const form  = new FormData();
            form.append('userName', this.state.userName);
            form.append('key', this.state.key);
            form.append('attribute', 'SOCIAL_MEDIA_HANDLE');
            form.append('platform', 'INSTAGRAM');
            form.append('value', this.state.instagram);
        
            fetch('https://limelight-ucla.herokuapp.com/app/edit', {
                method: 'POST',
                mode: 'no-cors',
                body: form
            });
        }

        if (this.state.youtubeChanged) {
            const form  = new FormData();
            form.append('userName', this.state.userName);
            form.append('key', this.state.key);
            form.append('attribute', 'SOCIAL_MEDIA_HANDLE');
            form.append('platform', 'YOUTUBE');
            form.append('value', this.state.youtube);
        
            fetch('https://limelight-ucla.herokuapp.com/app/edit', {
                method: 'POST',
                mode: 'no-cors',
                body: form
            });
        }

        if (this.state.facebookChanged) {
            const form  = new FormData();
            form.append('userName', this.state.userName);
            form.append('key', this.state.key);
            form.append('attribute', 'SOCIAL_MEDIA_HANDLE');
            form.append('platform', 'FACEBOOK');
            form.append('value', this.state.facebook);
        
            fetch('https://limelight-ucla.herokuapp.com/app/edit', {
                method: 'POST',
                mode: 'no-cors',
                body: form
            });
        }

        // if (this.state.twitterChanged) {
        //     const form  = new FormData();
        //     form.append('userName', this.state.userName);
        //     form.append('key', this.state.key);
        //     form.append('attribute', 'SOCIAL_MEDIA_HANDLE');
        //     form.append('platform', 'SNAPCHAT');
        //     form.append('value', this.state.twitter);
        
        //     fetch('https://limelight-ucla.herokuapp.com/app/edit', {
        //         method: 'POST',
        //         headers: {
        //             'Access-Control-Allow-Origin':'*'
        //           },
        //         body: form
        //     });
        // }

        if (this.state.otherChanged) {
            const form  = new FormData();
            form.append('userName', this.state.userName);
            form.append('key', this.state.key);
            form.append('attribute', 'OTHER_INFO');
            form.append('value', this.state.other);
        
            fetch('https://limelight-ucla.herokuapp.com/app/edit', {
                method: 'POST',
                mode: 'no-cors',
                body: form
            });
        }
    };

componentWillMount(){
    const userNameQuery = encodeURIComponent(this.state.userName);
    fetch(`https://limelight-ucla.herokuapp.com/app/getUser?userName=${userNameQuery}`, {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin':'*',
        },
    })
    .then((response) => {
        return response.json();
    })
    .then((response) => {
        this.setState({firstName: response.firstName});
        this.setState({lastName: response.lastName});
        this.setState({email: response.email});
        this.setState({other: response.otherInfo});
        if (Object.keys(response.socialMediaHandles).length !== 0 ){
            if (response.socialMediaHandles['INSTAGRAM'] !== null) {
                this.setState({instagram: response.socialMediaHandles['INSTAGRAM']});
            }
            if (response.socialMediaHandles['YOUTUBE'] !== null) {
                this.setState({youtube: response.socialMediaHandles['YOUTUBE']});
            }
            if (response.socialMediaHandles['FACEBOOK'] !== null) {
                this.setState({facebook: response.socialMediaHandles['FACEBOOK']});
            }
            if (response.socialMediaHandles['TWITTER'] !== null) {
                this.setState({twitter: response.socialMediaHandles['TWITTER']});
            }
        }
    });
}
  render() {
      const{ userName, key, firstName, lastName, email, instagram, youtube, facebook, twitter, other } = this.state;
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
                    <p>{userName}</p>
                    {/* <p>{key}</p> */}
                </div>
            </section>
            <section className="body">
                <form onSubmit={this.handleSubmit} id="form">
                    <div className="form-input">
                        <label htmlFor="firstName">First Name</label>
                        <input 
                        type="text" 
                        name="firstName" 
                        value={firstName} 
                        onChange={this.handleFirstNameChage}/>
                    </div>
                    
                    <div className="form-input">
                        <label htmlFor="lastName">Last Name</label>
                        <input
                        type="text"
                        name="lastName"
                        value={lastName} 
                        onChange={this.handleLastNameChage}/>
                    </div>
                    
                    <div className="form-input">
                        <label htmlFor="mail">E-Mail</label>
                        <input
                        type="text"
                        name="email"
                        value={email}
                        onChange={this.handleEmailChange} />
                    </div>

                    <div className="form-input">
                        <label htmlFor="instagram">Instagram</label>
                        <input type="text"
                        name="instagram"
                        value={instagram || ''}
                        onChange={this.handleInstagramChange} />
                    </div>

                    <div className="form-input">
                        <label htmlFor="youtube">Youtube</label>
                        <input type="text"
                        name="youtube"
                        value={youtube || ''}
                        onChange={this.handleYoutubeChange} />
                    </div>
                    
                    <div className="form-input">
                        <label htmlFor="facebook">Facebook</label>
                        <input type="text"
                        name="facebook"
                        value={facebook || ''}
                        onChange={this.handleFacebookChange} />
                    </div>

                    {/* <div className="form-input">
                        <label htmlFor="twitter">Twitter</label>
                        <input type="text"
                        name="twitter"
                        value={twitter || ''}
                        onChange={this.handleTwitterChange} />
                    </div> */}

                    <div className="form-input">
                        <label htmlFor="other">Other</label>
                        <input type="text"
                        name="other" 
                        value={other || ''} 
                        onChange={this.handleOtherChange} />
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
