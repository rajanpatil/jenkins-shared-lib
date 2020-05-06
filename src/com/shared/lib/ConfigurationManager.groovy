package com.shared.lib

import com.cloudbees.groovy.cps.NonCPS

class ConfigurationManager implements Serializable {

    private Map defaultConfig = [
            agent         : 'master',

            configureStage: [
                    agent: 'configure-python'
            ],
            installStage  : [
                    agent: 'install-python'
            ],
            codeStyleStage: [
                    agent: 'code-style-python'
            ],
            testStage     : [
                    agent: 'test-python'
            ]
    ]

    private Map config

    ConfigurationManager(Map config) {

        if (config == null || config.isEmpty()) {
            this.config = defaultConfig
        } else {
            this.config = mergeConfig(defaultConfig, config)
        }

    }

    Map getStageConfiguration(String name) {
        config.get(name)
    }

    String getConfiguration(String key) {
        config.get(key)
    }

    @NonCPS
    private def mergeConfig(Map defaultConfig, Map config) {
        Map result = [:]
        [defaultConfig, config].each { map ->
            map.each { key, value ->
                result[key] = result[key] instanceof Map ? mergeConfig(result[key], value) : value
            }
        }

        result
    }
}
