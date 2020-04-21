package com.shared.lib

import com.cloudbees.groovy.cps.NonCPS

class ConfigManager {
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
    Map config

    ConfigManager(Map config){
        if (config !=null && !config.isEmpty())
            this.config = mergeConfig(defaultConfig, config)
        else
            this.config = defaultConfig
    }

    @NonCPS
    def getConfig(String stage){
        this.config.get(stage)
    }

    @NonCPS
    def mergeConfig(Map defaultConfig, Map config) {
        Map result = [:]
        [defaultConfig, config].each { map ->
            map.each { key, value ->
                result[key] = result[key] instanceof Map ? mergeConfig(result[key], value) : value
            }
        }

        result
    }
}
