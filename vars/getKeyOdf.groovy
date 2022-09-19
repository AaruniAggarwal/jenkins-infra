def call(){
    withCredentials([file(credentialsId: 'PRIVATE_KEY', variable: 'FILE')]) {
        sh 'echo  $FILE > $RSA_KEY'
        sh 'chmod 0400 $RSA_KEY'
    }
}
