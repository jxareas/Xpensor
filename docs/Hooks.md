# Git Hooks

This project has some Git hooks included inside the [git-hooks](/hooks) folder. These hooks can be
installed automatically via the Gradle commands `./gradlew copyGitHooks`
and `./gradlew installGitHooks`. You can find these commands
in [this Gradle file](/buildscripts/githooks.gradle), but it's also good to know that the hooks are
installed automatically just by running a `clean` task.

## Pre-Commit

There is a [pre-commit](/hooks/pre-commit.sh) hook that will automatically run Ktlint formatting
over any modified Kotlin files.
This way you can just commit your code and trust that formatting happens behind the scenes,
without having to consciously worry about it.

## Pre-Push

There is a [pre-push](/hooks/pre-push.sh) hook that will run static analysis checks before any code
is pushed up to the remote repository.
This way, if you have any code smells, you can be alerted right away without waiting for
some Github Actions to fail.
