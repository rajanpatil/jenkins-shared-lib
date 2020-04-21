package com.shared.lib

class PythonServiceBuildSteps implements Serializable {

    def script

    PythonServiceBuildSteps(script){
        this.script = script
    }

    def install(){
        def defaultSteps = { script ->
            script.sh 'poetry install'
        }
        install(defaultSteps)
    }

    def install(customSteps){
        customSteps(script)
    }

    def checkCodeStyle(){
        script.sh '''
            poetry run black --check .
            poetry run mypy .
            poetry run flake8
        '''
    }

    def runTests(){
        script.sh 'poetry run pytest'
    }

    def build(){
        script.sh 'poetry build --format=wheel'
    }

}
