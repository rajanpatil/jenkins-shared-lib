import com.shared.lib.PythonServiceBuildSteps


def call(Map config) {

    def buildSteps = new PythonServiceBuildSteps(this)
    def configuration = buildSteps.mergeConfig(config)

    pipeline {

        agent any

        options {
            ansiColor('xterm')
        }

        stages {

            stage('Install Dependencies') {
                steps {
                    script {
                        def installSteps = configuration.install.steps
                        buildSteps.install(installSteps)
                    }
                }
            }

            stage('Check Code Style and Formatting') {
                steps {
                    script {
                        Map stageConfig = configuration.get('codeStyle')
                        buildSteps.checkCodeStyle(stageConfig.steps)
                    }
                }
            }

            stage('Run Tests') {
                steps {
                    script {
                        Map stageConfig = configuration.get('tests')
                        buildSteps.runTests(stageConfig.steps)
                    }
                }
            }

            stage('Build') {
                steps {
                    script {
                        Map stageConfig = configuration.getConfig('build')
                        buildSteps.build(stageConfig.steps)
                    }
                }
            }
        }
    }

}
