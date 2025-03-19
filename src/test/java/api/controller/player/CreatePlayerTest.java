package api.controller.player;

import org.interview.task.client.PlayerApiClient;
import org.interview.task.dto.PlayerDto;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static java.net.HttpURLConnection.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CreatePlayerTest extends BaseTest {

    private final PlayerApiClient playerApiClient = new PlayerApiClient();

    @Test
    public void shouldCreateNewPlayer() {
        // prepare player request dto
        PlayerDto requestPlayer = PlayerDto.builder()
                .age(25)
                .gender("male")
                .login("admin")
                .password("securePass")
                .role("admin")
                .screenName("TestPlayer")
                .build();

        // send create player request
        var createPlayerResponse = playerApiClient.createPlayerAs("supervisor", requestPlayer);
        assertThat(createPlayerResponse.statusCode()).isEqualTo(HTTP_OK);

        // verify that player response values equals to request values ( excluding id )
        var createdPlayer = createPlayerResponse.as(PlayerDto.class);
        assertThat(createdPlayer)
                .usingRecursiveComparison()
                // Excluding 'id' from comparison
                .ignoringFields("id")
                .isEqualTo(requestPlayer);

        // retrieve created player from the system
        var getPlayerResponse = playerApiClient.getPlayerById(createdPlayer.getId());
        assertThat(getPlayerResponse.statusCode()).isEqualTo(HTTP_OK);

        // verify that created player is equals to retrieved from the system
        var getPlayer = getPlayerResponse.as(PlayerDto.class);
        assertThat(createdPlayer)
                .usingRecursiveComparison().isEqualTo(getPlayer);
    }

    @Test
    public void shouldNotCreatePlayerInCaseOfInvalidEditor() {
        PlayerDto requestPlayer = PlayerDto.builder()
                .age(25)
                .gender("male")
                .login("admin")
                .password("securePass")
                .role("admin")
                .screenName("TestPlayer")
                .build();
        var response = playerApiClient.createPlayerAs("invalideditor", requestPlayer);
        assertThat(response.statusCode()).isEqualTo(HTTP_FORBIDDEN);
    }


    @Test(dataProvider = "missingRequiredFields")
    public void shouldNotCreatePlayerWithoutRequiredFields(String editor, PlayerDto playerRequest) {
        var response = playerApiClient.createPlayerAs(editor, playerRequest);
        assertThat(response.statusCode()).isEqualTo(HTTP_BAD_REQUEST);
    }

    @DataProvider(name = "missingRequiredFields")
    public Object[][] provideMissingFieldObjects() {
        return new Object[][]{
                {"supervisor", PlayerDto.builder().gender("male").login("admin").password("securePass").role("admin").screenName("TestPlayer").build()},
                {"supervisor", PlayerDto.builder().age(25).login("admin").password("securePass").role("admin").screenName("TestPlayer").build()},
                {"supervisor", PlayerDto.builder().age(25).gender("male").password("securePass").role("admin").screenName("TestPlayer").build()},
                {"supervisor", PlayerDto.builder().age(25).gender("male").login("admin").screenName("TestPlayer").build()},
                {"supervisor", PlayerDto.builder().age(25).gender("male").login("admin").password("securePass").role("admin").build()},
        };
    }
}
