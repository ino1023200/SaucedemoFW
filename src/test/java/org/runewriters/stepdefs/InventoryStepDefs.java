
package org.runewriters.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.openqa.selenium.WebDriver;
import org.runewriters.config.DriverManager;
import org.runewriters.pom.InventoryItemPage;
import org.runewriters.pom.InventoryPage;
import org.runewriters.pom.LoginPage;

import java.util.ArrayList;
import java.util.Collections;

public class InventoryStepDefs {

    private WebDriver webDriver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private InventoryItemPage inventoryItemPage;
    private String productPrice;
    private String productName;
    private String imageSource;
    private String productDescription;

    @Given("I am in inventory page")
    public void iAmInInventoryPage(){
        webDriver = DriverManager.getDriver();
        webDriver.get("https://www.saucedemo.com");

        loginPage = new LoginPage(webDriver);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogInButton();

        inventoryPage = new InventoryPage(webDriver);
    }

    @When("I click the product name{int}")
    public void iClickTheProductName(int name_index){
        webDriver = DriverManager.getDriver();
        productPrice = inventoryPage.getPriceAtIndex(name_index/2);
        productName = inventoryPage.getItemNameAtIndex(name_index/2);
        imageSource = inventoryPage.getItemImageSourceAtIndex(name_index/2);
        productDescription = inventoryPage.getItemDescriptionAtIndex(name_index/2);
        System.out.println(productPrice);
        inventoryPage.clickItemImageAtIndex(name_index);
    }

    @When("I click the product image{int}")
    public void iClickTheProduct(int image_index){
        webDriver = DriverManager.getDriver();
        inventoryPage = new InventoryPage(webDriver);
        productPrice = inventoryPage.getPriceAtIndex(image_index/2);
        productName = inventoryPage.getItemNameAtIndex(image_index/2);
        imageSource = inventoryPage.getItemImageSourceAtIndex(image_index/2);
        productDescription = inventoryPage.getItemDescriptionAtIndex(image_index/2);
        System.out.println(productPrice);
        inventoryPage.clickItemImageAtIndex(image_index);
    }

    @Then("I get into the product information page")
    public void iGetIntoTheProductInformationPage(){
        webDriver = DriverManager.getDriver();
        inventoryItemPage = new InventoryItemPage(webDriver);
    }

    @And("The price is match with the product")
    public void thePriceIsMatchWithTheProduct(){
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals(productPrice, inventoryItemPage.getItemPrice());
    }

    @And("The product name is match with the link")
    public void theProductNameIsMatchWithTheLink(){
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals(productName, inventoryItemPage.getItemName());
    }

    @And("The product image is match with the link")
    public void theProductImageIsMatchWithTheLink(){
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals(imageSource, inventoryItemPage.getItemImageSource());
    }

    @And("The product description is match with the link")
    public void theProductDescriptionIsMatchWithTheLink(){
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals(productDescription, inventoryItemPage.getItemDescription());
    }

    @When("I select sort Z to A from the dropdown list")
    public void iSelectSortZToAFromTheDropdownList(){
        webDriver = DriverManager.getDriver();
        inventoryPage.filterPageByNameZToA();
    }

    @When("I select sort A to Z from the dropdown list")
    public void iSelectSortAToZFromTheDropdownList(){
        webDriver = DriverManager.getDriver();
        inventoryPage.filterPageByNameAToZ();
    }

    @Then("All item sort in ascending order with its name")
    public void allItemSortInAscendingOrderWithItsName() {
        webDriver = DriverManager.getDriver();
        ArrayList<String> sortProductArray = new ArrayList<>();
        for(int i=0;i<6;i++){
            sortProductArray.add(inventoryPage.getItemNameAtIndex(i));
        }
        Collections.sort(sortProductArray);
        for(int i=0;i<6;i++){
            Assumptions.assumeTrue(sortProductArray.get(i).equals(inventoryPage.getItemNameAtIndex(i)));
        }
    }

    @Then("All item sort in descending order with its name")
    public void allItemSortInDescendingOrderWithItsName() {
        webDriver = DriverManager.getDriver();
        ArrayList<String> sortProductArray = new ArrayList<>();
        for(int i=0;i<6;i++){
            sortProductArray.add(inventoryPage.getItemNameAtIndex(i));
        }
        Collections.sort(sortProductArray, Collections.reverseOrder());
        for(int i=0;i<6;i++){
            Assumptions.assumeTrue(sortProductArray.get(i).equals(inventoryPage.getItemNameAtIndex(i)));
        }
    }

    @When("I select sort price low to high from the dropdown list")
    public void iSelectSortPriceLowToHighFromTheDropdownList(){
        webDriver = DriverManager.getDriver();
        inventoryPage.filterPageByPriceLowToHigh();
    }

    @When("I select sort price high to low from the dropdown list")
    public void iSelectSortPriceHighToLowFromTheDropdownList(){
        webDriver = DriverManager.getDriver();
        inventoryPage.filterPageByPriceHighToLow();
    }

    @Then("All item sort in ascending order with its price")
    public void allItemSortInAscendingOrderWithItsPrice(){
        webDriver = DriverManager.getDriver();
        ArrayList<Double> sortedPriceArrayList = new ArrayList<>();
        for(int i=0;i<6;i++){
            sortedPriceArrayList.add(Double.parseDouble(inventoryPage.getPriceAtIndex(i).replaceAll("[$]","")));
        }
        Collections.sort(sortedPriceArrayList);
        for(int i=0;i<6;i++){
            Assumptions.assumeTrue(sortedPriceArrayList.get(i).equals(Double.parseDouble(inventoryPage.getPriceAtIndex(i).replaceAll("[$]",""))));
        }
    }

    @Then("All item sort in descending order with its price")
    public void allItemSortInDescendingOrderWithItsPrice(){
        webDriver = DriverManager.getDriver();
        ArrayList<Double> sortedPriceArrayList = new ArrayList<>();
        for(int i=0;i<6;i++){
            sortedPriceArrayList.add(Double.parseDouble(inventoryPage.getPriceAtIndex(i).replaceAll("[$]","")));
        }
        Collections.sort(sortedPriceArrayList, Collections.reverseOrder());
        for(int i=0;i<6;i++){
            Assumptions.assumeTrue(sortedPriceArrayList.get(i).equals(Double.parseDouble(inventoryPage.getPriceAtIndex(i).replaceAll("[$]",""))));
        }
    }

    @When("I click add to cart or remove button {int}")
    public void iClickAddToCart(int i){
        webDriver = DriverManager.getDriver();
        inventoryPage.clickAddToCartOrRemoveButtonAtIndex(i);
    }

    @Then("{int} button shows remove")
    public void buttonShowsRemove(int i){
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals("REMOVE",inventoryPage.getButtonNameAtIndex(i));
    }

    @Then("{int} button shows add to chart")
    public void buttonShowsAddToChart(int i){
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals("ADD TO CART",inventoryPage.getButtonNameAtIndex(i));
    }

    @And("I click add to cart or remove button in inventory page")
    public void iClickIntAddToCartOrRemoveButtonInInventoryPage(){
        webDriver = DriverManager.getDriver();
        inventoryItemPage.clickAddToCartOrRemoveButton();
    }

    @Then("The button in inventory page shows remove")
    public void theButtonInInventoryPageShowsRemove(){
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals("REMOVE",inventoryItemPage.getAddToCartOrRemoveButtonName());
    }

    @And("I click back to products button")
    public void iClickBackToProductsButton(){
        webDriver = DriverManager.getDriver();
        inventoryItemPage.clickBackToProductsButton();
    }

    @Then("I get into the inventory page")
    public void iGetIntoTheInventoryPage(){
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals("https://www.saucedemo.com/inventory.html", inventoryPage.getCurrentURL());
    }

    @When("I click {int} add to cart or remove button")
    public void iClickAddToCartOrRemoveButton(int i ){
        webDriver = DriverManager.getDriver();
        inventoryPage.clickAddToCartOrRemoveButtonAtIndex(i);
    }

    @Then("The cart icon show plus one product")
    public void theCartIconSHowPlusOneProduct(){
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals("1",inventoryPage.getShoppingCartBadge());
    }

    @When("I click all product add to cart or remove button")
    public void whenIClickAllProductAddToCartOrRemoveButton(){
        webDriver = DriverManager.getDriver();
        for(int i =0; i <6;i++){
            inventoryPage.clickAddToCartOrRemoveButtonAtIndex(i);
        }
    }

    @Then("The cart icon shows all product amount")
    public void theCartIconShowsAllProductAmount(){
        webDriver = DriverManager.getDriver();
        Assertions.assertEquals("6",inventoryPage.getShoppingCartBadge());
    }

}