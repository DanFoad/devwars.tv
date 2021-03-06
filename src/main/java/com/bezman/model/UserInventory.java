package com.bezman.model;

import com.bezman.annotation.HibernateDefault;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Rob on 1/23/2016.
 */
@Getter
@Setter
@NoArgsConstructor
public class UserInventory extends BaseModel{

    private Integer id;

    @HibernateDefault("1")
    private Integer usernameChanges;

    @HibernateDefault("1")
    private Integer avatarChanges;

    @JsonIgnore
    private User user;

}
