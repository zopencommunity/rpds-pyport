# python-rpds-pyport

z/OS port of [rpds-py](https://github.com/crate-py/rpds) — Python bindings to Rust's
persistent data structures. Required by `referencing` → `jsonschema` → `mcp` → `fastmcp`.

## Installation

```sh
pip install rpds-py \
  --index-url https://repo.zopen.community/pypi/wheels/simple/ \
  --only-binary rpds-py
```

Or via zopen:
```sh
zopen install python-rpds-py
```

## How It Works

Same cross-compilation approach as `python-pydantic-coreport`: prebuilt wheels
downloaded from release assets, verified, and staged for the zopen wheel index.

`rpds-py` has minimal Rust dependencies (`rpds`, `archery`, `pyo3 0.29`) and needed
no z/OS source patches beyond the standard pyo3/libc/target-lexicon forks.
