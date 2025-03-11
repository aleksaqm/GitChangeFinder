package org.example

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

fun getChangedFilesLocalBranch(mergeBaseCommit : String, branch: String, localRepoPath: String): List<String>{
    val processBuilder = ProcessBuilder("git", "diff", "--name-only", mergeBaseCommit, branch)
        .redirectErrorStream(true)
        .directory(File(localRepoPath))
    val process = processBuilder.start()
    val reader = BufferedReader(InputStreamReader(process.inputStream))
    val files = reader.readLines()
    reader.close()

    val exitCode = process.waitFor()
    if (exitCode != 0){
        println(process.errorStream.reader().readText())
        return emptyList()
    }
    return files
}