package com.bezman.model;

/**
 * Created by Terence on 4/4/2015.
 */
public class GameSignup extends BaseModel
{

    public int id;

    public User user;
    public Game game;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }
}
