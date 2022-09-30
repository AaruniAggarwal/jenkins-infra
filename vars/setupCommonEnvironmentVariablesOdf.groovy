def call() {
    script {
        //Failed stage
        env.FAILED_STAGE=""
        //VMs setup
        if ( env.POWERVS == "true" ) {
            env.INSTANCE_NAME = "rdr-aar"
            env.NETWORK_NAME = "ocp-net"
            env.RHEL_USERNAME = "root"
            env.RHEL_SMT = "4"
            env.CLUSTER_DOMAIN = "redhat.com"
            env.SYSTEM_TYPE = "e980"
            env.ENABLE_LOCAL_REGISTRY = "false"
            env.LOCAL_REGISTRY_IMAGE = "docker.io/ibmcom/registry-ppc64le:2.6.2.5"
            env.SETUP_SQUID_PROXY = "false"
            //Needed for target service
            env.CRN = "crn:v1:bluemix:public:power-iaas:lon06:a/65b64c1f1c29460e8c2e4bbfbd893c2c:fac4755e-8aff-45f5-8d5c-1d3b58b7a229::"

            // Bellow 4 variables are not used. Disabled in template
            env.HELPERNODE_REPO = "https://github.com/RedHatOfficial/ocp4-helpernode"
            env.HELPERNODE_TAG = "324e09e3d303101874f540730c993cd986ddbc04"
            env.INSTALL_PLAYBOOK_REPO = "https://github.com/ocp-power-automation/ocp4-playbooks"
            switch (env.OCP_RELEASE) {
                case "4.11":
                    env.INSTALL_PLAYBOOK_TAG = "284b597b3e88c635e3069b82926aa16812238492"
                    break
                case "4.10":
                    env.INSTALL_PLAYBOOK_TAG = "284b597b3e88c635e3069b82926aa16812238492"
                    break
                case "4.9":
                    env.INSTALL_PLAYBOOK_TAG = "284b597b3e88c635e3069b82926aa16812238492"
                    break
                case "4.8":
                    env.INSTALL_PLAYBOOK_TAG = "284b597b3e88c635e3069b82926aa16812238492"
                    break
                case "4.7":
                    env.INSTALL_PLAYBOOK_TAG = "de8b4bf5f243f40dae91a3a0cc67a55c571d210e"
                    break
                case "4.6":
                    env.INSTALL_PLAYBOOK_TAG = "2888fad354e72af39af1be4f75efaea224187b6b"
                    break
                default:
                     env.INSTALL_PLAYBOOK_TAG = "284b597b3e88c635e3069b82926aa16812238492"
            }

            //Upgrade variables
            env.UPGRADE_IMAGE = ""
            env.UPGRADE_PAUSE_TIME = ""
            env.UPGRADE_DELAY_TIME = ""


            //Slack message
            env.MESSAGE=""

            env.DEPLOYMENT_STATUS = false
            env.BASTION_IP = ""

            //Pull Secret
            env.PULL_SECRET_FILE = "${WORKSPACE}/deploy/data/pull-secret.txt"
            env.ODF_AUTH_FILE = "${WORKSPACE}/deploy/data/auth.yaml"
            //Use RSA_KEY variable and getKeyOdf() when deploying ODF on pre-existing OCP Cluster. Store id_rsa inside Credentials folder in Jenkins. 
            env.RSA_KEY = "${WORKSPACE}/deploy/id_rsa"
            env.OPENSHIFT_INSTALL_TARBALL="https://mirror.openshift.com/pub/openshift-v4/ppc64le/clients/ocp-dev-preview/latest-${OCP_RELEASE}/openshift-install-linux.tar.gz"
            env.OPENSHIFT_CLIENT_TARBALL="https://mirror.openshift.com/pub/openshift-v4/ppc64le/clients/ocp-dev-preview/latest-${OCP_RELEASE}/openshift-client-linux.tar.gz"
            env.OPENSHIFT_CLIENT_TARBALL_AMD64="https://mirror.openshift.com/pub/openshift-v4/clients/ocp-dev-preview/latest-${OCP_RELEASE}/openshift-client-linux.tar.gz"
        }
        else {
            //PowerVC ENV Variables
            env.OS="linux"
            env.OS_IDENTITY_API_VERSION="3"
            env.OS_REGION_NAME="RegionOne"
            env.OS_PROJECT_DOMAIN_NAME="Default"
            env.OS_PROJECT_NAME="ibm-default"
            env.OS_TENANT_NAME="ibm-default"
            env.OS_USER_DOMAIN_NAME="Default"
            env.OS_COMPUTE_API_VERSION="2.46"
            env.OS_NETWORK_API_VERSION="2.0"
            env.OS_IMAGE_API_VERSION="2"
            env.OS_VOLUME_API_VERSION="3"
            env.OS_NETWORK="icp_network4"
            env.OS_PRIVATE_NETWORK="icp_network4"
            env.MASTER_TEMPLATE="${JOB_BASE_NAME}"+"-"+"${BUILD_NUMBER}"+"-"+"mas"
            env.WORKER_TEMPLATE="${JOB_BASE_NAME}"+"-"+"${BUILD_NUMBER}"+"-"+"wor"
            env.BOOTSTRAP_TEMPLATE="${JOB_BASE_NAME}"+"-"+"${BUILD_NUMBER}"+"-"+"boo"
            env.BASTION_TEMPLATE="${JOB_BASE_NAME}"+"-"+"${BUILD_NUMBER}"+"-"+"bas"
            env.RHEL_USERNAME = "root"
            env.OS_INSECURE = true
            env.WORKER_VOLUME_COUNT="1"
            env.WORKER_VOLUME_SIZE="500"

            // Pull secrets
            env.PULL_SECRET_FILE = "${WORKSPACE}/deploy/data/pull-secret.txt"
            env.ODF_AUTH_FILE = "${WORKSPACE}/deploy/data/auth.yaml"

            env.OPENSHIFT_POWERVC_GIT_TF_DEPLOY_PROJECT="https://github.com/ocp-power-automation/ocp4-upi-powervm.git"
            //Cluster and vm details
            env.CLUSTER_DOMAIN="redhat.com"
            env.INSTANCE_NAME = "rdr-odf"
            env.MOUNT_ETCD_RAMDISK="false"
            env.CHRONY_CONFIG="true"
            env.SCG_ID = "c5045866-7a78-4b5b-8ae8-061340be64f8"
            env.VOLUME_STORAGE_TEMPLATE = "c340f1_v7k base template"
            env.CNI_NETWORK_PROVIDER = "OpenshiftSDN"

            //e2e variables
            if ( env.ENABLE_E2E_TEST ) {
                env.E2E_GIT="https://github.com/openshift/origin"
                env.E2E_BRANCH="release-${env.OCP_RELEASE}"
                env.E2E_EXCLUDE_LIST="https://raw.github.ibm.com/redstack-power/e2e-exclude-list/${env.OCP_RELEASE}-powervm/ocp${env.OCP_RELEASE}_power_exclude_list.txt"
                env.ENABLE_E2E_UPGRADE="false"
            }

            //Scale test variables
            if ( env.ENABLE_SCALE_TEST ) {
                env.SCALE_NUM_OF_DEPLOYMENTS = "60"
                env.SCALE_NUM_OF_NAMESPACES = "1000"
                env.EXPOSE_IMAGE_REGISTRY = "false"
            }

            //Proxy setup
            env.SETUP_SQUID_PROXY = "false"
            env.PROXY_ADDRESS = ""

            //Slack message
            env.MESSAGE=""

            env.DEPLOYMENT_STATUS = false
            env.BASTION_IP = ""
            //Common Service
            env.CS_INSTALL = "false"

            // Compute Template Variables
            env.WORKER_MEMORY_MB=""
            env.MASTER_MEMORY_MB=""
            env.BASTION_MEMORY_MB=""
            env.BOOTSTRAP_MEMORY_MB=''
            env.OPENSHIFT_INSTALL_TARBALL="https://mirror.openshift.com/pub/openshift-v4/ppc64le/clients/ocp-dev-preview/latest-${OCP_RELEASE}/openshift-install-linux.tar.gz"
            env.OPENSHIFT_CLIENT_TARBALL="https://mirror.openshift.com/pub/openshift-v4/ppc64le/clients/ocp-dev-preview/latest-${OCP_RELEASE}/openshift-client-linux.tar.gz"
            env.OPENSHIFT_CLIENT_TARBALL_AMD64="https://mirror.openshift.com/pub/openshift-v4/clients/ocp-dev-preview/latest-${OCP_RELEASE}/openshift-client-linux.tar.gz"
        }
    }
}
