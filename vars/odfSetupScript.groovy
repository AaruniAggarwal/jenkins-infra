def call(){
    script {
        ansiColor('xterm') {
            echo ""
        }
        try {
            sh '''
               scp -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa ${WORKSPACE}/deploy/data/pull-secret.txt root@${BASTION_IP}:/root/
               scp -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa ${WORKSPACE}/deploy/data/auth.yaml root@${BASTION_IP}:/root/
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "git clone https://github.com/ocp-power-automation/ocs-upi-kvm.git"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "cd /root/ocs-upi-kvm; git submodule update --init"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "cd /root/ocs-upi-kvm/scripts; ./setup-ocs-ci.sh |tee /root/setup-ocs-ci.log"
               scp -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP}:/root/setup-ocs-ci.log ${WORKSPACE}/
            '''
        }
        catch (err) {
            echo 'Error ! Setup ocs-ci failed!'
            env.FAILED_STAGE=env.STAGE_NAME
            throw err
        }
    }
}

