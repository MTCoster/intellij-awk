# intellij-awk
[WiP] The missing IntelliJ IDEA language support plugin for [AWK](https://en.wikipedia.org/wiki/AWK)

## Motivation

- At the moment there is no AWK plugin for IDEA, which is a pity.
- Interested to sharpen my Java skills and learn some IDEA internals. 

## Goals v0.0.1

- Support basic AWK code highlighting
- Support basic AWK code navigations (go to declaration, show structure)
- Support only POSIX subset (aka BWK), w/o Gawk additions (this can be added later)

## Goals v0.0.2

- Showing documentation for built-in functions
- Showing documentation for built-in variables (`NR`/`NF`/etc.)

## Goals v0.0.3

- Enforce variable naming convention
    - `Name` for global
    - `name` for local


## Useful links

- BWK https://github.com/onetrueawk/awk
- [Custom language support JetBrains tutorial](https://plugins.jetbrains.com/docs/intellij/custom-language-support.html)
- Grammar:
    - [AWK Grammar in Yacc from BWK](https://github.com/onetrueawk/awk/blob/master/awkgram.y)
    - [AWK Grammar in Yacc from Gawk](http://git.savannah.gnu.org/cgit/gawk.git/tree/awkgram.y)
    - https://slebok.github.io/zoo/#awk
        - [EBNF Grammar](https://github.com/slebok/zoo/blob/master/zoo/awk/manual/fetched/src.grammar.txt)
        - https://pubs.opengroup.org/onlinepubs/7908799/xcu/awk.html#tag_000_000_108_016