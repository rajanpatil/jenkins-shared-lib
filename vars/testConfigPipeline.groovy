import com.shared.lib.ConfigurationManager

def call(Map configuration) {

    ConfigurationManager config = new ConfigurationManager(configuration)

    pipeline {
        agent config.getConfiguration('agent')

        stages {
            stage('test-config'){
                steps {
                    script {
                        def agentName = config.getConfiguration('agent')
                        echo agentName
                        def installStage = config.getStageConfiguration('install')
                        echo installStage.get('agent')
                        echo config.getConfiguration('serviceName')
                    }
                }
            }
        }
    }
}
