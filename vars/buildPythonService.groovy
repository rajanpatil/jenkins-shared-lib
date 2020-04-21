import com.shared.lib.PythonServiceBuildSteps

def call(Map config) {

    def buildSteps = new PythonServiceBuildSteps(this)

    pipeline {

        agent any

        options {
            ansiColor('xterm')
        }

        stages {

            stage('Install Dependencies') {
                steps {
                    script {
                        buildSteps.install()
                    }
                }
            }

            stage('Check Code Style and Formatting') {
                steps {
                    script {
                        buildSteps.checkCodeStyle()
                    }
                }
            }

            stage('Run Tests') {
                steps {
                    script {
                        buildSteps.runTests()
                    }
                }
            }

            stage('Build') {
                steps {
                    script {
                        buildSteps.build()
                    }
                }
            }
        }
    }

}