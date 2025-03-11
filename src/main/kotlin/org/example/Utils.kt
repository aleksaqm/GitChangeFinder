package org.example

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

fun findMergeBaseCommit(branchA: String, branchB: String, localRepoPath: String): String? {
    val processBuilder = ProcessBuilder("git", "merge-base", "origin/$branchA", branchB)
    processBuilder.directory(File(localRepoPath))

    val process = processBuilder.start()
    val reader = BufferedReader(InputStreamReader(process.inputStream))

    val mergeBaseCommit = reader.readLine()
    process.waitFor()

    return mergeBaseCommit
}

