node('linux') {
  stage ('Poll') {
    checkout([
      $class: 'GitSCM', branches: [[name: '*/main']], extensions: [],
      userRemoteConfigs: [[url: 'https://github.com/zopencommunity/python-rpds-pyport.git']]])
  }
  stage('Build') {
    build job: 'Port-Pipeline', parameters: [
      string(name: 'PORT_GITHUB_REPO', value: 'https://github.com/zopencommunity/python-rpds-pyport.git'),
      string(name: 'PORT_DESCRIPTION', value: 'Python bindings to Rust's persistent data structures (rpds)'),
      string(name: 'BUILD_LINE', value: 'DEV'),
      booleanParam(name: 'PUBLISH_PYTHON_WHEEL', value: true)
    ]
  }
}
