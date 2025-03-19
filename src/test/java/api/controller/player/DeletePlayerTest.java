package api.controller.player;

import org.interview.task.client.PlayerApiClient;
import org.interview.task.dto.PlayerDto;
import org.interview.task.enums.UserRoles;
import org.interview.task.helper.PlayerHelper;
import org.testng.annotations.Test;

import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class DeletePlayerTest {

    private final PlayerApiClient playerApiClient = new PlayerApiClient();

    @Test
    public void shouldCreateNewPlayer() {
        // create test player
        var createdPlayer = PlayerHelper.createValidPlayer();

        // delete created test player
        var deleteResponse = playerApiClient.deletePlayerById(UserRoles.SUPERVISOR.getValue(), createdPlayer.getId());
        assertThat(deleteResponse.statusCode()).isEqualTo(HTTP_OK);

        // verify that player was removed from the system
        var getPlayerResponse = playerApiClient.getPlayerById(createdPlayer.getId());
        assertThat(getPlayerResponse.statusCode()).isEqualTo(HTTP_OK);
        assertThat(getPlayerResponse.getBody().asString()).isEmpty();
    }

    @Test
    public void shouldNotRemovePlayerForNonExistingEditor() {
        // create test player
        var createdPlayer = PlayerHelper.createValidPlayer();

        // try to delete created test player with non existing editor role
        var deleteResponse = playerApiClient.deletePlayerById("invalidEditor", createdPlayer.getId());
        assertThat(deleteResponse.statusCode()).isEqualTo(HTTP_FORBIDDEN);

        // verify that player was NOT removed from the system
        var getPlayerResponse = playerApiClient.getPlayerById(createdPlayer.getId());

        assertThat(getPlayerResponse.statusCode()).isEqualTo(HTTP_OK);
        assertThat(getPlayerResponse.getBody().as(PlayerDto.class))
                .isEqualTo(createdPlayer);
    }
}
