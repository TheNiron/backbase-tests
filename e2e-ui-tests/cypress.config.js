module.exports = {
  reporter: 'cypress-mochawesome-reporter',
  defaultCommandTimeout: 8000,
  e2e: {
    setupNodeEvents (on, config) {
      require('cypress-mochawesome-reporter/plugin')(on)
    },
    videoCompression: 0,
    env: {
      userName: 'candidatex',
      password: 'qa-is-cool',
      backbase_url: '@qa-task.backbasecloud.com',
      base64_encode_credentials: 'Y2FuZGlkYXRleDpxYS1pcy1jb29s',
      backbase_host: 'https://qa-task.backbasecloud.com',
      conduitUserName1: 'niron@gmail.com',
      conduitPassword1: 'rasanjana29',
      conduitUserName2: 'sahan@gmail.com',
      conduitPassword2: 'rasanjana29'
    }
  }
}
