package com.nanodegree.projects.bakingrecipes.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Define the Ingredients object. Parse it using Jackson.
 */
public class Ingredients implements Parcelable {

    @JsonProperty("ingredient")
    private String ingredient;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("measure")
    private String measure;

    //  Initialize Ingredients
    public Ingredients() {
        this.quantity = 0;
        this.measure = "";
        this.ingredient = "";
    }

    protected Ingredients(Parcel in) {
        this.quantity = in.readInt();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
    }

    //  generate instance of Parcelable class
    public static final Creator<Ingredients> CREATOR = new Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel source) {
            return new Ingredients(source);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };

    public int getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    @Override
    public String toString() {
        String ing = "Ingredients{" +
                "quantity=" + quantity +
                ", measure='" + measure + '\'' +
                ", ingredient='" + ingredient + '\'' +
                '}';
        return ing;
    }
}