package org.example


fun main() {
    val branchA = "develop"
    val branchB = "main"
    val localRepoPath = "C:/Users/Aleksa/Documents/jetbrains_tasks/teamcity_task/TeamCityTask1/"

    val owner = "aleksaqm"
    val repo = "TeamCityTask1"
    val accessToken = "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"

    val gitChangeFinder: GitChangeFinder = GitChangeFinder()
    val sameChanges = gitChangeFinder.findChangedFiles(owner, repo, accessToken, localRepoPath, branchA, branchB)
    println(sameChanges)
}