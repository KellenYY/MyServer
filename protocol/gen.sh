#!/bin/sh
rm -rf ./gen-csharp/*
rm -rf ./gen-java/*

echo 'generate thrift ...'

./thrift --gen java profile.thrift
./thrift --gen csharp profile.thrift