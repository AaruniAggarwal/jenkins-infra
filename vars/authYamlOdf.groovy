def call(){
    withCredentials([string(credentialsId: 'ODF_AUTH_FILE', variable: 'FILE')]) {
        sh 'echo  $FILE > $ODF_AUTH_FILE'
    }
}
