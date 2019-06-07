package mnm.mods.easymotd;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.ServerMetadata;

public interface QueryListener {

    Event<QueryListener> EVENT = EventFactory.createArrayBacked(QueryListener.class, listeners -> data -> {
        for (QueryListener event : listeners) {
            event.onQuery(data);
        }
    });

    void onQuery(ServerMetadata metadata);
}
