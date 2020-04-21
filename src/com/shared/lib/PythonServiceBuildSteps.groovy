package com.shared.lib

class PythonServiceBuildSteps implements Serializable {

    def script

    PythonServiceBuildSteps(script) {
        this.script = script
    }

    def defaultInstallSteps = { script ->
        script.sh 'poetry install'
    }

    def defaultCheckCodeStyleSteps = { script ->
        script.sh '''
                poetry run black --check .
                poetry run mypy .
                poetry run flake8
            '''
    }

    def defaultRunTestsSteps = { script ->
        script.sh 'poetry run pytest'
    }

    def defaultBuildSteps = { script ->
        script.sh 'poetry build --format=wheel'
    }


    def install() {
        install(defaultInstallSteps)
    }

    def install(customSteps) {
        customSteps(script)
    }

    def checkCodeStyle() {
        checkCodeStyle(defaultCheckCodeStyleSteps)
    }

    def checkCodeStyle(customSteps) {
        customSteps(script)
    }

    def runTests() {
        runTests(defaultRunTestsSteps)
    }

    def runTests(customSteps) {
        customSteps(script)
    }

    def build() {
        build(defaultBuildSteps)
    }

    def build(customSteps) {
        customSteps(script)
    }

    def mergeConfig(Map serviceConfig) {
        Map defaultConfig = [
                install   : [
                        steps: this.defaultInstallSteps
                ],
                checkStyle: [
                        steps: this.defaultCheckCodeStyleSteps
                ],
                tests     : [
                        steps: this.defaultRunTestsSteps
                ],
                build     : [
                        steps: this.defaultBuildSteps
                ]
        ]

        Map result = [:]
        [defaultConfig, serviceConfig].each { map ->
            map.each { key, value ->
                result[key] = result[key] instanceof Map ? mergeConfig(result[key], value) : value
            }
        }

        result
    }

}
