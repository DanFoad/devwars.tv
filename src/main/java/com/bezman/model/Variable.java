package com.bezman.model;

/**
 * Created by Terence on 5/24/2015.
 */
public class Variable extends BaseModel
{

    public int id;

    public String key, value;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}
