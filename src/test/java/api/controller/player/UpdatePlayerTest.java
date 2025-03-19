package api.controller.player;

import org.interview.task.client.PlayerApiClient;
import org.interview.task.dto.PlayerDto;
import org.interview.task.helper.PlayerHelper;
import org.testng.annotations.Test;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.interview.task.enums.UserRoles.SUPERVISOR;

public class UpdatePlayerTest extends BaseTest {

    private final PlayerApiClient playerApiClient = new PlayerApiClient();

    @Test
    public void shouldUpdatePlayer() {
        // create test player
        var createdPlayer = PlayerHelper.createValidPlayer();

        // prepare update player dto
        var updatedPlayerDto = PlayerDto.builder()
                .age(38)
                .gender("updatedGender")
                .login("updatedLogin")
                .password("updatedPassword")
                .screenName("updatedScreenName")
                .role("updatedRole")
                .build();

        // update player with new values
        var updatedPlayerResponse = playerApiClient
                .updatePlayer(SUPERVISOR.getValue(), createdPlayer.getId(), updatedPlayerDto);
        // check status of put call
        assertThat(updatedPlayerResponse.statusCode()).isEqualTo(HTTP_OK);

        // verify that response model equals to updated values from dto
        var updatedPlayer = updatedPlayerResponse.as(PlayerDto.class);
        assertThat(updatedPlayer)
                .usingRecursiveComparison()
                .ignoringFields("password", "id")
                .isEqualTo(updatedPlayerDto);

        // retrieve updated player from the system
        var getPlayerResponse = playerApiClient.getPlayerById(createdPlayer.getId());
        var getPlayer = getPlayerResponse.as(PlayerDto.class);

        // and verify it was updated within the system
        assertThat(updatedPlayer)
                .usingRecursiveComparison()
                .isEqualTo(getPlayer);
    }
}
