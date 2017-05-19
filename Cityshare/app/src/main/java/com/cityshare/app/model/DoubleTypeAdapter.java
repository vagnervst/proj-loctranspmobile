package com.cityshare.app.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by vagne_000 on 18/05/2017.
 */

public class DoubleTypeAdapter extends TypeAdapter<Double> {

    @Override
    public void write(JsonWriter out, Double value) throws IOException {

    }

    @Override
    public Double read(JsonReader reader) throws IOException {
        if(reader.peek() == JsonToken.NULL){
            reader.nextNull();
            return null;
        }

        String stringValue = reader.nextString();
        try{
            Double value = 0.0;
            return value;
        }catch(NumberFormatException e){
            return null;
        }
    }
}
