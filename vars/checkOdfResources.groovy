def call(){
    script {
        ansiColor('xterm') {
            echo ""
        }
        try {
            sh '''
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get nodes" > /root/odf-resources.log; oc get nodes >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get clusterversion" >> /root/odf-resources.log; oc get clusterversion >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get csv -A" >> /root/odf-resources.log; oc get csv -A >> /root/odf-resources.log"
            //    ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "source odf_vars.sh; echo "# oc get csv odf-operator.v${ODF_VERSION}.0 -n openshift-storage -o yaml |grep full_version" >> /root/odf-resources.log; oc get csv odf-operator.v${ODF_VERSION}.0 -n openshift-storage -o yaml |grep full_version >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get pods -n openshift-local-storage" >> /root/odf-resources.log; oc get pods -n openshift-local-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get localvolumeset -n openshift-local-storage" >> /root/odf-resources.log; oc get localvolumeset -n openshift-local-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get pv" >> /root/odf-resources.log; oc get pv >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get pods -n openshift-storage" >> /root/odf-resources.log; oc get pods -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get pvc -n openshift-storage" >> /root/odf-resources.log; oc get pvc -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get sc -n openshift-storage" >> /root/odf-resources.log; oc get sc -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get storagecluster -n openshift-storage" >> /root/odf-resources.log; oc get storagecluster -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get cephcluster -n openshift-storage" >> /root/odf-resources.log; oc get cephcluster -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get storagesystem -n openshift-storage" >> /root/odf-resources.log; oc get storagesystem -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get storagecluster -n openshift-storage -o yaml" >> /root/odf-resources.log; oc get storagecluster -n openshift-storage -o yaml >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get backingstore -n openshift-storage" >> /root/odf-resources.log; oc get backingstore -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get bucketclass -n openshift-storage" >> /root/odf-resources.log; oc get bucketclass -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get bucketclass -n openshift-storage" >> /root/odf-resources.log; oc get bucketclass -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get noobaa -n openshift-storage" >> /root/odf-resources.log; oc get noobaa -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "# oc get noobaa -n openshift-storage -o yaml" >> /root/odf-resources.log; oc get noobaa -n openshift-storage -o yaml >> /root/odf-resources.log"
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

