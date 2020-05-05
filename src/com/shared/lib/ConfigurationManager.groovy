package com.shared.lib

class ConfigurationManager implements Serializable {

    private Map defaultConfig = [
            agent : 'python',
            stages: [
                    configure: [
                            agent: 'python'
                    ],
                    install  : [
                            agent: 'python'
                    ],
                    codeStyle: [
                            agent: 'python'
                    ],
                    test     : [
                            agent: 'python'
                    ]
            ]

    ]

    private Map config

    ConfigurationManager(Map config) {

        this.config = mergeConfig(defaultConfig, config)

    }

    Map getStageConfiguration(String name) {
        config.get('stages').getAt(name)
    }

    String getConfiguration(String key) {
        config.get(key)
    }

    private def mergeConfig(Map defaultConfig, Map config) {
        if (config == null || config.isEmpty())
            return defaultConfig
        Map result = [:]
        [defaultConfig, config].each { map ->
            map.each { key, value ->
                result[key] = result[key] instanceof Map ? mergeConfig(result[key], value) : value
            }
        }

        result
    }
}
