package com.bezman.model;

import com.bezman.exclusion.GsonExclude;
import com.google.gson.annotations.Expose;

/**
 * Created by Terence on 4/15/2015.
 */
public class Objective extends BaseModel
{

    public int id;

    public Integer orderID;

    @GsonExclude
    public Game game;

    public String objectiveText;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    public String getObjectiveText()
    {
        return objectiveText;
    }

    public void setObjectiveText(String objectiveText)
    {
        this.objectiveText = objectiveText;
    }

    public Integer getOrderID()
    {
        return orderID == null ? 0 : orderID;
    }

    public void setOrderID(Integer orderID)
    {
        this.orderID = orderID;
    }
}
