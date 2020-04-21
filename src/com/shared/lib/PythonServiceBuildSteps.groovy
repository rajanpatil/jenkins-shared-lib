package com.shared.lib

class PythonServiceBuildSteps implements Serializable {

    def script

    PythonServiceBuildSteps(script) {
        this.script = script
    }

    def defaultInstallSteps = { script ->
        script.sh 'poetry install'
    }

    def install() {
        install(defaultInstallSteps)
    }

    def install(customSteps) {
        customSteps(script)
    }

    def checkCodeStyle() {
        def defaultSteps = { script ->
            script.sh '''
                poetry run black --check .
                poetry run mypy .
                poetry run flake8
            '''
        }

        checkCodeStyle(defaultSteps)
    }

    def checkCodeStyle(customSteps) {
        customSteps(script)
    }

    def runTests() {
        def defaultSteps = { script ->
            script.sh 'poetry run pytest'
        }

        runTests(defaultSteps)
    }

    def runTests(customSteps) {
        customSteps(script)
    }

    def build() {
        def defaultSteps = { script ->
            script.sh 'poetry build --format=wheel'
        }

        build(defaultSteps)
    }

    def build(customSteps) {
        customSteps(script)
    }

}
