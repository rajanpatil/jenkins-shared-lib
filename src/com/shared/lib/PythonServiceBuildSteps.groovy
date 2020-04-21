package com.shared.lib

class PythonServiceBuildSteps implements Serializable {

    def script

    PythonServiceBuildSteps(script) {
        this.script = script
    }

    static def defaultInstallSteps = { script ->
        script.sh 'poetry install'
    }

    static def defaultCheckCodeStyleSteps = { script ->
        script.sh '''
                poetry run black --check .
                poetry run mypy .
                poetry run flake8
            '''
    }

    static def defaultRunTestsSteps = { script ->
        script.sh 'poetry run pytest'
    }

    static def defaultBuildSteps = { script ->
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

}
