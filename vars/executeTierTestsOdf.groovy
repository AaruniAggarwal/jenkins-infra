def call(){
    script {
        ansiColor('xterm') {
            echo ""
        }
        try {
            sh '''
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "pip3 install yq"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} 'source odf_vars.sh; export OCS_VERSION=`echo ${UPGRADE_OCS_VERSION} | cut -d "." -f 1-2`; export OCS_CSV_CHANNEL=stable-$OCS_VERSION; yq -y -i ".DEPLOYMENT.ocs_csv_channel |= env.OCS_CSV_CHANNEL" /root/ocs-ci-conf.yaml; yq -y -i ".ENV_DATA.ocs_version |= env.OCS_VERSION" /root/ocs-ci-conf.yaml'
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "cat /root/ocs-ci-conf.yaml"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "source odf_vars.sh; export OCS_VERSION=`echo ${UPGRADE_OCS_VERSION} | cut -d "." -f 1-2`; echo "Testing on ODF Version $OCS_VERSION"; cd /root/ocs-upi-kvm/scripts; ./test-ocs-ci.sh --tier ${TIER_TEST_SUITE} |tee /root/test-ocs-ci-tier-${TIER_TEST_SUITE}.log"
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

