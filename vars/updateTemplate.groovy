def call(String authurl, String pvczone, String distroimage, String mast, String work, String manger, String vul, String prox, String gluster, String disto, String numofworkers)
{
    // Updating tempate file with env variable
    sh " cd ${WORKSPACE}/canary-deployments/templates && [ ! -z authurl ] && grep -q '^auth_url *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^auth_url *=.*\$|auth_url = \\\"\"'${authurl}'\"\\\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\nauth_url = \\\"\"'${authurl}'\"\\\"|g\" \"${TERMPLATE_FILE}\""
    sh " cd ${WORKSPACE}/canary-deployments/templates && [ ! -z pvczone ] && grep -q '^availability_zone *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^availability_zone *=.*\$|availability_zone = \\\"\"'${pvczone}'\"\\\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\navailability_zone = \\\"\"'${pvczone}'\"\\\"|g\" \"${TERMPLATE_FILE}\""
    sh " cd ${WORKSPACE}/canary-deployments/templates && [ ! -z distroimage ] && grep -q '^os_image_name *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^os_image_name *=.*\$|os_image_name = \\\"\"${distroimage}\"\\\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\nos_image_name = \\\"\"${distroimage}\"\\\"|g\" \"${TERMPLATE_FILE}\""
    sh " cd ${WORKSPACE}/canary-deployments/templates && grep -q '^master =' \"${TERMPLATE_FILE}\" && sed -i \"s|^master *=.*\$|master = \"'${mast}'\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\nmaster = \"'${mast}'\"|g\" \"${TERMPLATE_FILE}\""
    sh " cd ${WORKSPACE}/canary-deployments/templates && [ ! -z distro ] && grep -q '^image_distro *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^image_distro *=.*\$|image_distro = \\\"\"${distro}\"\\\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\nimage_distro = \\\"\"${distro}\"\\\"|g\" \"${TERMPLATE_FILE}\""
    //sh " cd ${WORKSPACE}/canary-deployments/templates &&  grep -q 'icp_configuration' \"${TERMPLATE_FILE}\" && sed -i  -e '/etcd_extra_args/r ${WORKSPACE}/config.tf' \"${TERMPLATE_FILE}\""
    sh " cd ${WORKSPACE}/canary-deployments/templates &&  grep -q '^icp_config_file *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^icp_config_file *=.*\$|icp_config_file = \\\"${WORKSPACE}/installer/cluster/power.config.yaml\\\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\nicp_config_file = \\\"${WORKSPACE}/installer/cluster/power.config.yaml\\\"|g\" \"${TERMPLATE_FILE}\""
    if (env.DEPLOY_WORKER == "true")
    {
        num=numofworkers.toInteger()
        WORKER1=sh (returnStdout: true, script: "echo ${work}|sed \"s|nodes *=.*,|nodes = \\\"${num}\\\",|g\" |tr '\n' ' '| sed \"s|\\s*\$||g\"")
        sh " cd ${WORKSPACE}/canary-deployments/templates && grep -q '^worker *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^worker *=.*\$|worker = \"'${WORKER1}'\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\nworker = \"'${WORKER1}'\"|g\" \"${TERMPLATE_FILE}\""
        //sh " cd ${WORKSPACE}/canary-deployments/templates && grep -q '^worker *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^worker =.*\$|worker = \"'${work}'\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\nworker = \"'${work}'\"|g\" \"${TERMPLATE_FILE}\""
    }
    else if (env.DEPLOY_WORKER == "false")
    {
        WORKER1=sh (returnStdout: true, script: "echo ${work}|sed \"s|nodes *=.*,|nodes = \\\"0\\\",|g\" |tr '\n' ' '| sed \"s|\\s*\$||g\"")
        sh " cd ${WORKSPACE}/canary-deployments/templates && grep -q '^worker *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^worker *=.*\$|worker = \"'${WORKER1}'\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\nworker = \"'${WORKER1}'\"|g\" \"${TERMPLATE_FILE}\""
    }
    if (env.DEPLOY_MANAGER == "true")
    {
        sh " cd ${WORKSPACE}/canary-deployments/templates && grep -q '^manager *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^manager *=.*\$|manager = \"'${manger}'\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\nmanager = \"'${manger}'\"|g\" \"${TERMPLATE_FILE}\""
    }
    else if (env.DEPLOY_MANAGER == "false")
    {
        MANAGER1=sh (returnStdout: true, script: "echo ${manger}|sed \"s|nodes *=.*,|nodes = \\\"0\\\",|g\" |tr '\n' ' '| sed \"s|\\s*\$||g\"")
        sh " cd ${WORKSPACE}/canary-deployments/templates && grep -q '^manager *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^manager *=.*\$|manager = \"'${MANAGER1}'\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\nmanager = \"'${MANAGER1}'\"|g\" \"${TERMPLATE_FILE}\""
    }
    if (env.DEPLOY_VA == "true")
    {
        sh " cd ${WORKSPACE}/canary-deployments/templates && grep -q '^va *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^va *=.*\$|va = \"'${vul}'\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\nva = \"'${vul}'\"|g\" \"${TERMPLATE_FILE}\""
        sh " cd ${WORKSPACE}/canary-deployments/templates && grep -q 'vulnerability-advisor *=' \"${TERMPLATE_FILE}\" && sed -i \"s|.*vulnerability-advisor.*=.*\$|\\\"vulnerability-advisor\\\" = \\\"enabled\\\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"|\\\"vulnerability-advisor\\\" = \\\"enabled\\\" |a  \"'management_services = {'\"|\" \"${TERMPLATE_FILE}\""
    }
    else if (env.DEPLOY_VA == "false")
    {
        VA1=sh (returnStdout: true, script: "echo ${vul}|sed \"s|nodes *=.*,|nodes = \\\"0\\\",|g\" |tr '\n' ' '| sed \"s|\\s*\$||g\"")
        sh " cd ${WORKSPACE}/canary-deployments/templates && grep -q '^va *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^va *=.*\$|va = \"'${VA1}'\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\nva = \"'${VA1}'\"|g\" \"${TERMPLATE_FILE}\""
        sh " cd ${WORKSPACE}/canary-deployments/templates && grep -q 'vulnerability-advisor *=' \"${TERMPLATE_FILE}\" && sed -i \"s|.*vulnerability-advisor.*=.*\$|\\\"vulnerability-advisor\\\" = \\\"disabled\\\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"|\\\"vulnerability-advisor\\\" = \\\"disabled\\\" |a  \"'management_services = {'\"|\" \"${TERMPLATE_FILE}\""
        
    }

    if (env.ENABLE_GLUSTER == "true")
    {
        sh " cd ${WORKSPACE}/canary-deployments/templates && grep -q '^gluster *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^gluster *=.*\$|gluster = \"'${gluster}'\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\ngluster = \"'${gluster}'\"|g\" \"${TERMPLATE_FILE}\""
        sh " cd ${WORKSPACE}/canary-deployments/templates && grep -q '^create_storage *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^create_storage *=.*\$|create_storage = \\\"true\\\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\ncreate_storage = \\\"true\\\"|g\" \"${TERMPLATE_FILE}\""
        sh " cd ${WORKSPACE}/canary-deployments/templates && grep -q 'storage-glusterfs.*=' \"${TERMPLATE_FILE}\" && sed -i \"s|.*storage-glusterfs.*=.*\$|\\\"storage-glusterfs\\\" = \\\"enabled\\\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"|\\\"storage-glusterfs = enabled\\\" |a  \"'management_services = {'\"|\" \"${TERMPLATE_FILE}\""
    }
    else if (env.ENABLE_GLUSTER == "false")
    {
        GL1=sh (returnStdout: true, script: "echo ${gluster}|sed \"s|nodes *=.*,|nodes = \\\"0\\\",|g\" |tr '\n' ' '| sed \"s|\\s*\$||g\"")
        sh " cd ${WORKSPACE}/canary-deployments/templates && grep -q '^gluster *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^gluster *=.*\$|gluster = \"'${GL1}'\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\ngluster = \"'${GL1}'\"|g\" \"${TERMPLATE_FILE}\""
        sh " cd ${WORKSPACE}/canary-deployments/templates && grep -q 'storage-glusterfs.*=' \"${TERMPLATE_FILE}\" && sed -i \"s|.*storage-glusterfs.*=.*\$|\\\"storage-glusterfs\\\" = \\\"disabled\\\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"\\\"storage-glusterfs\\\" = \\\"disabled\\\" |a  \"'management_services = {'\"|\" \"${TERMPLATE_FILE}\""
    }

    if (env.SYSTEM_TUNING == "false")
    {
        sh " cd ${WORKSPACE}/canary-deployments/templates &&  grep -q '^system_tuning *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^system_tuning *=.*\$|system_tuning = \\\"\"disabled\"\\\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\nsystem_tuning = \\\"\"disabled\"\\\"|g\" \"${TERMPLATE_FILE}\""
    }
    else if (env.SYSTEM_TUNING == "true")
    {
        sh " cd ${WORKSPACE}/canary-deployments/templates &&  grep -q '^system_tuning *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^system_tuning *=.*\$|system_tuning = \\\"\"enabled\"\\\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\nsystem_tuning = \\\"\"enabled\"\\\"|g\" \"${TERMPLATE_FILE}\""
    }
    if (env.DEPLOY_PROXY == "true")
    {
        sh " cd ${WORKSPACE}/canary-deployments/templates && grep -q '^proxy *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^proxy *=.*\$|proxy = \"'${prox}'\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\nproxy = \"'${prox}'\"|g\" \"${TERMPLATE_FILE}\""
    }
    else if (env.DEPLOY_PROXY == "false")
    {
        PROXY1=sh (returnStdout: true, script: "echo ${prox}|sed \"s|nodes *=.*,|nodes = \\\"0\\\",|g\" |tr '\n' ' '| sed \"s|\\s*\$||g\"")
        sh " cd ${WORKSPACE}/canary-deployments/templates && grep -q '^proxy *=' \"${TERMPLATE_FILE}\" && sed -i \"s|^proxy *=.*\$|proxy = \"'${PROXY1}'\"|g\" \"${TERMPLATE_FILE}\" || sed -i \"4s|\$|\\nproxy = \"'${PROXY1}'\"|g\" \"${TERMPLATE_FILE}\""
    }
}
