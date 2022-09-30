def call(String config, String noOfWorkers="3"){
    script{
        //Setup PowerVS cluster config
        //Min config
        if (config == "min") {
            env.BASTION_MEMORY = "16"
            env.BASTION_PROCESSORS = "1"
            env.BASTION_VCPUS = "2"
            
            env.BOOTSTRAP_MEMORY = "16"
            env.BOOTSTRAP_PROCESSORS = ".5"
            env.BOOTSTRAP_VCPUS = "2"
            
            env.NUM_OF_MASTERS = "3"
            env.MASTER_PROCESSORS = "1.25"    // processors are equivalent to cores in powerVS
            env.MASTER_VCPUS = "2" 
            env.MASTER_MEMORY = "32" 

            env.NUM_OF_WORKERS = noOfWorkers
            env.WORKER_PROCESSORS = "1.5"
            env.WORKER_VCPUS = "3" 
            env.WORKER_MEMORY = "64"

            
        }
        //Max Config
        else{
            env.BASTION_MEMORY = "16"
            env.BASTION_PROCESSORS = "1"
            env.BASTION_VCPUS = "2"

            env.BOOTSTRAP_MEMORY = "16"
            env.BOOTSTRAP_PROCESSORS = ".5"
            env.BOOTSTRAP_VCPUS = "2"

            env.NUM_OF_MASTERS = "3"
            env.MASTER_PROCESSORS = "1.25"
            env.MASTER_VCPUS = "3"
            env.MASTER_MEMORY = "64"

            env.NUM_OF_WORKERS = "4"
            env.WORKER_PROCESSORS = "1.5"
            env.WORKER_VCPUS = "4" 
            env.WORKER_MEMORY = "128"
        }
    }
}
