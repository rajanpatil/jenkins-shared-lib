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
                        def installSteps = (config != null && config.installSteps != null) ?
                                config.installSteps :
                                buildSteps.defaultInstallSteps
                        buildSteps.install(installSteps)
                    }
                }
            }

            stage('Check Code Style and Formatting') {
                steps {
                    script {
                        def codeStyleSteps = (config != null && config.codeStyleSteps != null) ?
                                config.codeStyleSteps :
                                buildSteps.defaultCheckCodeStyleSteps
                        buildSteps.checkCodeStyle(codeStyleSteps)
                    }
                }
            }

            stage('Run Tests') {
                steps {
                    script {
                        def testsSteps = (config != null && config.testsSteps != null) ?
                                config.testsSteps :
                                buildSteps.defaultRunTestsSteps
                        buildSteps.runTests(testsSteps)
                    }
                }
            }

            stage('Build') {
                steps {
                    script {
                        def buildStageSteps = (config != null && config.buildSteps != null) ?
                                config.buildSteps :
                                buildSteps.defaultBuildSteps
                        buildSteps.build(buildStageSteps)
                    }
                }
            }
        }
    }

}
