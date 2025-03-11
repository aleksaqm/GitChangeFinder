package org.example

fun compareChanges(localChanges: List<String>, remoteChanges: List<String>): List<String> {
    val remoteSet = remoteChanges.toSet()
    return localChanges.filter { it in remoteSet }
}

