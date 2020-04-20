import com.shared.lib.EchoTest

def call() {

    def echoTest = new EchoTest(this)

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
        }
    }

}