import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class WebTests {

    private static final String BASE_URL = "https://the-internet.herokuapp.com/";

    @Test
    public void test1CheckCheckboxes() {

        SelenideElement checkbox1 = $(byAttribute("type", "checkbox"), 0);
        SelenideElement checkbox2 = $(byAttribute("type", "checkbox"), 1);

        open(BASE_URL + "checkboxes");
        checkbox1.click();
        checkbox2.click();

        System.out.println("State for checkbox1 - " + checkbox1.isSelected());
        System.out.println("State for checkbox2 - " + checkbox2.isSelected());
    }

    @Test
    public void test2CheckDropdown() {

        SelenideElement dropdown = $(byId("dropdown"));
        SelenideElement dropdownOption1 = $(byValue("1"));
        SelenideElement dropdownOption2 = $(byValue("2"));

        open(BASE_URL + "dropdown");

        dropdown.click();
        dropdownOption1.click();
        System.out.println("Text for dropdown - " + dropdown.getText());

        $(byId("dropdown")).click();
        dropdownOption2.click();
        System.out.println("Text for dropdown - " + dropdown.getText());
    }

    @Test
    public void test3CheckDisappearingElements() {

        ElementsCollection elements = $$(byCssSelector("li a"));

        open(BASE_URL + "disappearing_elements");

        int attempts = 1;

        while (attempts <= 1) {

            if (elements.size() == 5) {
                System.out.println("The page displays 5 elements with " + attempts + " attempt");
                break;
            }

            attempts++;
            refresh();
        }

        if (elements.size() != 5) {
            throw new RuntimeException("Failed to display 5 items in 10 attempts");
        }
    }

    @Test
    public void test4CheckInputs() {

        SelenideElement input = $(byAttribute("type", "number"));

        Random rand = new Random();
        int randomNumber = rand.nextInt(10000) + 1;

        open(BASE_URL + "inputs");
        input.val(String.valueOf(randomNumber));

        System.out.println("Value for input element - " + input.getValue());
    }

    @Test
    public void test5CheckHovers() {
        ElementsCollection pictures = $$(byClassName("figure"));

        open(BASE_URL + "hovers");

        pictures.forEach(picture -> {
            System.out.println("Text for picture: \n" + picture.hover().getText() + "\n-------------------");
        });
    }

    @Test
    public void test6CheckNotificationMessage() {

        SelenideElement link = $(byLinkText("Click here"));
        SelenideElement message = $(byId("flash"));
        SelenideElement messageCloseButton = $(byClassName("close"));

        open(BASE_URL + "notification_message_rendered");

        link.click();
        while (!message.getText().contains("Action successful")) {
            messageCloseButton.click();
            link.click();
        }
    }

    @Test
    public void test7CheckAddRemoveElements() {

        SelenideElement addElementButton = $(byText("Add Element"));
        ElementsCollection deleteButtons = $$(byClassName("added-manually"));

        open(BASE_URL + "add_remove_elements/");

        int attempts = 0;

        while (attempts < 5) {
            addElementButton.click();
            System.out.println("Add " + $(byClassName("added-manually"), attempts).getText() + " button with index: " + attempts);
            attempts++;
        }

        Random rand = new Random();

        for (int i = 0; i < 3; i++) {
            $(byClassName("added-manually"), rand.nextInt(attempts - i)).click();
            System.out.println("\n-------------------");
            System.out.println("Lost number of buttons: " + $$(byClassName("added-manually")).size());

            deleteButtons.forEach(deleteButton -> {
                System.out.println("Text for picture: " + deleteButton.getText());
            });
        }
    }

    @Test
    public void test81CheckStatusCode() {
        open(BASE_URL + "status_codes");
        $(byLinkText("200")).click();
        System.out.println("Text after going to the status page: " + $(byTagName("p")).getText());
    }

    @Test
    public void test82CheckStatusCode() {
        open(BASE_URL + "status_codes");
        $(byLinkText("301")).click();
        System.out.println("Text after going to the status page: " + $(byTagName("p")).getText());
    }

    @Test
    public void test83CheckStatusCode() {
        open(BASE_URL + "status_codes");
        $(byLinkText("404")).click();
        System.out.println("Text after going to the status page: " + $(byTagName("p")).getText());
    }

    @Test
    public void test84CheckStatusCode() {
        open(BASE_URL + "status_codes");
        $(byLinkText("500")).click();
        System.out.println("Text after going to the status page: " + $(byTagName("p")).getText());
    }

    @AfterEach
    public void tearDown() {
        closeWindow();
    }
}
