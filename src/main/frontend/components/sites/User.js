import React from 'react';
import { useUser } from '../../context/AuthProvider';
import { logoutUser } from '../../utils/data-fetching';

const Profile = () => {
    const user = useUser();

    return (
        <div class="container py-5">
            <span>Hi </span>
            <strong>{user.username} 👋</strong>
            <br />
            <br />
            <br />
            <button
                class="btn btn-danger"
                onClick={logoutUser}
            >
                Logout
            </button>
        </div>
    );
};

export default Profile;
