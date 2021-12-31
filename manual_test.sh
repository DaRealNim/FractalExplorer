#!/bin/bash

echo "Default Mandelbrot Set:"
(time ./run.sh -t mandelbrot) 2>&1 >/dev/null | grep real | awk '{print $2}'
echo

echo "Mandelbrot 1:"
(time ./run.sh -t mandelbrot -r -3,3,3,-3 -p 3000) 2>&1 >/dev/null | grep real | awk '{print $2}'
echo

echo "Mandelbrot 2:"
(time ./run.sh -t mandelbrot -i 1024 -r -0.5630,0.64781,-0.5614,0.64781 -p 4000) 2>&1 >/dev/null | grep real | awk '{print $2}'
echo

echo "Mandelbrot 3:"
(time ./run.sh -t mandelbrot -i 512 -r -1,1,-1,1 -p 5000) 2>&1 >/dev/null | grep real | awk '{print $2}'
echo

echo "Default Julia Set:"
(time ./run.sh -t julia) 2>&1 >/dev/null | grep real | awk '{print $2}'
echo

echo "Julia 1:"
(time ./run.sh -t julia -c -0.782,0.12 -r -3,3,3,-3 -p 3000) 2>&1 >/dev/null | grep real | awk '{print $2}'
echo

echo "Julia 2:"
(time ./run.sh -t julia -c -0.122333,-0.1337 -i 1024 -r -0.5630,0.64781,-0.5614,0.64781 -p 4000) 2>&1 >/dev/null | grep real | awk '{print $2}'
echo

echo "Julia 3:"
(time ./run.sh -t julia -c -0.69,0.420 -i 512 -r -1,1,-1,1 -p 5000) 2>&1 >/dev/null | grep real | awk '{print $2}'
