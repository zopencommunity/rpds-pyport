# Patches for python-rpds-pyport

## Overview

`rpds-py` is a Rust extension cross-compiled from LoP. No source patches applied by zopen-build.

## Cross-compilation

Same infrastructure as python-pydantic-coreport. Patches applied at cross-compile time:
- pyo3 0.29 fork (branch `itodorov/zos-support`)
- libc-zos fork
- target-lexicon git main (for `OperatingSystem::Zos`)
- libpython3.12.x side-deck linker fix

## Test results

- HashTrieMap, HashTrieSet, List: 7 tests per interpreter
