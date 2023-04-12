function loginApi(data) {
    return $axios({
      'url': '/user/login',
      'method': 'post',
      data
    })
  }
function sendMsgApi(date){
    return $axios({
        'url': '/user/sendMsg',
        'method': 'post',
        date
    })
}

function loginoutApi() {
  return $axios({
    'url': '/user/loginout',
    'method': 'post',
  })
}

  