package api.controller.player;

import org.interview.task.client.PlayerApiClient;
import org.interview.task.dto.PlayerDto;
import org.interview.task.helper.PlayerHelper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class GetPlayerTest extends BaseTest {

    private final PlayerApiClient playerApiClient = new PlayerApiClient();
    private PlayerDto playerDto;

    @BeforeClass
    public void setup() {
        // create new player or take any static player info from DB
        playerDto = PlayerHelper.createValidPlayer();
    }

    @Test
    public void shouldRetrievePlayerInfo() {
        // when fetching player info by id
        var getResponse = playerApiClient.getPlayerById(playerDto.getId());
        assertThat(getResponse.statusCode()).isEqualTo(HTTP_OK);
        var retrievedPlayer = getResponse.as(PlayerDto.class);

        // verify that expected data is equals to returned one
        assertThat(playerDto)
                .usingRecursiveComparison()
                .isEqualTo(retrievedPlayer);
    }

    @Test(dataProvider = "nonExistingPlayerId")
    public void shouldNotReturnResultsInCaseOfNonExistingPlayerIds(Long playerId) {
        var response = playerApiClient.getPlayerById(playerId);
        assertThat(response.statusCode()).isEqualTo(HTTP_OK);
        assertThat(response.getBody().asString()).isEmpty();
    }

    @DataProvider(name = "nonExistingPlayerId")
    public Object[][] provideNonExistingPlayerIds() {
        return new Object[][]{
                {-1232L},
                {123123123123123L}
        };
    }
}