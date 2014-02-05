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
            command_class_location = "commands.classes",
            command_rank_location = "commands.ranks",
            command_parameter_location = "commands.parameters",
            command_description_location = "commands.descriptions",
            command_enabled_location = "commands.enabled",

            user_rank_location = "users.ranks",
            user_nick_location = "users.nicks",

            alert_location = "util.alerts",
            link_location = "util.links",

            properties_location = "bot.properties";
}
