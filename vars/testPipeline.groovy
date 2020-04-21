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

                        def stepsToExecute = config.customEnabled == true ? config.customSteps : buildSteps.defaultInstallSteps
                        buildSteps.install(stepsToExecute)
                        /*if (config.customEnabled == true) {
                            *//*def customSteps = { script ->
                                script.echo "This is custom step"
                                script.echo "Another custom step"
                                script.sh "ls -ltr"
                            }*//*
                            buildSteps.install(config.customSteps)
                        } else {
                            buildSteps.install()
                        }*/

                    }
                }
            }
        }
    }
}