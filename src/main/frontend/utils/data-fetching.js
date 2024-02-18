import axios from 'axios';
import Cookies from 'js-cookie';

// adds the CSRF Token to every request
axios.interceptors.request.use((req) => {
    if (
        req.method === 'post' ||
        req.method === 'delete' ||
        req.method === 'put' ||
        req.method === 'patch'
    ) {
        if (!(/^http:.*/.test(req.url) || /^https:.*/.test(req.url))) {
            req.headers.common = {
                ...req.headers.common,
                'X-XSRF-TOKEN': Cookies.get('XSRF-TOKEN'),
            };
        }
    }

    return req;
});

export const getUser = async () => {
    try {
        const userData = await axios.get('/user');

        return {
            isLoggedIn: true,
            username: userData.data.email,
            errorMessage: '',
        };
    } catch (exception) {
        let errorMessage = '';
        if (exception instanceof Error) {
            errorMessage = exception.message;
        }

        const req = await axios.get('/error?message=true');
        if (req.data) {
            errorMessage = req.data;
        }

        return {
            isLoggedIn: false,
            username: '',
            errorMessage,
        };
    }
};

export const logoutUser = async () => {
    await axios.post('/logout');
    window.location.href = process.env.DOMAIN_URL;
};
