package org.example.utils

fun getCommonItems(localChanges: List<String>, remoteChanges: List<String>): List<String> {
    if (localChanges.isEmpty() || remoteChanges.isEmpty()) return emptyList()

    val remoteSet = remoteChanges.toHashSet()
    return localChanges.filter { it in remoteSet }
}

