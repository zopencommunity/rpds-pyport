# rpds-py z/OS Port

`rpds-py` is a tiny Rust crate (persistent data structures) required by
`referencing` → `jsonschema` → `mcp`. It uses `pyo3 0.29` and `rpds`/`archery`.

## Patches applied

1. `Cargo.toml` — add `[patch.crates-io]` for pyo3 (local fork), libc (z/OS fork),
   and target-lexicon (git main for `OperatingSystem::Zos`).
2. `.cargo/config.toml` — inject `pyo3_macros` proc-macro `.so` and link
   `libpython3.12.x` side-deck.

## Build

```bash
export PYO3_CONFIG_FILE=/home/itodorov/rust-scripts/cross/patches/pyo3-zos/pyo3-zos-config.txt
export PYO3_NO_PYTHON=1
cd /tmp/rpds_py-2026.6.3

# Step 1: native build for proc-macro .so
cargo check
so=$(ls -t target/debug/deps/libpyo3_macros-*.so | head -1)
cp $so /tmp/libpyo3_macros_rpds.so

# Step 2: cross build (Rust compiles locally, link via server)
export CROSS_SERVER_DOMAIN=zoscan2b.pok.stglabs.ibm.com
export CC_s390x_ibm_zos=/home/itodorov/rust_bld/toolchain/s390x-ibm-zos-cc
export AR_s390x_ibm_zos=/home/itodorov/rust_bld/toolchain/s390x-ibm-zos-ar
cargo build --target s390x-ibm-zos
```

## Install on z/OS

```bash
# The module is a single .so (no pure Python wrapper needed)
scp target/s390x-ibm-zos/debug/deps/librpds.so \
    itodoro@zoscan2b:rpds.cpython-312.so

# On z/OS:
USER_SITE=$(python3 -m site --user-site)
cp rpds.cpython-312.so $USER_SITE/rpds.cpython-312.so

# Create dist-info so pip knows it's installed
mkdir -p $USER_SITE/rpds_py-2026.6.3.dist-info
echo -e "Metadata-Version: 2.1\nName: rpds-py\nVersion: 2026.6.3" \
  > $USER_SITE/rpds_py-2026.6.3.dist-info/METADATA
```

## Test

```
python3 -c "import rpds; print(dir(rpds)[:5])"
# ['HashTrieMap', 'HashTrieSet', 'List', 'Queue', 'Stack']
```
