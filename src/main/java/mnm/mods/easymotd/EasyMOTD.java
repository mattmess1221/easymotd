package mnm.mods.easymotd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloadListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.LowercaseEnumTypeAdapterFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EasyMOTD implements ModInitializer, SynchronousResourceReloadListener {

    private static final Logger logger = LogManager.getLogger();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Text.class, new Text.Serializer())
            .registerTypeHierarchyAdapter(Style.class, new Style.Serializer())
            .registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory())
            .create();

    private static final Identifier MOTD_JSON = new Identifier("easymotd", "texts/motd.json");

    private static final TypeToken<List<Text>> TEXT_TYPE = new TypeToken<List<Text>>() {
    };

    private final Random random = new Random();
    private final List<Text> messages = new ArrayList<>();

    private MinecraftServer server;

    @Override
    public void onInitialize() {
        QueryListener.EVENT.register(metadata -> {
            if (messages.size() > 1) {
                int pos = random.nextInt(messages.size());
                metadata.setDescription(messages.get(pos));
            }
        });
        ServerStartCallback.EVENT.register(server -> {
            this.server = server;
            messages.add(new LiteralText(server.getServerMotd()));
            server.getDataManager().registerListener(this);
            loadMotd(server.getDataManager());
        });
    }

    @Override
    public void apply(ResourceManager manager) {
        loadMotd(manager);
    }

    private void loadMotd(ResourceManager manager) {
        messages.clear();

        try (Resource res = manager.getResource(MOTD_JSON);
             BufferedReader reader = new BufferedReader(new InputStreamReader(res.getInputStream()))) {
            List<Text> motd = gson.fromJson(reader, TEXT_TYPE.getType());
            this.messages.addAll(motd);

            logger.info("Loaded {} motd messages", this.messages::size);
        } catch (Exception e) {
            logger.warn("Unable to load motd.json.", e);
        }
        Text text;
        if (messages.isEmpty()) {
            logger.info("No messages loaded from motd.json. Using default.");
            text = new LiteralText(server.getServerMotd());
        } else {
            text = messages.get(0);
        }
        server.getServerMetadata().setDescription(text);
    }
}
