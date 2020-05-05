import com.shared.lib.ConfigurationManager

def call(Map configuration) {

    ConfigurationManager config = new ConfigurationManager(configuration)
    String agentName = config.getConfiguration('agent')
    pipeline {
        agent {
            label "${agentName}"
        }

        stages {
            stage('test-config'){
                steps {
                    script {
                        echo agentName
                        def installStage = config.getStageConfiguration('installStage')
                        echo installStage.get('agent')
                        echo config.getConfiguration('serviceName')
                    }
                }
            }
        }
    }
}
