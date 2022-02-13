package app.controller;


import app.dto.TaskRequest;
import app.entity.FruitStand;
import io.jooby.Context;
import io.jooby.annotations.ContextParam;
import io.jooby.annotations.GET;
import io.jooby.annotations.Path;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "FruitStandController")
@Path("/fruitstand")
public class FruitStandController {


    @GET("/")
    public String task1(@RequestBody TaskRequest taskRequest, @ContextParam Context context) {





        return null;
    }
}
