package com.shared.lib

class EchoTest implements Serializable {

    def steps

    EchoTest(steps) {
        this.steps = steps
    }

    def echoMessage(message) {
        steps.echo "EchoTest: ${message}"
    }
}
