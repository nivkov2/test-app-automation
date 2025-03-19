package org.interview.task.helper;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.interview.task.client.PlayerApiClient;
import org.interview.task.dto.PlayerDto;
import org.interview.task.enums.UserRoles;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.interview.task.enums.UserRoles.*;

@UtilityClass
@Slf4j
public class PlayerHelper {

    private final PlayerApiClient playerApiClient = new PlayerApiClient();

    public PlayerDto createValidPlayer() {
        log.info("Creating valid Player entity");
        PlayerDto requestPlayer = PlayerDto.builder()
                .age(25)
                .gender("male")
                .login("admin")
                .password("securePass")
                .role("admin")
                .screenName("TestPlayer")
                .build();

        var response = playerApiClient.createPlayerAs(SUPERVISOR.getValue(), requestPlayer);
        response.then().statusCode(HTTP_OK);

        return response.as(PlayerDto.class);
    }

}
