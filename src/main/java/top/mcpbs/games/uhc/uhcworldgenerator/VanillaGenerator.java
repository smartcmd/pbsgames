package top.mcpbs.games.uhc.uhcworldgenerator;

import cn.nukkit.level.generator.Generator;

import java.util.Collections;
import java.util.Map;

public abstract class VanillaGenerator extends Generator {

    public static final int TYPE_LARGE_BIOMES = 5;
    public static final int TYPE_AMPLIFIED = 6;

    public static int SEA_LEVEL = 64;

    @Override
    public Map<String, Object> getSettings() {
        return Collections.emptyMap();
    }
}
