def call(){
    withCredentials([string(credentialsId: 'PRIVATE_KEY_ODF', variable: 'FILE')]) {
        sh 'echo  $FILE > $RSA_KEY'
        sh 'chmod 0400 $RSA_KEY'
    }
}
