def call(){
    withCredentials([string(credentialsId: 'ODF_PULL_SECRET', variable: 'FILE')]) {
        sh 'echo  $FILE > $PULL_SECRET_FILE'
    }
}
