import com.shared.lib.ConfigManager
import com.shared.lib.PythonServiceBuildSteps

def call(Map config) {

    def buildSteps = new PythonServiceBuildSteps(this)
    def configManager = new ConfigManager(config)

    pipeline {

        agent any

        options {
            ansiColor('xterm')
        }

        stages {

            stage('Install Dependencies') {
                steps {
                    script {
                        Map stepsToExecute = configManager.getConfig('install')
                        buildSteps.install(stepsToExecute.steps)
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