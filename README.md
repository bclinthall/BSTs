# BSTs

## Using gradle now

I've set this up using the `gradle` build system.  It's like `make` but with more features.

When you clone our git repo, you will have a file called `gradlew` and another called `gradlew.bat`.  
If you execute either of those, all the gradle stuff you need to use `gradle` will be downloaded.

Gradle looks in `src/main/java` for regular classes and `src/test/java` for unit test classes. 
As long as you put stuff in the right places, it gets compiled and tested.

Run `./gradlew test` to test.

Run `/gradlew run` to run it.  It currently does 3 tests for how many operations it takes a 
large splay tree to find all the keys in a large list of keys.

There is now a Gradle eclipse plugin.  http://www.vogella.com/tutorials/EclipseGradle/article.html

### For future reference
If you have gradle installed, you can initiate a gradle project with `gradle init --type java-application`. 
Or you can set the type to `java-library` if that is more appropriate. That will set up a directory structure, 
a `build.gradle` file, and even a dummy class and dummy test class. It doesn't auto generate a `.gitignore`, 
so google "Gradle Java gitignore" and follow the advice before committing a bunch of stuff you don't really 
want git tracking.
