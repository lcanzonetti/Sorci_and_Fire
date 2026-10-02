package com.github.alexthe666.iceandfire.config.biome;

import com.github.alexthe666.citadel.Citadel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLPaths;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SpawnBiomeConfig {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(IafSpawnBiomeData.class, new IafSpawnBiomeData.Deserializer()).create();
    private final ResourceLocation fileName;

    private SpawnBiomeConfig(ResourceLocation fileName) {
        if (!fileName.getNamespace().endsWith(".json")) {
            this.fileName = ResourceLocation.fromNamespaceAndPath(fileName.getNamespace(), fileName.getPath() + ".json");
        } else {
            this.fileName = fileName;
        }

    }

    public static IafSpawnBiomeData create(ResourceLocation fileName, IafSpawnBiomeData dataDefault) {
        SpawnBiomeConfig config = new SpawnBiomeConfig(fileName);
        IafSpawnBiomeData data = config.getConfigData(dataDefault);
        return data;
    }

    public static <T> T getOrCreateConfigFile(File configDir, String configName, T defaults, Type type) {
        File configFile = new File(configDir, configName);
        if (!configFile.exists()) {
            try {
                FileUtils.write(configFile, GSON.toJson(defaults));
            } catch (IOException e) {
                Citadel.LOGGER.error("Spawn Biome Config: Could not write " + configFile, e);
            }
        }
        try {
            return GSON.fromJson(FileUtils.readFileToString(configFile), type);
        } catch (IafSpawnBiomeData.InvalidCitadelFormatException ex) {
            Citadel.LOGGER.warn("Spawn Biome Config: %s uses an outdated citadel_format version, replacing it with the defaults (old file kept as .old)".formatted(configFile));
            try {
                FileUtils.copyFile(configFile, new File(configDir, configName + ".old"));
                FileUtils.write(configFile, GSON.toJson(defaults));
            } catch (IOException e) {
                Citadel.LOGGER.error("Spawn Biome Config: Could not write " + configFile, e);
            }
        } catch (Exception e) {
            Citadel.LOGGER.error("Spawn Biome Config: Could not load " + configFile, e);
        }

        return defaults;
    }

    private File getConfigDirFile() {
        Path configPath = FMLPaths.CONFIGDIR.get();
        Path jsonPath = Paths.get(configPath.toAbsolutePath().toString(), fileName.getNamespace());
        return jsonPath.toFile();
    }

    private IafSpawnBiomeData getConfigData(IafSpawnBiomeData defaultConfigData) {
        IafSpawnBiomeData configData = getOrCreateConfigFile(getConfigDirFile(), fileName.getPath(), defaultConfigData, new TypeToken<IafSpawnBiomeData>() {
        }.getType());
        return configData;
    }
}