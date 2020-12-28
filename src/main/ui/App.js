import React, {useState, useEffect, Fragment} from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';

// specify root api rest path
const root = '/api';

// add xsrf cookie to every stuff
axios.interceptors.request.use((req) => {
	if (
		req.method === "post" ||
		req.method === "delete" ||
		req.method === "put" ||
		req.method === "patch"
	) {
		// check if relative to url only
		if (!(/^http:.*/.test(req.url) || /^https:.*/.test(req.url))) {
			req.headers.common = {
				...req.headers.common,
				"X-XSRF-TOKEN": Cookies.get("XSRF-TOKEN"),
			};
		}
	}

	return req;
});

const App = () => {

	const [user, setUser] = useState("");
	const [isLoggedIn, setIsLoggedIn] = useState("");
	const [isLoading, setIsLoading] = useState(true);
	const [errorLogging, setErrorLogging] = useState("");

	const logout = async (e) => {
		await axios.post("/logout");
		window.location.href = "http://localhost:8081";
	};

	useEffect(() => {

		const loadData = async () => {
			const userData = await axios.get("/user");
			return userData;
		}

		loadData().then(async (userData) => {
			setUser(userData.data.name);
			setIsLoggedIn('succeeded');
		}).catch(async e => {
			setIsLoggedIn('failed');
			const req = await axios.get("/error");
			if(req.data !== "") {
				setErrorLogging(req.data);
			}
		});

	}, []);

	useEffect(() => {

		if(isLoggedIn === 'succeeded')
			setIsLoading(false);

		if(isLoggedIn === 'failed')
			setIsLoading(false);

	}, [isLoggedIn])

	if(isLoading) {
		return (
		  <b>Loading ...</b>
		);
	} else {
		if(isLoggedIn === 'succeeded') {
			return (
				<Fragment>
					<div>Hello {user}</div>
					<button onClick={logout}>Logout</button>
				</Fragment>
			);
		} else {
			return(
				<Fragment>
					<h1>Login</h1>
					<strong style={{color: 'red'}}>
						{ errorLogging !== "" ? errorLogging : "" }
					</strong>
					<div className="container">
						<div>
							With GitHub: <a href="/oauth2/authorization/github">click here</a>
						</div>
					</div>
				</Fragment>
			);
		}
	}
}

export default App;