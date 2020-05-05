import com.shared.lib.ConfigurationManager

def call(Map configuration) {

    ConfigurationManager config = new ConfigurationManager(configuration)

    pipeline {
        agent any

        stages {
            stage('test-config'){
                steps {
                    script {
                        def value1 = config.getConfiguration('agent')
                        echo value1
                        def value2 = config.getStageConfiguration('install')
                        echo value2.get('agent')
                    }
                }
            }
        }
    }
}
