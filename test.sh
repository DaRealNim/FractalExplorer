#!/bin/bash
./run.sh -t mandelbrot
./run.sh -t mandelbrot -r -3,3,3,-3 -p 3000
./run.sh -t mandelbrot -i 1024 -r -5,5,-5,5 -p 4000
./run.sh -t mandelbrot -i 512 -r -1,1,-1,1 -p 4200
./run.sh -t julia
./run.sh -t julia -c -0.782,0.12 -r -3,3,3,-3 -p 3000
./run.sh -t julia -c -0.122333,-0.1337 -i 1024 -r -4,4,4,-4 -p 4000
./run.sh -t julia -c -0.69,0.420 -i 512 -r -1,1,-1,1 -p 4000
