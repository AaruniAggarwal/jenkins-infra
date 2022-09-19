def call(){
    withCredentials([file(credentialsId: 'PRIVATE_KEY', variable: 'FILE')]) {
        sh 'cp $FILE $RSA_KEY'
        sh 'chmod 600 $RSA_KEY'
    }
}
