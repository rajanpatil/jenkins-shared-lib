package com.shared.lib

import com.cloudbees.groovy.cps.NonCPS

class ConfigManager implements Serializable {

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

    ConfigManager(Map config, script) {
        String repoName = getRepoName(script.env.GIT_URL)

        defaultConfig.put("repoName", repoName)

        if (config == null || config.isEmpty()) {
            this.config = defaultConfig
        } else {
            this.config = mergeConfig(defaultConfig, config)
        }

    }

    ConfigManager(Map config) {

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
                result[key] = result[key] instanceof Map ? mergeConfig(result[key] as Map, value as Map) : value
            }
        }

        result
    }

    @NonCPS
    private def getRepoName(String gitUrl) {
        def matcher = gitUrl =~ /([^\/]+)\.git$/
        if (matcher.find()) {
            return matcher.group(1)
        }
        throw new RuntimeException("Couldn't get repo name from gitURL: "+ gitUrl)
    }
}
