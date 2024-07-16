
//See amazon authentication example at https://github.com/awsdocs/aws-doc-sdk-examples/blob/main/javascriptv3/example_code/cognito-identity-provider/scenarios/cognito-developer-guide-react-example/frontend-client/src/authService.ts
import { CognitoIdentityProviderClient, InitiateAuthCommand, SignUpCommand, ConfirmSignUpCommand } from "@aws-sdk/client-cognito-identity-provider";
import axios from 'axios';
import {url} from "./App.jsx";

const authClientId = "3daqdgk2g695k9663k3vo30ht8";
const AuthFlow =  "USER_PASSWORD_AUTH";
const appRegion = "us-east-2";

export const getToken = async function(code)
{
    var output = {loggedIn: false, accessToken: null};

    var url1 = url;
    url1 = url1.concat("/public/login");

    var json = {
        code: code
    };

    var result = await axios.post(url1, json);

    console.log(result);
    output.accessToken = result.data.response.access_token;
    output.refreshToken = result.data.response.refresh_token;
    output.idToken = result.data.response.id_token;
    output.account = result.data.account;
    if(output.accessToken != null || output.accessToken != undefined)
    {
        output.loggedIn = true;
    }
    return output;
};

export const cognitoClient = new CognitoIdentityProviderClient({
  region: appRegion,
});

export const signIn = async (username, password) => {
  const params = {
    AuthFlow: "USER_PASSWORD_AUTH",
    ClientId: authClientId,
    AuthParameters: {
      USERNAME: username,
      PASSWORD: password,
    },
  };
  try {
    const command = new InitiateAuthCommand(params);
    const { AuthenticationResult } = await cognitoClient.send(command);
    if (AuthenticationResult) {
      sessionStorage.setItem("idToken", AuthenticationResult.IdToken || '');
      sessionStorage.setItem("accessToken", AuthenticationResult.AccessToken || '');
      sessionStorage.setItem("refreshToken", AuthenticationResult.RefreshToken || '');
      sessionStorage.setItem("session", AuthenticationResult.Session);
      console.log(AuthenticationResult);
      return AuthenticationResult;
    }
  } catch (error) {
    console.error("Error signing in: ", error);
    throw error;
  }
};

export const signUp = async (email, password) => {
  const params = {
    ClientId: clientId,
    Username: email,
    Password: password,
    UserAttributes: [
      {
        Name: "email",
        Value: email,
      },
    ],
  };
  try {
    const command = new SignUpCommand(params);
    const response = await cognitoClient.send(command);
    console.log("Sign up success: ", response);
    return response;
  } catch (error) {
    console.error("Error signing up: ", error);
    throw error;
  }
};
