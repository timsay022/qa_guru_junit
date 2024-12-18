import com.codeborne.selenide.Configuration;
import data.Categories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class MagnitMenuTest {

    @BeforeEach
    void setUp() {
        open("https://mm.ru/");
        Configuration.pageLoadStrategy = "eager";
        Configuration.browserSize = "1920x1080";
    }

    @ValueSource( strings = {
            "Новогодние товары", "Электроника",
            "Бытовая техника",
            "Одежда", "Аксессуары"
    })
    @ParameterizedTest(name = "При открытии страницы {0} выдаются результаты соответствующей категории")
    void searchTitleFromMenuItem(String menuItem) {
        $(".categories").$(byText(menuItem)).click();
        $("h1").shouldHave(text(menuItem));
        $$("#category-products").shouldBe(sizeGreaterThan(0));
    }

    @CsvSource (value = {
            "Новогодние товары | Новогодние товары",
            "Строительство и ремонт | Товары для строительства и ремонта",
            "Дача, сад и огород | Товары для дачи и сада",

    }, delimiter = '|')
    @ParameterizedTest(name = "При открытии страницы {0} выдаются результаты соответствующей категории {1}")
    void searchTitleFromMenuItemWithCsvSource(String menuItem, String pageTitle) {
        $(".categories").$(byText(menuItem)).click();
        $("h1").shouldBe(visible);
        $("h1").shouldHave(text(pageTitle));
        $$("#category-products").shouldBe(sizeGreaterThan(0));
    }

    static Stream<Arguments> checkCaterogiesItem() {
        return Stream.of(
                Arguments.of(
                        Categories.clothers,
                        List.of("Женская одежда", "Мужская одежда", "Спецодежда", "Детская одежда")
                ),
                Arguments.of(
                        Categories.technique,
                        List.of("Климатическая техника", "Крупная бытовая техника", "Прочие аксессуары и запчасти для бытовой техники",
                                "Техника для дома", "Техника для красоты и здоровья", "Техника для кухни")
                ),
                Arguments.of(
                        Categories.shoes,
                        List.of("Женская обувь", "Мужская обувь", "Обувь для девочек", "Обувь для мальчиков", "Аксессуары для обуви", "Специализированная обувь")
                )
        );
    }

    @MethodSource
    @ParameterizedTest
    void checkCaterogiesItem(Categories categories, List<String> subcategories) {
        $("[data-test-id='button__catalog']").click();
        $$(".parent-category-item").find(text(categories.description)).hover();
        $$(".category-item .category-title").shouldHave(texts(subcategories));

    }

}
