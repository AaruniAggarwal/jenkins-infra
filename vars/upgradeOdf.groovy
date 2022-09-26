def call(){
    script {
        ansiColor('xterm') {
            echo ""
        }
        try {
            sh '''
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "source odf_vars.sh; echo "Upgrading ODF to ${UPGRADE_OCS_VERSION}"; cd /root/ocs-upi-kvm/scripts; ./upgrade-ocs-ci.sh |tee /root/upgrade-ocs-ci.log"
               scp -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP}:/root/upgrade-ocs-ci.log ${WORKSPACE}/
            '''
        }
        catch (err) {
            echo 'Error ! ODF Upgrade using upgrade-ocs-ci.sh failed!'
            env.FAILED_STAGE=env.STAGE_NAME
            throw err
        }
    }
}

