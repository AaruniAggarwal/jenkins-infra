def call(){
    withCredentials([string(credentialsId: 'AUTH_YAML', variable: 'FILE')]) {
        sh 'echo  $FILE > $WORKSPACE/deploy/data/auth.yaml'
    }
}
