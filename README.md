# GitChangeFinder

GitChangeFinder is a Kotlin library that identifies files modified independently in both a remote branch and a local branch since their common merge base.

## Features
- Returns a list of files that were changed in both branches (local and remote).

## Installation
Build the JAR Using Gradle.
   
Run the following command from the root of the project:

- `./gradlew clean build`

The JAR file will be located in:
`build/libs/git-change-finder-1.0.0.jar`

## Usage Example
### Add the JAR to Your Project
If you're using this library in another project, add the JAR as a dependency:

For Gradle (Kotlin DSL):
```
dependencies {
    implementation(files("libs/git-change-finder-1.0.0.jar"))
}
```

For Gradle (Groovy DSL):
```
dependencies {
    implementation files('libs/git-change-finder-1.0.0.jar')
}
```

### Example Code Usage in Kotlin:
```kotlin
import my.package.GitChangeFinder

fun main() {
    val finder = GitChangeFinder()

    val changedFiles = finder.findChangedFiles(
        owner = "your-github-username",
        repo = "your-repository",
        accessToken = "your-github-token",
        localRepoPath = "/path/to/local/repo",
        branchA = "main",
        branchB = "feature-branch"
    )

    println("Files changed in both branches: $changedFiles")
}
```

Example Output:
> Files changed in both branches: [src/main/App.kt, src/utils/Helper.kt]

### Parameters Explained

| Parameter  | Description |
| ------------- | ------------- |
| owner  | GitHub repository owner (username or organization)  |
| repo  | GitHub repository name  |
| accessToken  | GitHub Personal Access Token (PAT) for authentication  |
| localRepoPath  | Absolute path to the local repository  |
| branchA  | Remote branch (e.g., main)  |
| branchB  | Local branch (e.g., feature-branch)  |


### API Design
Public Function
```kotlin
fun findChangedFiles(
    owner: String,
    repo: String,
    accessToken: String,
    localRepoPath: String,
    branchA: String,
    branchB: String
): List<String>
```
This is the only public function in the library.
All internal functions (LocalHandler, RemoteHandler) are encapsulated.

### Running Tests
The project includes unit tests. To run them, execute:

`./gradlew test`


