package ro.code4.monitorizarevot.data.rest.gson;

import com.google.gson.*;
import io.realm.RealmList;
import ro.code4.monitorizarevot.net.model.Version;

import java.lang.reflect.Type;
import java.util.List;

public class VersionSerializer implements JsonSerializer<List<Version>>, JsonDeserializer<List<Version>> {

    @Override
    public List<Version> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        JsonObject jsonObject = json.getAsJsonObject();
        RealmList<Version> versions = new RealmList<>();

        for (String key : jsonObject.keySet()) {
            Version v = new Version();
            v.setKey(key);
            v.setValue(jsonObject.get(key).getAsInt());
            versions.add(v);
        }

        return versions;
    }

    @Override
    public JsonElement serialize(List<Version> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonVersion = new JsonObject();

        for (Version version: src) {
            jsonVersion.addProperty(version.getKey(), version.getValue());
        }

        return jsonVersion;
    }
}
