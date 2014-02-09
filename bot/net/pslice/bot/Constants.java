package net.pslice.bot;

public interface Constants {

    /*
     * ===========================================
     * File locations:
     *
     * These are currently final and cannot be
     *   changed by the user. Later versions may
     *   allow for this.
     * ===========================================
     */

    String
            command_location = "commands",

            user_rank_location = "users.ranks",
            user_nick_location = "users.nicks",

            alert_location = "util.alerts",
            function_location = "util.functions",
            memory_location = "util.memories",

            properties_location = "bot.properties";
}
