# STAFF TIME TRACKER
## Table of Contents
[Summary](#Summary)  
[Installation](#Installation)  
[Commands](#Commands)  
[Configuration](#Configuration)  
[Data & Storage](#data--storage)  
[Permissions](#permissions)

## Summary
STTracker is a Minecraft server plugin project intended to track staff online time

## Installation
1. Build the plugin
```
git clone https://github.com/extstudios/STTracker.git
cd STTracker
mvn clean package
```
The project is a Maven build targeting Java 16 and depending on spigot-api:1.20-R0.1-SNAPSHOT (scope provided).
2. Drop into your server
    - Place the built JAR in your server’s plugins/ folder.
    - The plugin declares api-version: '1.20'.

## Commands
All commands are under the base command /ms (alias: /mobsoccer). Usage and behavior below

 Command                                 | What it does                                                                 
-----------------------------------------|------------------------------------------------------------------------------
 /st (no args)                           | Prints a help list for all subcommands.                                      
 /st check {playerName}                  | Shows the player’s total recorded playtime (HH:MM:SS).             
 /st reload	                             | alls the plugin’s disable/enable cycle

## Configuration

staffList.yml  
Used to persist a map of staff names → UUIDs under the staffList key.  
The plugin loads this map on enable and updates it when eligible players join.  

## Data & Storage
**Database**: SQLite file named timers.db in the plugin directory.  
**Schema**: Table playtimes 
```(id INTEGER PRIMARY KEY AUTOINCREMENT, player_uuid TEXT, login_time TEXT, logout_time TEXT).```
#### Writes:
On quit (or on server/plugin stop for still-online staff), a row is inserted with login_time and logout_time.

#### Reads:
/st check <player> sums all login_time → logout_time durations for that player. Output formatted as HH:MM:SS.

#### Cleanup:
A cleaner runs on enable and, if ≥ 35 days have elapsed since its internal marker, executes DELETE FROM playtimes. (Marker starts “now”, so this triggers only after long uptimes.)
## Permissions
Declared in plugin.yml (all default to op). There’s also a wildcard node with children.
Permission	Purpose (from plugin.yml)
