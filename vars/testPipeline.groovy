import com.shared.lib.EchoTest

def echoTest = new EchoTest(this)

def call() {

    pipeline {
        agent any

        stages {
            stage('test'){
                steps {
                    echoTest.echoMessage "Testing pipeline..."
                }
            }
        }
    }

}