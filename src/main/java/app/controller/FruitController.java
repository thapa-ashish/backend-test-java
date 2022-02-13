package app.controller;

import app.dto.TaskRequest;
import app.entity.FruitStand;
import app.service.FruitService;
import app.service.FruitStandService;
import io.jooby.Context;
import io.jooby.annotations.ContextParam;
import io.jooby.annotations.GET;
import io.jooby.annotations.Path;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Controller for returning the results from your solutions for task 1
 */
@Tag(name = "FruitController", description = "Controller for the first task of the code test")
@Path("/fruit")
public class FruitController {

    // A logger, should you need one
    private static final Logger logger = LoggerFactory.getLogger(FruitController.class);
    private FruitService fruitService;
    private FruitStandService fruitStandService;

    public FruitController(FruitService fruitService, FruitStandService fruitStandService) {
        this.fruitService = fruitService;
        this.fruitStandService = fruitStandService;
    }

    /**
     * Endpoint for your solution
     *
     * @param context the jooby HTTP request context,
     *                lets you interact with and manipulate the HTTP request and HTTP response
     * @return your result
     */
    @GET("/task1")
    public String task1(@RequestBody TaskRequest taskRequest, @ContextParam Context context) {
        List<FruitStand> fruitStands = fruitStandService.getAllFruitStand();
        fruitStands.sort(new SortFruitStandByOrder());

        List<String> fruits = taskRequest.getFruits().stream().map(f -> f.getFruitName()).collect(Collectors.toCollection(ArrayList::new));
        HashMap<FruitStand, Double> pricePerStand = new HashMap<>();
        fruitStands.forEach(f -> {
            AtomicReference<Double> cost = new AtomicReference<>(0.00);
            fruits.stream().forEach(fruit -> {
                FruitStand fruitStand = fruitStandService.getFruitByNameAtStand(fruit, f.getOrder());
                cost.updateAndGet(v -> v + fruitStand.getCostPerUnit());
            });
            pricePerStand.put(f, cost.get());
        });


        Double minCost = pricePerStand.get(fruitStands.get(0));
        FruitStand fruitStand = null;
        for (FruitStand fs : fruitStands) {
            Double costPerStand = pricePerStand.get(fs);
            minCost = (minCost < costPerStand) ? minCost : costPerStand;
        }


        for (Map.Entry<FruitStand, Double> entry : pricePerStand.entrySet()) {
            if (entry.getValue() == minCost) {
                fruitStand = entry.getKey();
                System.out.println(entry.getKey());
            }
        }

        return "Minimum cost would be " + minCost + " at Stand " + fruitStand.getOrder();
    }


    private class SortFruitStandByOrder implements Comparator<FruitStand> {
        @Override
        public int compare(FruitStand o1, FruitStand o2) {
            return o1.getOrder() - o2.getOrder();
        }
    }
}
