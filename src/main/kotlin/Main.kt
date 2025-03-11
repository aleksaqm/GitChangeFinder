package org.example

fun main() {
    val branchA = "develop"
    val branchB = "main"
    val localRepoPath = "C:/Users/Aleksa/Documents/jetbrains_tasks/teamcity_task/TeamCityTask1/"

    val mergeBase = findMergeBaseCommit(branchA, branchB, localRepoPath)
    println(mergeBase)

    val owner = "aleksaqm"
    val repo = "TeamCityTask1"
    val accessToken = "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"


    val filesListRemote = mergeBase?.let { getChangedFilesRemoteBranch(owner, repo, branchA, it, accessToken) }
    val filesListLocal = mergeBase?.let { getChangedFilesLocalBranch(it, branchA, localRepoPath) }
    println(filesListLocal)
    println(filesListRemote)
    if (filesListRemote != null && filesListLocal != null) {
        val finalList = compareChanges(filesListLocal, filesListRemote)
        println(finalList)
    }
}