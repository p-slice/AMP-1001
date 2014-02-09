AMP-1001
========

A second IRC bot made by me.

The new AMP-1001: Now with other stuff!

It's been a while since anything has changed here, however recently I completed some major changes to how the bot works with and saves everything from commands to users, and all that stuff in between. Although I haven't honestly compared its speed with older versions, I have reason to believe that the new method of doing things is many many times faster, especially since it's no longer necessary to do silly things like continuously read from txt files and the like. There is also a wide range of new commands and abilities, with more on the way.

Major Update Feb. 2nd, 2014:

Once again with even more new features added. They've all been added in below...


General Commands:
- quit: Command to disconnect and shut down.
- join <#channel>: Command to join a channel.
- leave (#channel): Command to leave a channel. 
    - If no channel is specified, leaves channel the command was run in.
- kick \<user> (reason): Command to kick a user from the channel.
    - If no reason is specified, the message is "Kicked from channel".
- help (command): Command to display info about a command.
    - If no command is specified, a list of all possible commands is given.
- info \<property>: Command to display info about a bot property.
- say (#channel) \<message>: Command to send a message to a channel.
    - If no channel is specified, the message is sent to the channel the command was run in.
    - If the bot is not in the channel, it will join it, send the message, and leave again.
- action (#channel) \<action>: Command to send an action to a channel.
    - If no channel is specified, the action is sent to the channel the command was run in.
    - If the bot is not in the channel, it will join it, send the action, and leave again.
- remember \<name> (new memory): Command to save text info for later use.
    - To save a new memory, type it in after the name.
- user \<name>: Command to view info about a user.
- rename \<name>: Command to change the name of the bot.
- twerk: Command similar to "Roulette" on most bots, only this one involves twerking.
- solve \<equation>: Command to solve a basic equation.
    - Supported operations include +, -, *, /, ^, !, brackets and functions.
- function f(x) = (equation): Command to save and view functions.
- ping: The good old pinging command.

Administrative Commands:
- setcommand \<setting> \<command> \<value>: Command to change a setting of a command.
- setuser \<setting> \<user> \<value>: Command to change a setting of a user.
- setproperty \<property> \<value>: Command to change the default properties the bot loads with.
- reload: Command to reload all files.
- verbose: Command to toggle if the bot sends raw info to the console
- toggleinputmethod*: Command to toggle how ranks are assigned.
- override**: Command to toggle override mode.
- identify: Command to identify the bot with NickServ.
- alert \<event>: Command to toggle alerts for various events.
- restart: Command to restart the program (Must be running from a .jar instance).
- reset \<command>: Command to reset a command to its default settings.

\* Options are:
- By assigned rank: ranks are custom to individual users.
- By channel rank: ranks are based on rank in channel (OP = 10, voice = 5, normal = 0).

** Override mode: Commands are only accepted by the user assigned "Master" in properties. Only the "Master" is able to use this command.

Bot Properties (Found in Files/BotInfo.properties):
- Nick: Name used by the bot.
- Login: Login used by the bot.
- Realname: Real name used by the bot.
- Verbose: Whether or not raw data is sent to the console.
- Changenick: Whether or not the bot will change its name upon joining if it is already in use.
- Master: The user who has ultimate control over the bot.
- Prefix: The symbol placed before commands.
- Server: The server to connect to upon startup.
- Channel: The channel to join upon startup.
- Identify: Whether or not the bot should try identify with NickServ upon startup.
- nickservID - UNUSED: The name the bot should try identify with.
- nickservPASS - The password the bot should try identify with.

Other features (More are being worked on, this may remain small for a while):
- PMs are forwarded to the assigned "Master".
- Commands can be run from in private messages.
- Commands can be enabled/disabled.
- Bot is once again object based instead of static method based, making it easier to run multiple at once.
- Alerts:
    - If the alert for an event is enabled, the bot will PM its "Master" with details about the event.
    - Alerts can easily be enabled or disabled.
- Default values are all stored in the program, so missing files are quickly and easily replaced.

There's still a lot to do, but hey, it works for now.
