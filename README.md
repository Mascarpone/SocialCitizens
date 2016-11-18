# SocialCitizens
A Bukkit plugin creating singular NPCs able to build a believable social network.

![alt tag](https://github.com/Mascarpone/SocialCitizens/blob/master/doc/Proposal%20slide.pdf)

## Context
The SocialCitizens project is a Game AI prototype written by **Mascarpone** and **gemabrow** in November 2016 as part of their final assignment at UCSC, Santa Cruz, USA.

The main idea is to use a genetic algorithm with utility-curve-based genes to simulate a unique NPC's personality. Then a connected graph is generated to represent the social ties between all the players. The  personality and actual status of an NPC in addition to its position in the graph will lead it to make utility-based decisions.

## Tools
This plugin is built for Bukkit (Minecraft development server) and uses the Citizens API. It's a Maven project written in Java.

See https://dev.bukkit.org/bukkit-plugins/citizens/.

## How to use SocialCitizens
A working `SocialCitizens.jar` file will be available on this repository at the end of the project. 
However, you can contribute to the project by loading it as a Maven project:

- Import this repository in your favorite IDE (like eclipse) as a **Maven project**.
- To build the .jar, click `File > Run as > Maven install`. The .jar can be found in the _target_ folder of the project.
- To test the pugin in the game, Copy and Paste the .jar file into the _plugin_ folder of your Bukkit server.

**Explain how to spawn a social citizen in the game HERE.**

## Documentation
The code is partly documented in source files and a JavaDoc would probably be generated at the end of the project.
Nevertheless, the `doc` folder contains precious information about the theories used in the prototype and what what is supported in it.
