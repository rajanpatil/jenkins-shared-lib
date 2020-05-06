import com.shared.lib.ConfigManager

def call(Map configuration) {

    ConfigManager config = new ConfigManager(configuration)
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
