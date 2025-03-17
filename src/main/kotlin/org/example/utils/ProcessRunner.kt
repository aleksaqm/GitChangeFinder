package org.example.org.example.utils

import java.io.File

class ProcessRunner {
    fun runCommand(command: List<String>, directory: File): String {
        val process = ProcessBuilder(command)
            .directory(directory)
            .redirectErrorStream(true)
            .start()

        val output = process.inputStream.bufferedReader().use { it.readText().trim() }
        val exitCode = process.waitFor()

        if (exitCode != 0) {
            throw RuntimeException("Command failed with exit code $exitCode: $output")
        }

        return output
    }
}