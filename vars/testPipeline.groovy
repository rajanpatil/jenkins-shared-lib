import com.shared.lib.EchoTest
import com.shared.lib.PythonServiceBuildSteps

def call(Map config) {

    def echoTest = new EchoTest(this)
    def buildSteps = new PythonServiceBuildSteps(this)

    pipeline {
        agent any

        stages {
            stage('test'){
                steps {
                    script {
                        echoTest.echoMessage "Testing pipeline..."
                    }
                }
            }

            stage('custom steps'){
                steps {
                    script {

                        def stepsToExecute = (config !=null && config.customSteps !=null)?
                                config.customSteps :
                                buildSteps.defaultInstallSteps
                        buildSteps.install(stepsToExecute)
                    }
                }
            }
        }
    }
}