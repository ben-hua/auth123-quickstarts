import { useAuth } from 'react-oidc-context';
import './App.css';
import reactLogo from './assets/react.svg';
import viteLogo from '/vite.svg';


function App() {
  const auth = useAuth();

  return (
    <>
      <div>
        <a href="https://vitejs.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        {auth.isAuthenticated ?
          (
            <>
              <h3>
                Hi 你已经登录了~
              </h3>
              <p>用户名: {auth.user?.profile.preferred_username}</p>
              <p>邮箱: {auth.user?.profile.email}</p>

              <button onClick={() => { auth.signoutSilent(); auth.removeUser(); }}>
                Sign out
              </button>
            </>
          ) :
          (
            <>
              <h3 >
                当前未登录.
              </h3>
              <button onClick={() => auth.signinRedirect()} >
                Sign in
              </button>
            </>
          )
        }
      </div>
    </>
  )
}

export default App
