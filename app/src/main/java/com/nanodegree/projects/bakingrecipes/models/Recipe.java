package com.nanodegree.projects.bakingrecipes.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Define the REcipe object. Parse it using Jackson.
 */
public class Recipe implements Parcelable {

    @JsonProperty("id")
    private int id;
    @JsonProperty("image")
    private String image;
    @JsonProperty("ingredients")
    private List<Ingredients> ingredients;
    @JsonProperty("name")
    private String name;
    @JsonProperty("servings")
    private int servings;
    @JsonProperty("steps")
    private List<Step> steps;

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.image);
        parcel.writeInt(this.servings);
        parcel.writeString(this.name);
        parcel.writeList(this.ingredients);
        parcel.writeInt(this.id);
        parcel.writeList(this.steps);
    }

    @Override
    public String toString() {
        String recipe = "Recipe{" +
                "image='" + image + '\'' +
                ", servings=" + servings +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", id=" + id +
                ", steps=" + steps +
                '}';
        return recipe;
    }

    //  Initialize Recipe object
    public Recipe() {
        this.id = 0;
        this.image = "";
        this.ingredients = new ArrayList<>();
        this.name = "";
        this.servings = 0;
        this.steps = new ArrayList<>();
    }

    protected Recipe(Parcel in) {
        this.image = in.readString();
        this.servings = in.readInt();
        this.name = in.readString();
        this.ingredients = new ArrayList<>();
        in.readList(this.ingredients, Ingredients.class.getClassLoader());

        this.id = in.readInt();
        this.steps = new ArrayList<>();
        in.readList(this.steps, Step.class.getClassLoader());
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public int getId() {
        return id;
    }

    public int getServings() {
        return servings;
    }

    public List<Step> getSteps() {
        return steps;
    }

    //  generate instance of Parcelable class
    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    //  Parse using Jackson
    //  Reference - https://www.concretepage.com/jackson-api/read-write-json-using-jackson-objectmapper-jsonparser-jsongenerator-example
    public static String toBase64String(Recipe recipe) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Base64.encodeToString(mapper.writeValueAsBytes(recipe), 0);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //  Parse using Jackson
    //  Reference - https://www.concretepage.com/jackson-api/read-write-json-using-jackson-objectmapper-jsonparser-jsongenerator-example
    public static Recipe fromBase64(String encoded) {
        if (!"".equals(encoded)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(Base64.decode(encoded, 0), Recipe.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}