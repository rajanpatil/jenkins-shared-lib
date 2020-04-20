package com.shared.lib

class EchoTest implements Serializable {

    def script

    EchoTest(script) {
        this.script = script
    }

    def echoMessage(message) {
        script.echo "EchoTest: ${message}"
    }
}
