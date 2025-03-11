package org.example

import java.io.File

fun getChangedFilesLocalBranch(mergeBaseCommit : String, branch: String, localRepoPath: String): List<String>{
    val process = ProcessBuilder("git", "diff", "--name-only", mergeBaseCommit, branch)
        .directory(File(localRepoPath))
        .redirectErrorStream(true)
        .start()

    val output = process.inputStream.bufferedReader().use { it.readLines() }
    val exitCode = process.waitFor()

    if (exitCode != 0) {
        throw GitCommandException("Failed to get changed files for branch '$branch'. Ensure the branch exists and the repository is valid.")
    }

    return output
}