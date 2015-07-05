package com.bezman.model;

import com.bezman.exclusion.GsonExclude;
import com.google.gson.annotations.Expose;

/**
 * Created by Terence on 4/15/2015.
 */
public class CompletedObjective extends BaseModel
{
    public int id;

    public int team_id;

    public Objective objective;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getTeam_id()
    {
        return team_id;
    }

    public void setTeam_id(int team_id)
    {
        this.team_id = team_id;
    }

    public Objective getObjective()
    {
        return objective;
    }

    public void setObjective(Objective objective)
    {
        this.objective = objective;
    }
}
