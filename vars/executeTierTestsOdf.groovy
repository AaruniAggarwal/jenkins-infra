def call(){
    script {
        ansiColor('xterm') {
            echo ""
        }
        try {
            sh '''
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "source odf_vars.sh; echo "Testing on ODF Version ${OCS_VERSION}"; cd /root/ocs-upi-kvm/scripts; ./test-ocs-ci.sh --tier ${TIER_TEST_SUITE} |tee /root/test-ocs-ci-tier-${TIER_TEST_SUITE}.log"
               scp -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP}:/root/test-ocs-ci-tier-${TIER_TEST_SUITE}.log ${WORKSPACE}/
            '''
        }
        catch (err) {
            echo 'Error ! Tier-${TIER_TEST_SUITE} execution on ODF${ODF_VERSION} using test-ocs-ci.sh failed!'
            env.FAILED_STAGE=env.STAGE_NAME
            throw err
        }
    }
}

