def call(){
    script {
        ansiColor('xterm') {
            echo ""
        }
        try {
            sh '''
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "source odf_vars.sh; echo "Upgrading ODF to ${UPGRADE_OCS_VERSION}"; cd /root/ocs-upi-kvm/scripts; ./upgrade-ocs-ci.sh |tee /root/upgrade-ocs-ci.log"
               scp -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP}:/root/upgrade-ocs-ci.log ${WORKSPACE}/
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "odf_csv=`oc get csv -n openshift-storage |grep odf-operator | awk {'print $1'}`; oc get csv $odf_csv -n openshift-storage -o yaml |grep full_version |awk {'print $2'} | tail -n 1 > odf-full-build.txt"
               scp -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP}:/root/odf-full-build.txt ${WORKSPACE}/
            '''
            env.ODF_FULL_BUILD = readFile "odf-full-build.txt"
            
        }
        catch (err) {
            echo 'Error ! ODF Upgrade using upgrade-ocs-ci.sh failed!'
            env.FAILED_STAGE=env.STAGE_NAME
            throw err
        }
    }
}

