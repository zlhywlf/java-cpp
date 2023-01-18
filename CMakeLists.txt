cmake_minimum_required(VERSION 3.0.0)
project(demo VERSION 0.1.0)

include(CTest)
enable_testing()

include_directories(
    $ENV{JAVA_HOME}/include
    $ENV{JAVA_HOME}/include/linux
)


set(LIBRARY_OUTPUT_PATH "../demo/src/main/resources")


add_library(demo SHARED cpp/demo.cpp)

set(CPACK_PROJECT_NAME ${PROJECT_NAME})
set(CPACK_PROJECT_VERSION ${PROJECT_VERSION})
include(CPack)
