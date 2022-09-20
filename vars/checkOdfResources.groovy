def call(){
    script {
        ansiColor('xterm') {
            echo ""
        }
        try {
            sh '''
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo -e "$ oc get nodes \n" > /root/odf-resources.log ; oc get nodes >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo -e "\n$ oc get clusterversion \n" >> /root/odf-resources.log ; oc get clusterversion >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo -e "\n$ oc get csv -A \n" >> /root/odf-resources.log ; oc get csv -A >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "source odf_vars.sh; echo -e "\n$ oc get csv odf-operator.v$OCS_VERSION.0 -n openshift-storage -o yaml |grep full_version \n" >> /root/odf-resources.log ; oc get csv odf-operator.v$OCS_VERSION.0 -n openshift-storage -o yaml |grep full_version >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo -e "\n$ oc get pods -n openshift-local-storage \n" >> /root/odf-resources.log ; oc get pods -n openshift-local-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo -e "\n$ oc get localvolumeset -n openshift-local-storage \n" >> /root/odf-resources.log ; oc get localvolumeset -n openshift-local-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo -e "\n$ oc get pv \n" >> /root/odf-resources.log ; oc get pv >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo -e "\n$ oc get pods -n openshift-storage \n" >> /root/odf-resources.log ; oc get pods -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo -e "\n$ oc get pvc -n openshift-storage \n" >> /root/odf-resources.log ; oc get pvc -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo -e "\n$ oc get sc -n openshift-storage \n" >> /root/odf-resources.log ; oc get sc -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo -e "\n$ oc get storagecluster -n openshift-storage \n" >> /root/odf-resources.log ; oc get storagecluster -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo -e "\n$ oc get cephcluster -n openshift-storage \n" >> /root/odf-resources.log ; oc get cephcluster -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo -e "\n$ oc get storagesystem -n openshift-storage \n" >> /root/odf-resources.log ; oc get storagesystem -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo -e "\n$ oc get storagecluster -n openshift-storage -o yaml \n" >> /root/odf-resources.log ; oc get storagecluster -n openshift-storage -o yaml >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo -e "\n$ oc get backingstore -n openshift-storage \n" >> /root/odf-resources.log ; oc get backingstore -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo -e "\n$ oc get bucketclass -n openshift-storage \n" >> /root/odf-resources.log ; oc get bucketclass -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo -e "\n$ oc get bucketclass -n openshift-storage \n" >> /root/odf-resources.log ; oc get bucketclass -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo -e "\n$ oc get noobaa -n openshift-storage \n" >> /root/odf-resources.log ; oc get noobaa -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo -e "\n$ oc get noobaa -n openshift-storage -o yaml \n" >> /root/odf-resources.log ; oc get noobaa -n openshift-storage -o yaml >> /root/odf-resources.log"
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

