import ReactDOM from 'react-dom/client';
import { AuthProvider } from 'react-oidc-context';
import App from './App.tsx';
import './index.css';


const oidcConfig = {
  authority: "https://dev.auth123.top/realms/quickstart",
  client_id: "front_ui",
  redirect_uri: "http://localhost:5173/",
  onSigninCallback: () => {
    // Remove query string parameters from URL
    window.history.replaceState({}, "", window.location.pathname);
  },

};


const root = ReactDOM.createRoot(document.getElementById('root')!);
root.render(
  <AuthProvider {...oidcConfig}>
    <App />
  </AuthProvider>
);


