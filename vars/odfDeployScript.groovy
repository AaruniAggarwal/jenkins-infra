def call(){
    script {
        ansiColor('xterm') {
            echo ""
        }
        try {
            sh '''
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "mkdir bin; cp /usr/local/bin/oc /root/bin/"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "cp -r /root/openstack-upi/auth/ /root/"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "cd /root/ocs-upi-kvm/scripts; ./deploy-ocs-ci.sh |tee /root/deploy-ocs-ci.log"
               scp -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP}:/root/deploy-ocs-ci.log ${WORKSPACE}/
            '''
        }
        catch (err) {
            echo 'Error ! Deployment of ODF using deploy-ocs-ci.sh failed!'
            env.FAILED_STAGE=env.STAGE_NAME
            throw err
        }
    }
}

