package com.example.ruben.hobbyhuset;

import org.json.JSONObject;

/**
 * Abstract parent class for Kunde, Vare, Object to implement the general toJSON for update calls
 */

public abstract class Item {

    abstract JSONObject toJSON();
}
