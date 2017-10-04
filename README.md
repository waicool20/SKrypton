# SKrypton [![Build Status](https://travis-ci.org/waicool20/SKrypton.svg?branch=master)](https://travis-ci.org/waicool20/SKrypton) [![Build status](https://ci.appveyor.com/api/projects/status/ymrghyv7oas5q2iu?svg=true)](https://ci.appveyor.com/project/waicool20/skrypton)

A browser automation library that uses the SikuliX framework but doesn't grab the mouse

# Installation and Usage

- Requires [Java JRE 8](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) installed
- SKrypton Native components // TODO Link in native components installer download

// TODO Link in maven/gradle repo link

# Build instructions

Prerequisites:

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
