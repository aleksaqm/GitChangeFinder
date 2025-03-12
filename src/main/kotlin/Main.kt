package org.example

import org.example.org.example.gitlocal.findMergeBaseCommit

fun main() {
    val branchA = "develop"
    val branchB = "main"
    val localRepoPath = "C:/Users/Aleksa/Documents/jetbrains_tasks/teamcity_task/TeamCityTask1/"

    val owner = "aleksaqm"
    val repo = "TeamCityTask1"
    val accessToken = "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"

    val sameChanges = findChangedFiles(owner, repo, accessToken, localRepoPath, branchA, branchB)
    println(sameChanges)

}