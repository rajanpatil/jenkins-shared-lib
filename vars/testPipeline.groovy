import com.shared.lib.EchoTest
import com.shared.lib.PythonServiceBuildSteps

def call() {

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
                        def customSteps = { script ->
                            script.echo "This is custom step"
                            script.echo "Another custom step"
                            script.sh "ls -ltr"
                        }
                        buildSteps.install(customSteps)
                    }
                }
            }
        }
    }
}