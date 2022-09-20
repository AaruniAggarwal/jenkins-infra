def call(){
    script {
        ansiColor('xterm') {
            echo ""
        }
        try {
            sh '''
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(echo -e "# oc get nodes" |tee /root/odf-resources.log; oc get nodes |tee -a /root/odf-resources.log)"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(echo -e "# oc get clusterversion \n" |tee -a /root/odf-resources.log; oc get clusterversion |tee -a /root/odf-resources.log)"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(echo -e "# oc get csv -A \n" |tee -a /root/odf-resources.log; oc get csv -A |tee -a /root/odf-resources.log)"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(source odf_vars.sh; echo -e "# oc get csv odf-operator.v$OCS_VERSION.0 -n openshift-storage -o yaml |grep full_version \n" |tee -a /root/odf-resources.log; oc get csv odf-operator.v$OCS_VERSION.0 -n openshift-storage -o yaml |grep full_version |tee -a /root/odf-resources.log)"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(echo -e "# oc get pods -n openshift-local-storage \n" |tee -a /root/odf-resources.log; oc get pods -n openshift-local-storage |tee -a /root/odf-resources.log)"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(echo -e "# oc get localvolumeset -n openshift-local-storage \n" |tee -a /root/odf-resources.log; oc get localvolumeset -n openshift-local-storage |tee -a /root/odf-resources.log)"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(echo -e "# oc get pv \n" |tee -a /root/odf-resources.log; oc get pv |tee -a /root/odf-resources.log)"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(echo -e "# oc get pods -n openshift-storage \n" |tee -a /root/odf-resources.log; oc get pods -n openshift-storage |tee -a /root/odf-resources.log)"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(echo -e "# oc get pvc -n openshift-storage \n" |tee -a /root/odf-resources.log; oc get pvc -n openshift-storage |tee -a /root/odf-resources.log)"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(echo -e "# oc get sc -n openshift-storage \n" |tee -a /root/odf-resources.log; oc get sc -n openshift-storage |tee -a /root/odf-resources.log)"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(echo -e "# oc get storagecluster -n openshift-storage \n" |tee -a /root/odf-resources.log; oc get storagecluster -n openshift-storage |tee -a /root/odf-resources.log)"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(echo -e "# oc get cephcluster -n openshift-storage \n" |tee -a /root/odf-resources.log; oc get cephcluster -n openshift-storage |tee -a /root/odf-resources.log)"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(echo -e "# oc get storagesystem -n openshift-storage \n" |tee -a /root/odf-resources.log; oc get storagesystem -n openshift-storage |tee -a /root/odf-resources.log)"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(echo -e "# oc get storagecluster -n openshift-storage -o yaml \n" |tee -a /root/odf-resources.log; oc get storagecluster -n openshift-storage -o yaml |tee -a /root/odf-resources.log)"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(echo -e "# oc get backingstore -n openshift-storage \n" |tee -a /root/odf-resources.log; oc get backingstore -n openshift-storage |tee -a /root/odf-resources.log)"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(echo -e "# oc get bucketclass -n openshift-storage \n" |tee -a /root/odf-resources.log; oc get bucketclass -n openshift-storage |tee -a /root/odf-resources.log)"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(echo -e "# oc get bucketclass -n openshift-storage \n" |tee -a /root/odf-resources.log; oc get bucketclass -n openshift-storage |tee -a /root/odf-resources.log)"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(echo -e "# oc get noobaa -n openshift-storage \n" |tee -a /root/odf-resources.log; oc get noobaa -n openshift-storage |tee -a /root/odf-resources.log)"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "(echo -e "# oc get noobaa -n openshift-storage -o yaml \n" |tee -a /root/odf-resources.log; oc get noobaa -n openshift-storage -o yaml |tee -a /root/odf-resources.log)"
               scp -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP}:/root/odf-resources.log ${WORKSPACE}/
            '''
        }
        catch (err) {
            echo 'Error ! Gathering of ODF resources failed!'
            env.FAILED_STAGE=env.STAGE_NAME
            throw err
        }
    }
}

