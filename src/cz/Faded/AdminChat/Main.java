package cz.Faded.AdminChat;


import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{


    public void onEnable(){
        getCommand("adminchat").setExecutor(this);
        getCommand("adminchatreload").setExecutor(this);
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }
    public void onDisable(){ }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(cmd.getName().equalsIgnoreCase("adminchat")){
            Player p = (Player) sender;
            if(p.hasPermission(getConfig().getString("permission"))){
                if(!(args.length == 0)) {
                    String message = "";
                    for (int i = 0; i < args.length; i++) {
                        message = message + args[i] + " ";
                    }
                    for (Player op : Bukkit.getOnlinePlayers()) {
                        if(op.hasPermission(getConfig().getString("permission"))) {
                            op.sendMessage(getConfig().getString("format").replace("\"", "").replace("&", "§").replace("%player%", p.getName()).replace("%message%", message));
                            op.playSound(op.getLocation(), Sound.BLOCK_NOTE_PLING, 2.0F, 2.0F);
                        }
                    }
                } else {
                    p.sendMessage(getConfig().getString("usage_message").replace("\"", "").replace("&", "§"));
                }
            } else {
                p.sendMessage(getConfig().getString("permission_message").replace("\"", "").replace("&", "§"));
            }
        }
        if(cmd.getName().equalsIgnoreCase("adminchatreload")){
            if(sender.hasPermission("adminchat.reload")){
                reloadConfig();
                sender.sendMessage("§cAdminChat: Config reloaded");
            } else{
                sender.sendMessage(getConfig().getString("permission_message").replace("\"", "").replace("&", "§"));
            }
        }
        return false;
    }
}