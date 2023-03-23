
var timeoutProcess
function triggerTimeout(timeOutMinute) {
  timeoutProcess = setTimeout(function () {
    $('#timeoutModal').modal('show')
  }, timeOutMinute * 60 * 1000 - 2000)
}
function triggerStopTimeout() {
  if (timeoutProcess) {
    clearTimeout(timeoutProcess)
  }
}
export default function ({ app: { $axios, $cookies }, store }) {
  // $axios.defaults.baseURL = process.env.baseUrl;
  $axios.defaults.timeout = 300000;
  $axios.defaults.withCredentials = true;
  $axios.interceptors.request.use(config => {
    return config;
  });
  $axios.interceptors.response.use(response => {
    return response.data;
  });
  $axios.onRequest(config => {
    if (process.client) {
      const loginToken = store.state.loginToken
      if (loginToken) {
        const to = store.state.timeout
        triggerStopTimeout()
        triggerTimeout(to)
      } else {
        triggerStopTimeout()
      }
    }
    return config;
  });
  $axios.onError(error => {
    const code = parseInt(error.response && error.response.status);

  });
}

function getAuthCookie(response) {
  let currentCookie = "";
  if (response && response.headers) {
    const authCookie = response.headers["set-cookie"];
    if (authCookie) {

      currentCookie = authCookie
        .toString()
        .split(";")[0]
        .toString();
      currentCookie = currentCookie.split("=")[1].toString();
    }
  }

  return currentCookie;
}
