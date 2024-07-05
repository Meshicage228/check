package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.domain.InputStringDetails;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.utils.StringInputUtil;
import java.util.Map;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DisplayName("String input tests")
@ExtendWith(MockitoExtension.class)
class StringInputUtilTest {

    @Spy
    private StringInputUtil stringInputUtil = new StringInputUtil();

    @Test
    @DisplayName("Correct details string")
    void whenArgsAreValid_thenFormsStringDetailsCorrectly() {
        String[] args = {
                "3-1",
                "2-3",
                "5-1",
                "balanceDebitCard=100",
                "discountCard=1112",
                "saveToFile=./result.csv",
                "datasource.url=jdbc:postgresql://localhost:5432/check",
                "datasource.username=postgres",
                "datasource.password=28072004"
        };

        InputStringDetails details = stringInputUtil.formStringDetails(args);

        assertThat(details.getCardNumber()).isEqualTo("1112");
        assertThat(details.getCardBalance()).isEqualTo(100.0f);
        assertThat(details.getUrl()).isEqualTo("jdbc:postgresql://localhost:5432/check");
        assertThat(details.getUsername()).isEqualTo("postgres");
        assertThat(details.getPassword()).isEqualTo("28072004");

        assertThat(details.getExtractedPairs())
                .containsExactlyInAnyOrderEntriesOf(Map.of(3, 1, 2, 3, 5, 1));
    }

    @Test
    @DisplayName("Missing db parameters")
    void missingParameters() {
        String[] args = {
                "3-1",
                "2-3",
                "5-1",
                "balanceDebitCard=100",
                "saveToFile=./result.csv"
        };

        assertThatThrownBy(() -> stringInputUtil.formStringDetails(args))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("No balance card provided")
    void wrongValidation() {
        String[] args = {
                "3-1",
                "2-3",
                "5-1",
                "discountCard=1112",
                "saveToFile=./result.csv",
                "datasource.url=jdbc:postgresql://localhost:5432/check",
                "datasource.username=postgres",
                "datasource.password=postgres"
        };

        assertThatThrownBy(() -> stringInputUtil.validateInputString(args))
                .isInstanceOf(BadRequestException.class);
    }
}