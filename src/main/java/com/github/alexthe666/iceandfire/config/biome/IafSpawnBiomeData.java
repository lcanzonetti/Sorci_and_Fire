package com.github.alexthe666.iceandfire.config.biome;


import com.google.gson.*;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.*;

public class IafSpawnBiomeData extends com.github.alexthe666.citadel.config.biome.SpawnBiomeData {

    private List<List<SpawnBiomeEntry>> biomes = new ArrayList<>();
    // Bumped to 1 for 1.21: 1.18 files reference forge: biome tags that no longer exist
    private static final int CITADEL_FORMAT = 1;
    private static final String CITADEL_FORMAT_STRING = "citadel_format";
    private int citadelFormat = CITADEL_FORMAT;

    public IafSpawnBiomeData() {
    }

    public void setCitadelFormat(int format) {
        citadelFormat = format;
    }

    private IafSpawnBiomeData(SpawnBiomeEntry[][] biomesRead) {
        biomes = new ArrayList<>();
        for (SpawnBiomeEntry[] innerArray : biomesRead) {
            biomes.add(Arrays.asList(innerArray));
        }
    }

    public IafSpawnBiomeData addBiomeEntry(BiomeEntryType type, boolean negate, String value, int pool) {
        if (biomes.isEmpty() || biomes.size() < pool + 1) {
            biomes.add(new ArrayList<>());
        }
        biomes.get(pool).add(new SpawnBiomeEntry(type, negate, value));
        return this;
    }

    public boolean matches(@Nullable Holder<Biome> biomeHolder, ResourceLocation registryName) {
        for (List<SpawnBiomeEntry> all : biomes) {
            boolean overall = true;
            for (SpawnBiomeEntry cond : all) {
                if (!cond.matches(biomeHolder, registryName)) {
                    overall = false;
                }
            }
            if (overall) {
                return true;
            }
        }
        return false;
    }

    public static class Deserializer implements JsonDeserializer<IafSpawnBiomeData>, JsonSerializer<IafSpawnBiomeData> {

        @Override
        public IafSpawnBiomeData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonobject = json.getAsJsonObject();
            int citadelFormat = GsonHelper.getAsInt(jsonobject, CITADEL_FORMAT_STRING, -1);

            if (citadelFormat != IafSpawnBiomeData.CITADEL_FORMAT) {
                throw new InvalidCitadelFormatException("The file contained the %s format version, while we expected a %s format version".formatted(citadelFormat, CITADEL_FORMAT));
            }
            SpawnBiomeEntry[][] biomesRead = GsonHelper.getAsObject(jsonobject, "biomes", new SpawnBiomeEntry[0][0], context, SpawnBiomeEntry[][].class);
            return new IafSpawnBiomeData(biomesRead);
        }

        @Override
        public JsonElement serialize(IafSpawnBiomeData src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add(CITADEL_FORMAT_STRING, context.serialize(src.citadelFormat));
            jsonobject.add("biomes", context.serialize(src.biomes));
            return jsonobject;
        }
    }

    private class SpawnBiomeEntry {
        BiomeEntryType type;
        boolean negate;
        String value;

        public SpawnBiomeEntry(BiomeEntryType type, boolean remove, String value) {
            this.type = type;
            this.negate = remove;
            this.value = value;
        }

        public boolean matches(@Nullable Holder<Biome> biomeHolder, ResourceLocation registryName) {
            if (type == BiomeEntryType.REGISTRY_NAME) {
                return registryName.toString().equals(value) != negate;
            }
            if (biomeHolder == null) {
                return negate;
            }
            // Biome categories and the biome dictionary no longer exist; they map onto the "c:is_<name>" convention tags.
            String tagName = type == BiomeEntryType.BIOME_TAG ? value : "c:is_" + value.toLowerCase(Locale.ROOT);
            boolean found = biomeHolder.tags().anyMatch(biomeTagKey -> biomeTagKey.location().toString().equals(tagName));
            return found != negate;
        }
    }

    static class InvalidCitadelFormatException extends JsonParseException {
        InvalidCitadelFormatException(String s) { super(s);}
    }
}