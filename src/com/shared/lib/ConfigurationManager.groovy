package com.shared.lib

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
            Map result = [:]
            [defaultConfig, config].each { map ->
                map.each { key, value ->
                    result[key] = result[key] instanceof Map ? mergeConfig(result[key], value) : value
                }
            }
            this.config = result
        }

    }

    Map getStageConfiguration(String name) {
        config.get(name)
    }

    String getConfiguration(String key) {
        config.get(key)
    }
}
