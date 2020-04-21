import com.shared.lib.PythonServiceBuildSteps

def mergeConfig(Map config) {
    Map defaultConfig = [
            install: [
                    steps: PythonServiceBuildSteps.defaultInstallSteps
            ],
            checkStyle: [
                    steps: PythonServiceBuildSteps.defaultCheckCodeStyleSteps
            ],
            tests: [
                    steps: PythonServiceBuildSteps.defaultRunTestsSteps
            ],
            build: [
                    steps: PythonServiceBuildSteps.defaultBuildSteps
            ]
    ]

    Map result = [:]
    [defaultConfig, config].each { map ->
        map.each { key, value ->
            result[key] = result[key] instanceof Map ? mergeConfig(result[key], value) : value
        }
    }

    result
}

def call(Map config) {

    def buildSteps = new PythonServiceBuildSteps(this)
    def configuration = mergeConfig(config)

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
