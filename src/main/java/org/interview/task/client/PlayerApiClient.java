package org.interview.task.client;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.interview.task.dto.PlayerDto;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.interview.task.config.ConfigurationManager.config;

@Slf4j
public class PlayerApiClient {
    private static String CREATE_SUFFIX = "/create/";
    private static String GET_SUFFIX = "/get";
    private static String DELETE_SUFFIX = "/delete/";
    private static String GET_ALL_SUFFIX = "/get/all";
    private static String UPDATE_SUFFIX = "/update/";

    public Response createPlayerAs(String editor, PlayerDto player) {
        var url = config().basePlayerUrl() + CREATE_SUFFIX + editor;
        log.info("Calling {} to create new player: {}", url, player);
        return given()
                .accept(JSON)
                .queryParam("age", player.getAge())
                .queryParam("gender", player.getGender())
                .queryParam("login", player.getLogin())
                .queryParam("password", player.getPassword())
                .queryParam("role", player.getRole())
                .queryParam("screenName", player.getScreenName())
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    public Response getPlayerById(Long id) {
        var requestBody = Map.of("playerId", id);
        log.info("Retrieving player info by playerId: {}", id);
        return given()
                .accept(JSON)
                .contentType(JSON)
                .when()
                .body(requestBody)
                .post(config().basePlayerUrl() + GET_SUFFIX)
                .then()
                .extract()
                .response();
    }

    public Response getAllPlayers() {
        log.info("Retrieving all existing players");
        return given()
                .accept(JSON)
                .contentType(JSON)
                .when()
                .get(config().basePlayerUrl() + GET_ALL_SUFFIX)
                .then()
                .extract()
                .response();
    }

    public Response updatePlayer(String editor, Long playerId, PlayerDto updatedPlayer) {
        log.info("Updating player with id {} with new values: {}", playerId, updatedPlayer);
        return given()
                .accept(JSON)
                .contentType(JSON)
                .when()
                .put(config().basePlayerUrl() + UPDATE_SUFFIX + editor + "/" + playerId)
                .then()
                .extract()
                .response();
    }

    public Response deletePlayerById(String editor, Long playerId) {
        var requestBody = Map.of("playerId", playerId);
        log.info("Deleting player with id: {}", playerId);
        return given()
                .accept(JSON)
                .contentType(JSON)
                .when()
                .body(requestBody)
                .delete(config().basePlayerUrl() + DELETE_SUFFIX + editor)
                .then()
                .extract()
                .response();
    }

}
