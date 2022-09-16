def call(){
    script {
        ansiColor('xterm') {
            echo ""
        }
        try {
            sh '''
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "oc get nodes \n"; oc get nodes > /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "oc get clusterversion \n"; oc get clusterversion >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "oc get csv -A \n"; oc get csv -A >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "oc get pods -n openshift-local-storage \n"; oc get pods -n openshift-local-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "oc get localvolumeset -n openshift-local-storage \n"; oc get localvolumeset -n openshift-local-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "oc get pv \n"; oc get pv >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "oc get pods -n openshift-storage \n"; oc get pods -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "oc get pvc -n openshift-storage \n"; oc get pvc -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "oc get sc -n openshift-storage \n"; oc get sc -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "oc get storagecluster -n openshift-storage \n"; oc get storagecluster -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "oc get cephcluster -n openshift-storage \n"; oc get cephcluster -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "oc get storagesystem -n openshift-storage \n"; oc get storagesystem -n openshift-storage >> /root/odf-resources.log"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo "oc get storagecluster -n openshift-storage -o yaml \n"; oc get storagecluster -n openshift-storage -o yaml >> /root/odf-resources.log"
               
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

