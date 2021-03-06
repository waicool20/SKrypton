# SKrypton [![Build Status](https://travis-ci.org/waicool20/SKrypton.svg?branch=master)](https://travis-ci.org/waicool20/SKrypton) [![Build status](https://ci.appveyor.com/api/projects/status/ymrghyv7oas5q2iu?svg=true)](https://ci.appveyor.com/project/waicool20/skrypton) [ ![Download](https://api.bintray.com/packages/waicool20/SKrypton/skrypton-api/images/download.svg) ](https://bintray.com/waicool20/SKrypton/skrypton-api/_latestVersion)

A browser automation library that uses the SikuliX framework but doesn't grab the mouse.

This library uses QtWebEngine as its backbone for displaying webpages, therefore apart from SikuliX 
this project also has its own set of native components which need to be pre-installed. The library
utilizes JNI to bridge between the native and JVM side. 

The library works by providing its own sub-classes of Region, Match and Screen (Just prefixed with
SKrypton) Each SKryptonScreen instance has its own virtual mouse, keyboard and clipboard. Therefore
each can be controlled independently from each other and the desktop. To aid debugging, a virtual 
mouse indicator (Visibility can be toggled) has been added.

The name SKrypton comes from combining Script and Krypton, why Krypton? Because it's an element 
two Atomic Numbers away from [Selenium](http://www.seleniumhq.org/) another well-known browser
automation tool named after an element. SKrypton can support control from Selenium if remote debug 
port is enabled.

[Here's a small clip to see it in action](https://streamable.com/t1oiu)

[This is the file being showcased](src/test/kotlin/com/waicool20/skrypton/SKryptonGithub.kt)

# Installation and Usage

### I just want to run a SKrypton program!:

- Requires [Java JRE 8](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) installed
- SKrypton Native components (Installable Jar under Downloads, just double click and follow instructions)
    - [Windows 64-bit](https://bintray.com/waicool20/SKrypton/skrypton-native-windows64#)
    - [Linux 64-bit](https://bintray.com/waicool20/SKrypton/skrypton-native-linux64#)
    - I don't own a Mac, so no builds for it yet sorry.

### Developing with the API:

#### Official builds

SKrypton is available on the jcenter and maven central repository, replace `VERSION` with the 
version specified by the badge above or a version of your own choosing.

```
repositories {
    // For resolving SKrypton artifacts
    jcenter()       // or mavenCentral()
    // For resolving SikuliX artifacts
    maven { url 'https://oss.sonatype.org/content/repositories/snapshots/' }
}
dependencies {
    compile group: 'com.waicool20.skrypton', name: 'skrypton-api', version: 'VERSION'
}
```

#### Snapshots

[Snapshots for api jar are available here](https://oss.jfrog.org/artifactory/oss-snapshot-local/com/waicool20/skrypton/skrypton-api) 

To include the library API snapshot in your project you can add this snippet to your `build.gradle` file,
replace `VERSION` with a specific snapshot (Take a look in the link above and get the latest one)

```
repositories {
    // For resolving SKrypton artifacts
    maven { url 'https://oss.jfrog.org/artifactory/oss-snapshot-local/' }
    // For resolving SikuliX artifacts
    maven { url 'https://oss.sonatype.org/content/repositories/snapshots/' }
}
dependencies {
    compile group: 'com.waicool20.skrypton', name: 'skrypton-api', version: 'VERSION'
}
```

[Snapshots for the installers are also available in the group directory](https://oss.jfrog.org/artifactory/oss-snapshot-local/com/waicool20/skrypton/)

#### Quick start (Kotlin API):

```kotlin
fun main(args: Array<String>) {
    /* Initialize the SKryptonApp instance with the args from the command line
       Since SKryptonApp is chromium based, we can enable DevTools on port 8888
       and access it through localhost:8888 */
    SKryptonApp.initialize(args, remoteDebugPort = 8888) {
        // Initialize a screen with the given URL
        screen("https://github.com/waicool20/SKrypton") {
            // Click coordinates X: 100 Y: 100
            // This is from the SikuliX API, refer to these docs for more info:
            // https://sikulix-2014.readthedocs.io/en/latest/region.html
            click(Location(100, 100))
        }
        // Initialize a screen with the given URL, width and height
        screen("https://github.com/waicool20/SKrypton", width = 800, height = 600) {
            // Type "Hello World!" into the screen.
            // This is from the SikuliX API, refer to these docs for more info:
            // https://sikulix-2014.readthedocs.io/en/latest/region.html        
            type("Hello World!") 
        }
        // Initialize a screen that doesn't show the virtual cursor
        screen("https://github.com/waicool20/SKrypton", showCursor = false) {
            // Move mouse to X: 100 Y: 100 but don't click
            // This is from the SikuliX API, refer to these docs for more info:
            // https://sikulix-2014.readthedocs.io/en/latest/region.html
            hover(Location(100, 100))
        }        
    }.exec(true) // Finally execute the SKryptonApp instance
}
```

# Documentation

[You are welcome to checkout the API documentation over here](https://waicool20.github.io/SKrypton/skrypton-api/)

# Build instructions

### Prerequisites:

Common:
- [Java JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [CMake 3.9.x](https://cmake.org/download/)
- [Qt 5.9.1](https://www1.qt.io/download-open-source/#section-2)
    - QtWebEngine is a required component and must be installed
    - On Windows, MSVC version must be used (MinGW etc. versions are untested)
    
Linux:
- GNU GCC7 (Available readily under this PPA `ubuntu-toolchain-r-test`on Ubuntu based distros)
- GNU G++7 (Available readily under this PPA `ubuntu-toolchain-r-test`on Ubuntu based distros)
- libGLESv2 (Typically incl. in `libgles2-mesa` package on Ubuntu based distros)
- libEGL (Typically incl. in `libegl1-mesa` package on Ubuntu based distros)

Windows:
- [MSVC 2017](https://www.visualstudio.com/downloads/)

---
### Actually building the project:

Linux:
```bash
# Build API Jar and native components release jar only
./gradlew build

# Build All Jars (incls. Test Jar and native components debug jar)
./gradlew buildAll
```

Windows:
```
# Build API Jar and native components release jar only
gradlew.bat build

# Build All Jars (incls. Test Jar and native components debug jar)
gradlew.bat buildAll
```

API Jar and Test Jar are generated under:

> build/libs

Native component Jars are generated under:

> native/build/libs

# License

This project is licensed under the MIT license. See [LICENSE.md](LICENSE.md) for more details.
