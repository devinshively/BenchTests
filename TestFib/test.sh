#!/bin/bash
wrk -c 64 -d 30s http://localhost:4567/10
