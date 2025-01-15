node {
  stage('SCM') {
    checkout scm
  }

  stage('Build') { 
      steps {
          sh 'mvn -B -DskipTests clean package' 
      }
  }


stage("Quality Gate"){
  timeout(time: 2, unit: 'MINUTES') { // Just in case something goes wrong, pipeline will be killed after a timeout
    def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
    if (qg.status != 'OK') {
      error "Pipeline aborted due to quality gate failure: ${qg.status}"
    }
  }
}
