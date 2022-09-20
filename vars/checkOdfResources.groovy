def call(){
    script {
        ansiColor('xterm') {
            echo ""
        }
        try {
            sh '''
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get nodes" |tee /root/odf-resources.log; oc get nodes |tee -a /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get clusterversion" |tee -a /root/odf-resources.log; oc get clusterversion |tee -a /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get csv -A" |tee -a /root/odf-resources.log; oc get csv -A |tee -a /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "source odf_vars.sh; echo "# oc get csv odf-operator.v$OCS_VERSION.0 -n openshift-storage -o yaml |grep full_version" |tee -a /root/odf-resources.log; oc get csv odf-operator.v$OCS_VERSION.0 -n openshift-storage -o yaml |grep full_version |tee -a /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get pods -n openshift-local-storage" |tee -a /root/odf-resources.log; oc get pods -n openshift-local-storage |tee -a /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get localvolumeset -n openshift-local-storage" |tee -a /root/odf-resources.log; oc get localvolumeset -n openshift-local-storage |tee -a /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get pv" |tee -a /root/odf-resources.log; oc get pv |tee -a /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get pods -n openshift-storage" |tee -a /root/odf-resources.log; oc get pods -n openshift-storage |tee -a /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get pvc -n openshift-storage" |tee -a /root/odf-resources.log; oc get pvc -n openshift-storage |tee -a /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get sc -n openshift-storage" |tee -a /root/odf-resources.log; oc get sc -n openshift-storage |tee -a /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get storagecluster -n openshift-storage" |tee -a /root/odf-resources.log; oc get storagecluster -n openshift-storage |tee -a /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get cephcluster -n openshift-storage" |tee -a /root/odf-resources.log; oc get cephcluster -n openshift-storage |tee -a /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get storagesystem -n openshift-storage" |tee -a /root/odf-resources.log; oc get storagesystem -n openshift-storage |tee -a /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get storagecluster -n openshift-storage -o yaml" |tee -a /root/odf-resources.log; oc get storagecluster -n openshift-storage -o yaml |tee -a /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get backingstore -n openshift-storage" |tee -a /root/odf-resources.log; oc get backingstore -n openshift-storage |tee -a /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get bucketclass -n openshift-storage" |tee -a /root/odf-resources.log; oc get bucketclass -n openshift-storage |tee -a /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get bucketclass -n openshift-storage" |tee -a /root/odf-resources.log; oc get bucketclass -n openshift-storage |tee -a /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get noobaa -n openshift-storage" |tee -a /root/odf-resources.log; oc get noobaa -n openshift-storage |tee -a /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get noobaa -n openshift-storage -o yaml" |tee -a /root/odf-resources.log; oc get noobaa -n openshift-storage -o yaml |tee -a /root/odf-resources.log"
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

