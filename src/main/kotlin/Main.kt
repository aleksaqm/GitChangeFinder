package org.example

fun main() {
    val branchA = "develop"  // Replace with your actual branch names
    val branchB = "main"
    val localRepoPath = "C:/Users/Aleksa/Documents/jetbrains_tasks/teamcity_task/TeamCityTask1/"

    val mergeBase = findMergeBaseCommit(branchA, branchB, localRepoPath)
    println(mergeBase)

    val owner = "aleksaqm"
    val repo = "TeamCityTask1"
    val accessToken = "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"


    val filesListRemote = mergeBase?.let { getChangedFilesRemoteBranch(owner, repo, branchA, it, accessToken) }
    println(filesListRemote)
}