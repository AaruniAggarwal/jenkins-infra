def call(){
    script {
        ansiColor('xterm') {
            echo ""
        }
        try {
            sh '''
                  FILE=test-ocs-ci-tier-${TIER_TEST_SUITE}.log
                  sed -n '/short test summary info/, /Test result:/p' test-ocs-ci-tier-${TIER_TEST_SUITE}.log > tier${TIER_TEST_SUITE}-summary.txt
                  sed -n '/short test summary info/, /Test result:/p' test-ocs-ci-tier-${TIER_TEST_SUITE}.log | awk '/passed/||/failed/||/skipped/' > slacksummary.txt
                  
            '''
        }
        catch (err) {
            echo 'Error ! Capturing tier test summary failed!'
            env.FAILED_STAGE=env.STAGE_NAME
            throw err
        }
    }
}