package api.controller.player;

import io.restassured.common.mapper.TypeRef;
import org.interview.task.client.PlayerApiClient;
import org.interview.task.dto.PlayerDto;
import org.interview.task.helper.PlayerHelper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.IntStream;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class GetAllPlayersTest extends BaseTest {

    private final PlayerApiClient playerApiClient = new PlayerApiClient();
    private List<PlayerDto> expectedPlayers;

    @BeforeClass
    public void setup() {
        // create 2 new players we expect to be returned from getAll endpoint
        IntStream.range(0, 1).forEach(i ->
                expectedPlayers.add(PlayerHelper.createValidPlayer()));
    }

    @Test
    public void shouldRetrievePlayerInfo() {
        // when fetching all players
        var getAllResponse = playerApiClient.getAllPlayers();
        assertThat(getAllResponse.statusCode()).isEqualTo(HTTP_OK);
        var retrievedPlayers = getAllResponse.jsonPath().getList("players", PlayerDto.class);

        // verify that retrieved player list contains list of expected players
        assertThat(retrievedPlayers)
                .containsAll(expectedPlayers);
    }

}
