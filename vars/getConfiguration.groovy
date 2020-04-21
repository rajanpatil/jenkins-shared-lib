package com.shared.lib

import groovy.transform.Field


@Field
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

@Field
Map config

def call(Map serviceConfig) {

    node {

        if (serviceConfig !=null && !serviceConfig.isEmpty())
            this.config = mergeConfig(defaultConfig, serviceConfig)
        else
            this.config = defaultConfig

        return config
    }

}

def mergeConfig(Map defaultConfig, Map config) {
    Map result = [:]
    [defaultConfig, config].each { map ->
        map.each { key, value ->
            result[key] = result[key] instanceof Map ? mergeConfig(result[key], value) : value
        }
    }

    result
}

