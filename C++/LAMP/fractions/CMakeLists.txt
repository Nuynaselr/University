cmake_minimum_required(VERSION 3.9)
project(fractions)

set(CMAKE_CXX_STANDARD 11)

add_executable(fractions main.cpp cmake-build-debug/RationalFraction.h RationalFraction.h cmake-build-debug/RationalFraction.cpp Decimal.cpp)