package models;

import java.util.List;

import play.db.jpa.GenericModel;
import play.db.jpa.JPABase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.json.SkipStrategy;

/**
 * @author jgomes - Jefferson Chaves Gomes | 11/09/2014 - 11:09:31
 */
public abstract class BaseModel extends GenericModel implements Comparable<BaseModel> {

    private static final long serialVersionUID = -2925246703624591882L;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // JSON aux methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public String toJson() {
        final GsonBuilder builder = buildJsonParser();
        final Gson gson = builder.create();
        return gson.toJson(this);
    }
    public static String toJson(final List<?> list) {
        final GsonBuilder builder = buildJsonParser();
        final Gson gson = builder.create();
        return gson.toJson(list);
    }
    public static <T extends JPABase> T fromJson(final String jsonString, final Class<?> clazz) {
        final GsonBuilder builder = buildJsonParser();
        final Gson gson = builder.create();
        return (T) gson.fromJson(jsonString, clazz);
    }
    public static GsonBuilder buildJsonParser() {
        final GsonBuilder builder = new GsonBuilder();
        builder.setExclusionStrategies(new SkipStrategy());
        builder.setDateFormat("dd/MM/yyyy HH:mm:ss");
        return builder;
    }
}