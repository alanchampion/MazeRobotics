# MazeRobotics
A discrete event simulation of robots solving a floor-plan like maze. 

The goal is to create a efficient way for robots to find a solution to a floor-plan like maze. Currently, the code can solve a floor-plan as specified in the configuration file. Future work includes creating a graphical display of this process and making the maze solving even more efficient. 

## Getting Started
Make the project
```
make
```
Run with parameters. You can replace examples/test.txt with a configuration file of your choice. 
```
./SIM examples/test.txt
```

## Creating a Configuration File
Here is an example configuration file. 
```
roomA 250
roomB 100
roomC 150

roomA 10 roomB 10 roomC
roomA 0 roomC

roomA
roomC

roomA roomC
```
The first lines are defining each room with a size of the room. 

Second set of lines define the connections of each room. Notice that you can chain connections if you want to. (In this example there is a hallway of length 10 from roomA to roomB, a hallway of length 10 from roomB to roomC, and a hallway of length 0 from roomA to roomC. 

Next set of lines are the starting locations for any robots. You can have as many robots as you like, and you can place multiple in the same room. 

The last line of the file is the goal rooms. In this example, the robots are trying to find a path from roomA to roomC (or vice versa). 

## Arguments
The simulation requires at least the configuration file as an argument.

Argument | Default | Description
-------- | ------- | -----------
Configuration File | none | This pulls in the configuration file that will define the space, robot starting locations, and goal rooms.
Steps | `-1` | This defines the number of steps to run. If `-1` is specified, than the simulation will run until completion.
Hallway Width | `10` | This defines the width of each hallway. For example, if a hallway's length is specified to be `15` in the configuration file and the width is specified to be `10`, than the hallway will have a size of `150`.
Step Size | `10` | This defines how much a robot can scan on each step. The bigger this is, the more that a robot can scan in each step and the quicker the simulation will end.

## Authors
* **Alan Champion** - *initial work* - [AlanChampion](https://github.com/AlanChampion)

See also the list of [contributors](https://github.com/alanchampion/MazeRobotics/contributors) who participated in this project.

## About
This project was created for the Robotics Planning and Manipulation course at Colorado School of Mines. 
