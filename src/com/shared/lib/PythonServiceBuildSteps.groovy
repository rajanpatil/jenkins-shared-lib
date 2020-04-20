package com.shared.lib

class PythonServiceBuildSteps implements Serializable {

    def script

    PythonServiceBuildSteps(script){
        this.script = script
    }

    def install(){
        script.sh 'poetry install'
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
