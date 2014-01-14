AMP-1001
========

A second IRC bot made by me.

The new AMP-1001: Now with other stuff!

It's been a while since anything has changed here, however recently I completed some major changes to how the bot works with and saves everything from commands to users, and all that stuff in between. Although I haven't honestly compared its speed with older versions, I have reason to believe that the new method of doing things is many many times faster, especially since it's no longer necessary to do silly things like continuously read from txt files and the like. There is also a wide range of new commands and abilities, with more on the way.

General Commands:
- quit: Command to disconnect and shut down.
- join <#channel>: Command to join a channel.
- leave (#channel): Command to leave a channel. 
    - If no channel is specified, leaves channel the command was run in.
- kick \<user> (reason): Command to kick a user from the channel.
    - If no reason is specified, the message is "Kicked from channel".
- help (command): Command to display info about a command.
    - If no command is specified, a list of all possible commands is given.
- say (channel) \<message>: Command to send a message to a channel.
    - If no channel is specified, the message is sent to the channel the command was run in.
- link \<name> (new link): Command to display a link to a saved website.
    - To save a new link, put the URL after the name of the link.
- user \<name>: Command to view info about a user.
- rename \<name>: Command to change the name of the bot.

Administrative Commands:
- setcommand \<setting> \<command> \<value>: Command to change a setting of a command.
- setuser \<setting> \<user> \<value>: Command to change a setting of a user.
- setproperty \<property> \<value>: Command to change the default properties the bot loads with.
- reload: Command to reload all files.
- verbose: Command to toggle if the bot sends raw info to the console
- toggleinputmethod*: Command to toggle how ranks are assigned.
- override**: Command to toggle override mode.
- identify: Command to identify the bot with NickServ.

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
- Default values are all stored in the program, so missing files are quickly and easily replaced.

There's still a lot to do, but hey, it works for now.
