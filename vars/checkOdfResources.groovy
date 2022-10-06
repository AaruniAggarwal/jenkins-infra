def call(){
    script {
        ansiColor('xterm') {
            echo ""
        }
        try {
            sh '''
               cd ${WORKSPACE}/deploy
               scp -o 'StrictHostKeyChecking no' -i id_rsa ${WORKSPACE}/scripts/check-odf-resources.sh root@${BASTION_IP}:
               ssh -o 'StrictHostKeyChecking no' -i id_rsa root@${BASTION_IP} "chmod +x check-odf-resources.sh; ./check-odf-resources.sh"
               scp -o 'StrictHostKeyChecking no' -i id_rsa root@${BASTION_IP}:/root/odf-resources*.log ${WORKSPACE}/
            '''
        }
        catch (err) {
            echo 'Error ! Gathering of ODF resources failed!'
            env.FAILED_STAGE=env.STAGE_NAME
            throw err
        }
    }
}

