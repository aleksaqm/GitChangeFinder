package org.example.org.example.gitlocal

import org.example.org.example.exceptions.GitCommandException
import java.io.File

fun findMergeBaseCommit(branchA: String, branchB: String, localRepoPath: String): String {
    val process = ProcessBuilder("git", "merge-base", "origin/$branchA", branchB)
        .directory(File(localRepoPath))
        .redirectErrorStream(true)
        .start()

    val output = process.inputStream.bufferedReader().use { it.readLine()?.trim() }
    val exitCode = process.waitFor()

    if (exitCode != 0 || output.isNullOrBlank()) {
        throw GitCommandException("Failed to find merge base between '$branchA' and '$branchB'. Ensure the branches exist and are properly set up.")
    }

    return output
}