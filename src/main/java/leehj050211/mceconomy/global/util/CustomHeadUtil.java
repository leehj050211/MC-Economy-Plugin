package leehj050211.mceconomy.global.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@UtilityClass
public class CustomHeadUtil {

    private final String minecraftUrl = "http://textures.minecraft.net/texture/";

    public ItemStack getHead(String textureId) {
        try {
            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
            PlayerTextures texture = profile.getTextures();
            texture.setSkin(new URL(minecraftUrl + textureId));
            profile.setTextures(texture);
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
            skullMeta.setPlayerProfile(profile);
            head.setItemMeta(skullMeta);
            return head;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
