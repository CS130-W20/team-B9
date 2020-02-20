import React from 'react';
import { Link } from 'react-router-dom';
import './profile.scss';
import logo from '../assets/logo.png'

class UserProfile extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
        username: '',
        name: '',
        age: '',
        contact: '',
        instagram: '',
        youtube: '',
        twitter: '',
        other: ''
    }
  }

  render() {
      const{ username, name, age, contact, instagram, youtube, twitter, other } = this.state;
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
                <div className="upload-pic-button-container">
                    <form action="/action_page.php">
                        <input type="file" id="img" name="img" accept="image/*"/>
                    </form>
                </div>
            </section>
            <section className="body">
                <form>
                    <tr>
                        <label for="Name">Name</label>
                        <input type="text" name="name" value={name} />
                    </tr>
                    <tr>
                        <label for="age">Age</label>
                    <   input type="number" name="age" value={age} />
                    </tr>
                    <tr>
                        <label for="contact">Contact</label>
                        <input type="text" name="contact" value={contact} />
                    </tr>
                    <tr>
                        <label for="instagram">Instagram</label>
                        <input type="text" name="instagram" value={instagram} />
                    </tr>
                    <tr>
                        <label for="youtube">Youtube</label>
                        <input type="text" name="youtube" value={youtube} />
                    </tr>
                    <tr>
                        <label for="twitter">Twitter</label>
                        <input type="text" name="twitter" value={twitter} />
                    </tr>
                    <tr>
                        <label for="other">Other</label>
                        <input type="text" name="other" value={other} />
                    </tr>
                </form>
                <div className="save-button-container">
                    <Link to="/stream">Save</Link>                    
                </div>
            </section>
        </div>
    );
  }
}

export default UserProfile;
