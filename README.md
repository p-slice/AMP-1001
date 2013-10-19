AMP-1001
========

A second IRC bot made by me.

Overview:
   Although in some ways he will be similar to my previous IRC bot, AMP-1001 will also have some noticeable differences. The most important change will be the permissions/recognizing system, which is based on how high a user's permission rank is. Different commands will require different ranks, and all are stacked.

Commands:
(Commands are still being added, this won't be complete for a while)

+say ( #chan-name ) < text >: Say something into the channel. Add the channel to specify which. Required rank: 10

+quit: Quit the server. Required rank: 10

+join < #chan-name >: Join the specified channel. Required rank: 7

+leave ( #chan-name ): Leave the channel. Add the channel to specify which. Required rank: 7

+kick < player > ( reason ): Kick the specified player. Default reason is "Kicked from channel" else the given reason. Required rank: 10

+info < text >: Get bot's info of specified search. Required rank: 3

+user < info > < player >: Get the rank of the specified user. Required rank: 3

+setuser < info>  < player > < new info >:  Set the rank of the specified player. Required rank: 10

+adduser < name >: Create a new user. Defaults - nick = < name > , info = //, rank = 1. Required rank: 10

+server IP < server >: Get the IP of the specified server. Required rank: 5


More stuff will be added soon.
