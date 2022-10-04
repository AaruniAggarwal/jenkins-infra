def call(){
    script {
        ansiColor('xterm') {
            echo ""
        }
        try {
            if ( env.POWERVS == "true" ) {
            sh '''
               echo "export PLATFORM=${PLATFORM}" > odf_vars.sh
               echo "export OCP_VERSION=${OCP_RELEASE}" >> odf_vars.sh
               echo "export OCS_VERSION=${ODF_VERSION}" >> odf_vars.sh
               echo "export PVS_API_KEY=${IBMCLOUD_API_KEY}" >> odf_vars.sh
               echo "export PVS_SERVICE_INSTANCE_ID=${SERVICE_INSTANCE_ID}" >> odf_vars.sh
               echo "export RHID_USERNAME=${REDHAT_USERNAME}" >> odf_vars.sh
               echo "export RHID_PASSWORD=${REDHAT_PASSWORD}" >> odf_vars.sh
               echo "export OCS_REGISTRY_IMAGE=quay.io/rhceph-dev/ocs-registry:latest-stable-${ODF_VERSION}" >> odf_vars.sh
               echo "export VAULT_SUPPORT=${ENABLE_VAULT}" >> odf_vars.sh
               echo "export FIPS_ENABLEMENT=${ENABLE_FIPS}" >> odf_vars.sh
               scp -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa odf_vars.sh root@${BASTION_IP}:/root/
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "chmod +x odf_vars.sh; source odf_vars.sh"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo Deploying ODF version - ${ODF_VERSION}"
               
            '''
            }
            else {
              sh '''
               echo "export PLATFORM=${PLATFORM}" > odf_vars.sh
               echo "export PVC_URL=${OS_AUTH_URL}" >> odf_vars.sh
               echo "export PVC_LOGIN_NAME=${PVC_USERNAME}" >> odf_vars.sh
               echo "export PVC_LOGIN_PASSWORD=${PVC_PASSWORD}" >> odf_vars.sh
               echo "export OCP_VERSION=${OCP_RELEASE}" >> odf_vars.sh
               echo "export OCS_VERSION=${ODF_VERSION}" >> odf_vars.sh
               echo "export RHID_USERNAME=${REDHAT_USERNAME}" >> odf_vars.sh
               echo "export RHID_PASSWORD=${REDHAT_PASSWORD}" >> odf_vars.sh
               echo "export OCS_REGISTRY_IMAGE=${OCS_REGISTRY_IMAGE}" >> odf_vars.sh
               echo "export VAULT_SUPPORT=${ENABLE_VAULT}" >> odf_vars.sh
               echo "export FIPS_ENABLEMENT=${ENABLE_FIPS}" >> odf_vars.sh
               scp -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa odf_vars.sh root@${BASTION_IP}:/root/
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "chmod +x odf_vars.sh; source odf_vars.sh"
               ssh -o 'StrictHostKeyChecking no' -i ${WORKSPACE}/deploy/id_rsa root@${BASTION_IP} "echo Deploying ODF version - ${ODF_VERSION}"
               
            '''  
            }
        }
        catch (err) {
            echo 'Error ! Exporting of environment variables failed!'
            env.FAILED_STAGE=env.STAGE_NAME
            throw err
        }
    }
}


